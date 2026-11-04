/*
 * @test
 * @summary Verifies that LocalizedMessage.formatLocalized does not raise InternalError on a booted VM and returns a non-null result or a MissingResourceException for an absent key
 * @modules java.base/sun.security.util
 * @run main LocalizedMessageWitupTest
 */

import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        LocalizedMessage msg = new LocalizedMessage("witup.nonexistent.key");
        try {
            String s = msg.formatLocalized("a", 1);
            if (s == null) {
                throw new RuntimeException("formatLocalized returned null");
            }
        } catch (java.util.MissingResourceException e) {
            // absent key in bundle is an accepted outcome
        }

        LocalizedMessage noArgs = new LocalizedMessage("witup.nonexistent.key");
        try {
            noArgs.formatLocalized();
        } catch (java.util.MissingResourceException e) {
            // absent key in bundle is an accepted outcome
        }
    }
}
