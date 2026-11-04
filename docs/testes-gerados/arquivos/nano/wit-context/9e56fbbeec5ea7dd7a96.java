/* @test    @summary Test for Integer.parseUnsignedInt(String s) with null input to verify NullPointerException is thrown.    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {
    public static void main(String[] args) {
        testParseUnsignedIntNull();
    }

    public static void testParseUnsignedIntNull() {
        try {
            Integer.parseUnsignedInt(null);
            System.err.println("Failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }
}
