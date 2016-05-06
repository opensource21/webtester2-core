package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import java.net.URL;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.Open;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever an URL was opened (navigated to).
 * <p>
 * It includes the URL as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Open#url(String)
 * @see Open#url(URL)
 * @see Open#url(String, Class)
 * @see Open#url(URL, Class)
 * @see Open#defaultEntryPoint()
 * @see Open#defaultEntryPoint(Class)
 * @since 2.0
 */
@SuppressWarnings("serial")
@Getter
public class OpenedUrlEvent extends AbstractEvent {

    private final String url;

    public OpenedUrlEvent(String url) {
        this.url = url;
    }

    @Override
    public String describe() {
        return format("opened url %s", url);
    }

}
