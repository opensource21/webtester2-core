package info.novatec.testit.webtester.internal.must;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Be;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Must;
import info.novatec.testit.webtester.pages.Page;


public final class MustChecker {

    private static final String ILLEGAL_SIGNATURE_MSG =
        "invalid @Must method declarations (returns PageFragment and has no parameters): ";

    private static Predicate<Method> isIdentificationMethod = method -> method.isAnnotationPresent(IdentifyUsing.class);
    private static Predicate<Method> isMustMethod = method -> method.isAnnotationPresent(Must.class);
    private static Predicate<Method> isRelevantMethod = isIdentificationMethod.and(isMustMethod);
    private static Predicate<Method> returnsPageFragment =
        method -> PageFragment.class.isAssignableFrom(method.getReturnType());
    private static Predicate<Method> hasNoParams = method -> method.getParameterCount() == 0;
    private static Predicate<Method> isValidMethod = returnsPageFragment.and(hasNoParams);

    public static <T extends Page> void checkMustMethods(Class<T> pageClass, T page) {
        // NOTE: since page is a proxy, the original class needs to be provided from outside!
        doInvokeMustMethods(pageClass, page);
    }

    public static <T extends PageFragment> void checkMustMethods(Class<T> pageFragmentClass, T pageFragment) {
        // NOTE: since pageFragment is a proxy, the original class needs to be provided from outside!
        doInvokeMustMethods(pageFragmentClass, pageFragment);
    }

    private static void doInvokeMustMethods(Class<?> objectClass, Object object) {

        List<Method> mustMethods = getMustMethods(objectClass);
        assertThatAllMethodsHaveValidSignature(mustMethods);

        Map<String, Method> singularMethods = new HashMap<>();
        mustMethods.forEach(method -> singularMethods.put(method.getName(), method));

        singularMethods.values().forEach(method -> invoke(method, object));

    }

    private static List<Method> getMustMethods(Class<?> objectClass) {
        List<Method> mustMethods = new LinkedList<>();
        Arrays.stream(objectClass.getInterfaces())
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredMethods()))
            .filter(isRelevantMethod)
            .forEach(mustMethods::add);
        Arrays.stream(objectClass.getDeclaredMethods()).filter(isRelevantMethod).forEach(mustMethods::add);
        return mustMethods;
    }

    private static void assertThatAllMethodsHaveValidSignature(List<Method> mustMethods) {
        List<Method> illegalMethods = new LinkedList<>();
        mustMethods.forEach(method -> {
            if (!isValidMethod.test(method)) {
                illegalMethods.add(method);
            }
        });
        if (!illegalMethods.isEmpty()) {
            throw new IllegalSignatureException(ILLEGAL_SIGNATURE_MSG + illegalMethods);
        }
    }

    private static void invoke(Method method, Object object) {
        try {
            Must annotation = method.getAnnotation(Must.class);
            PageFragment fragment = ( PageFragment ) method.invoke(object);
            Be condition = annotation.value();
            if (!condition.checkFor(fragment)) {
                throw new MustConditionException("condition not met for method (" + method + "): " + condition);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MustConditionException(e);
        }
    }

    private MustChecker() {
        // utility class constructor
    }

}
