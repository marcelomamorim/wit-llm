/*
 * @test
 * @summary Test java.lang.Short.decode(String) for valid and out-of-range inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testDecodeValid();
        testDecodeOutOfRange();
    }

    private static void testDecodeValid() {
        // Valid inputs within short range
        String[] validInputs = {"0", "32767", "-32768", "+123", "-0", "0x7FFF", "0x8000", "0777"};
        for (String input : validInputs) {
            try {
                Short result = Short.decode(input);
                int val = result.shortValue();
                if (val < Short.MIN_VALUE || val > Short.MAX_VALUE) {
                    throw new RuntimeException("decode returned out-of-range value for input: " + input);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("decode threw NumberFormatException for valid input: " + input, e);
            }
        }
    }

    private static void testDecodeOutOfRange() {
        // Inputs that decode to int outside short range should throw NumberFormatException
        String[] outOfRangeInputs = {"32768", "-32769", "0x10000", "-0x8001", "40000", "-40000"};
        for (String input : outOfRangeInputs) {
            boolean thrown = false;
            try {
                Short.decode(input);
            } catch (NumberFormatException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("decode did not throw NumberFormatException for out-of-range input: " + input);
            }
        }
    }
}
