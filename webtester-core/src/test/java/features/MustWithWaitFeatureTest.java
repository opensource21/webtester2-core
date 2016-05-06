package features;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.internal.must.MustConditionException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.annotations.Be;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Must;
import info.novatec.testit.webtester.pagefragments.annotations.Until;
import info.novatec.testit.webtester.pagefragments.annotations.Wait;
import info.novatec.testit.webtester.pages.Page;


public class MustWithWaitFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage() {
        open("html/features/must-be-with-wait.html");
    }

    /*
     * The #button is displayed with a slight delay.
     * Only when the @Wait is used will the must condition be executed positively.
     */

    @Test
    public void demonstratePassingMustBeBehaviour() {
        browser().create(PassingFeaturePage.class);
    }

    @Test(expected = MustConditionException.class)
    public void demonstrateFailingMustBeBehaviour() {
        browser().create(FailingFeaturePage.class);
    }

    /* test pages */

    public interface PassingFeaturePage extends Page {

        @Must(Be.VISIBLE)
        @Wait(Until.PRESENT_AND_VISIBLE)
        @IdentifyUsing("#button")
        Button button();

    }

    public interface FailingFeaturePage extends Page {

        @Must(Be.VISIBLE)
        @IdentifyUsing("#button")
        Button button();

    }

}
