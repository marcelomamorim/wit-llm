/* @test    @summary Test for Integer.decode to verify handling of empty string input and exception throwing    @run main IntegerWitupTest_P0148f88bc177 */

import java.lang.Integer;

public class IntegerWitupTest_P0148f88bc177 {
    public static void main(String[] args) {
        testDecodeEmptyString();
    }

    private static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
