/*
 * @test
 * @summary Test sun.security.util.LocalizedMessage.formatLocalized method behavior
 * @run main LocalizedMessageWitupTest
 */

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        testFormatLocalizedBasic();
    }

    private static void testFormatLocalizedBasic() {
        // Create instance with a known key
        sun.security.util.LocalizedMessage lm = new sun.security.util.LocalizedMessage("test.key");

        // Call formatLocalized with no arguments
        String resultNoArgs = lm.formatLocalized();
        if (resultNoArgs == null) {
            throw new RuntimeException("formatLocalized returned null with no arguments");
        }

        // Call formatLocalized with some arguments
        String resultWithArgs = lm.formatLocalized("arg1", 123, null);
        if (resultWithArgs == null) {
            throw new RuntimeException("formatLocalized returned null with arguments");
        }

        // The returned strings should contain the key or be non-empty
        if (resultNoArgs.isEmpty()) {
            throw new RuntimeException("formatLocalized returned empty string with no arguments");
        }
        if (resultWithArgs.isEmpty()) {
            throw new RuntimeException("formatLocalized returned empty string with arguments");
        }

        // Since the actual localization depends on VM boot state and resources,
        // we do not assert exact string content, only non-null and non-empty results.
    }

}
