/* @test
   @summary Testa o método Short.decode(String) para comportamentos observáveis.
   @run main ShortWitupTest_Pc4ba3532b728
 */
import java.lang.Short;
public class ShortWitupTest_Pc4ba3532b728 {
    public static void main(String[] args) {
        testDecodeValidRange();
        testDecodeBelowRange();
        testDecodeAboveRange();
        testDecodeInvalidFormat();
    }

    static void testDecodeValidRange() {
        try {
            Short s1 = Short.decode("32767");
            assert s1 != null && s1.shortValue() == 32767 : "Failed on max value";
            Short s2 = Short.decode("-32768");
            assert s2 != null && s2.shortValue() == -32768 : "Failed on min value";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid range", e);
        }
    }

    static void testDecodeBelowRange() {
        try {
            Short.decode("-32769");
            throw new AssertionError("Expected NumberFormatException for below range");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testDecodeAboveRange() {
        try {
            Short.decode("32768");
            throw new AssertionError("Expected NumberFormatException for above range");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testDecodeInvalidFormat() {
        try {
            Short.decode("notANumber");
            throw new AssertionError("Expected NumberFormatException for invalid format");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
