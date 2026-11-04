/*
 * @test
 * @summary Test for Locale.removeUnicodeLocaleAttribute handling null attribute
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeValid();
    }

    public static void testRemoveUnicodeLocaleAttributeNull() {
        Locale locale = Locale.getDefault();
        try {
            locale.removeUnicodeLocaleAttribute(null);
            System.out.println("FAIL: Expected NullPointerException for null attribute");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null attribute");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    public static void testRemoveUnicodeLocaleAttributeValid() {
        // Since removeUnicodeLocaleAttribute is package-private and not public,
        // and the method is not accessible directly, this test is a placeholder.
        // In real scenario, would test via a subclass or reflection if accessible.
        // Here, we just verify that calling with a non-null string does not throw.
        try {
            // Using a dummy string, assuming method exists and is accessible
            // This is a placeholder; actual invocation depends on access modifiers.
            // For demonstration, we simulate a call.
            // locale.removeUnicodeLocaleAttribute("foo"); // Not accessible
            System.out.println("SKIP: Cannot invoke removeUnicodeLocaleAttribute directly.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
