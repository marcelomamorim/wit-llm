/* @test
   @summary Testa o método Long.decode com entradas válidas e inválidas.
   @run main LongWitupTest_Pd9badad1662c
 */
import java.lang.Long;
public class LongWitupTest_Pd9badad1662c {
    public static void main(String[] args) {
        testDecodeValidInputs();
        testDecodeEmptyString();
        testDecodeSignInWrongPosition();
        testDecodeInvalidRadixPrefix();
        testDecodeInvalidNumberFormat();
    }

    static void testDecodeValidInputs() {
        try {
            Long val1 = Long.decode("12345");
            Long val2 = Long.decode("-12345");
            Long val3 = Long.decode("0xFF");
            Long val4 = Long.decode("#FF");
            Long val5 = Long.decode("077");
            Long val6 = Long.decode("+12345");
            assert val1.equals(Long.valueOf(12345)) : "Failed on decimal positive";
            assert val2.equals(Long.valueOf(-12345)) : "Failed on decimal negative";
            assert val3.equals(Long.valueOf(255)) : "Failed on hex with 0x";
            assert val4.equals(Long.valueOf(255)) : "Failed on hex with #";
            assert val5.equals(Long.valueOf(63)) : "Failed on octal";
            assert val6.equals(Long.valueOf(12345)) : "Failed on plus sign";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid inputs", e);
        }
    }

    static void testDecodeEmptyString() {
        try {
            Long.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeSignInWrongPosition() {
        try {
            Long.decode("-+123");
            throw new AssertionError("Expected NumberFormatException for sign in wrong position");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidRadixPrefix() {
        try {
            Long.decode("0xGHI");
            // Should succeed because GHI is invalid, but parse should throw
            throw new AssertionError("Expected NumberFormatException for invalid number");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidNumberFormat() {
        try {
            Long.decode("abc");
            throw new AssertionError("Expected NumberFormatException for non-numeric string");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
