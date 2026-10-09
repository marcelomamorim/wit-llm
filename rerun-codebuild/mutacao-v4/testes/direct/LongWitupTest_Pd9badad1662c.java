/* @test
   @summary Testa o método Long.decode(String).
   @run main LongWitupTest_Pd9badad1662c
 */
import java.lang.Long;
public class LongWitupTest_Pd9badad1662c {
    public static void main(String[] args) {
        testDecodeDecimal();
        testDecodeHexadecimal();
        testDecodeOctal();
        testDecodeWithSign();
        testDecodeInvalidSignPosition();
        testDecodeEmptyString();
        testDecodeInvalidNumberFormat();
    }

    static void testDecodeDecimal() {
        Long result = Long.decode("12345");
        assert result.equals(Long.valueOf(12345)) : "Failed decimal decode";
    }

    static void testDecodeHexadecimal() {
        Long result = Long.decode("0x1A3F");
        assert result.equals(Long.valueOf(0x1A3F)) : "Failed hexadecimal decode";
        result = Long.decode("0XFF");
        assert result.equals(Long.valueOf(0xFF)) : "Failed hexadecimal decode with uppercase X";
        result = Long.decode("#ABC");
        assert result.equals(Long.valueOf(0xABC)) : "Failed hexadecimal decode with #";
    }

    static void testDecodeOctal() {
        Long result = Long.decode("077");
        assert result.equals(Long.valueOf(077)) : "Failed octal decode";
    }

    static void testDecodeWithSign() {
        Long result = Long.decode("-9876");
        assert result.equals(Long.valueOf(-9876)) : "Failed negative decimal decode";
        result = Long.decode("+54321");
        assert result.equals(Long.valueOf(54321)) : "Failed positive sign decode";
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Long.decode("-+123");
            throw new AssertionError("Expected NumberFormatException for invalid sign position");
        } catch (NumberFormatException e) {
            // expected
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

    static void testDecodeInvalidNumberFormat() {
        try {
            Long.decode("notANumber");
            throw new AssertionError("Expected NumberFormatException for invalid number");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
