package info.novatec.testit.webtester.pagefragments.identification.producers;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.identification.ByProducer;


/**
 * This {@link ByProducer} produces a {@link By} using {@link By#cssSelector(String)}.
 *
 * @see ByProducer
 * @since 2.0
 */
public class CssSelector implements ByProducer {

    @Override
    public By createBy(String value) {
        return By.cssSelector(value);
    }

    @Override
    public String toString() {
        return "CSS Selector";
    }

}
