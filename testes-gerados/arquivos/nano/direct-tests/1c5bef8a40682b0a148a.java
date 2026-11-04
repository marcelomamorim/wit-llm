/* @test    @summary Test Norm2AllModes.normalize with valid and invalid inputs    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;
import java.io.IOException;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testNormalizeValidInput();
        testNormalizeNullDest();
        testNormalizeSameObjectDest();
    }

    static void testNormalizeValidInput() {
        try {
            String input = "abc";
            StringBuilder dest = new StringBuilder();
            Appendable result = Norm2AllModes.normalize(input, dest);
            assert result == dest : "Result should be the same as dest";
            assert dest.toString().equals(input) : "Normalized string should equal input";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNormalizeValidInput", e);
        }
    }

    static void testNormalizeNullDest() {
        try {
            String input = "abc";
            Norm2AllModes.normalize(input, null);
            throw new AssertionError("Expected NullPointerException for null dest");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNormalizeNullDest", e);
        }
    }

    static void testNormalizeSameObjectDest() {
        try {
            String input = "abc";
            StringBuilder dest = new StringBuilder();
            // Intentionally passing same object for src and dest to trigger exception
            Norm2AllModes.normalize(input, dest);
            // Now test with dest as StringBuilder, which is not same as input
            // but to simulate same object, pass dest as dest
            Norm2AllModes.normalize(dest, dest);
            throw new AssertionError("Expected IllegalArgumentException for same object dest");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNormalizeSameObjectDest", e);
        }
    }
}
