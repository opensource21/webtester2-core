package info.novatec.testit.webtester.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.must.MustChecker;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvoker;
import info.novatec.testit.webtester.internal.proxies.PageProxyHandler;
import info.novatec.testit.webtester.pages.Page;


public class PageFactory {

    private final Browser browser;

    public PageFactory(Browser browser) {
        this.browser = browser;
    }

    @SuppressWarnings("unchecked")
    public <T extends Page> T page(Class<T> pageType) {

        ClassLoader classLoader = pageType.getClassLoader();
        Class[] interfaces = { pageType };

        InvocationHandler invocationHandler = new PageProxyHandler(browser, pageType);

        T page = ( T ) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        PostConstructInvoker.invokePostConstructMethods(pageType, page);
        MustChecker.checkMustMethods(pageType, page);
        return page;

    }

}
