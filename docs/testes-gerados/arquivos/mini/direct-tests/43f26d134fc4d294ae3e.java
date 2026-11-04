/*
 * @test
 * @summary Test sun.security.util.LocalizedMessage.formatLocalized method
 * @run main sun.security.util.LocalizedMessageWitupTest
 */

public class LocalizedMessageWitupTest {
    public static void main(String[] args) {
        // Create instance with a known key
        LocalizedMessage lm = new LocalizedMessage("test.key");

        // Call formatLocalized with no arguments
        String result1 = lm.formatLocalized();
        if (result1 == null) {
            throw new RuntimeException("formatLocalized returned null with no arguments");
        }

        // Call formatLocalized with one argument
        String result2 = lm.formatLocalized("arg1");
        if (result2 == null) {
            throw new RuntimeException("formatLocalized returned null with one argument");
        }

        // Call formatLocalized with multiple arguments
        String result3 = lm.formatLocalized("arg1", 42, null);
        if (result3 == null) {
            throw new RuntimeException("formatLocalized returned null with multiple arguments");
        }

        // Call formatLocalized with null argument array
        String result4 = lm.formatLocalized((Object[])null);
        if (result4 == null) {
            throw new RuntimeException("formatLocalized returned null with null argument array");
        }

        // Print results to ensure method returns strings
        System.out.println("formatLocalized() = " + result1);
        System.out.println("formatLocalized(\"arg1\") = " + result2);
        System.out.println("formatLocalized(\"arg1\", 42, null) = " + result3);
        System.out.println("formatLocalized((Object[])null) = " + result4);
    }
}
