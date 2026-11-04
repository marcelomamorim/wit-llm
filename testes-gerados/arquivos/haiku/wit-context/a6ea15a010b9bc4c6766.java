/*
 * @test
 * @summary Checks that LocalizedMessage.getLocalized propagates the resource lookup failure for an unknown key, with and without arguments
 * @modules java.base/sun.security.util
 * @run main LocalizedMessageWitupTest
 */

import java.util.MissingResourceException;
import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {

    private static final String MISSING_KEY = "witup.nonexistent.key.localized.message";

    public static void main(String[] args) {
        checkNullArgumentsPropagatesLookupFailure();
        checkEmptyArgumentsPropagatesLookupFailure();
        checkInstanceFormatLocalizedPropagatesLookupFailure();
    }

    private static void checkNullArgumentsPropagatesLookupFailure() {
        boolean thrown = false;
        try {
            LocalizedMessage.getLocalized(MISSING_KEY, (Object[]) null);
        } catch (MissingResourceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected MissingResourceException for null arguments");
        }
    }

    private static void checkEmptyArgumentsPropagatesLookupFailure() {
        boolean thrown = false;
        try {
            LocalizedMessage.getLocalized(MISSING_KEY);
        } catch (MissingResourceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected MissingResourceException for empty arguments");
        }
    }

    private static void checkInstanceFormatLocalizedPropagatesLookupFailure() {
        LocalizedMessage msg = new LocalizedMessage(MISSING_KEY);
        boolean thrown = false;
        try {
            msg.formatLocalized("arg");
        } catch (MissingResourceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected MissingResourceException from formatLocalized");
        }
    }
}
