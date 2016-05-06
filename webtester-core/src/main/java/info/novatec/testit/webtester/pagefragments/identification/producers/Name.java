package info.novatec.testit.webtester.pagefragments.identification.producers;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.identification.ByProducer;


/**
 * This {@link ByProducer} produces a {@link By} using {@link By#name(String)}.
 *
 * @see ByProducer
 * @since 2.0
 */
public class Name implements ByProducer {

    @Override
    public By createBy(String value) {
        return By.name(value);
    }

    @Override
    public String toString() {
        return "Name";
    }

}
