/*
 * @test
 * @summary Behavioral tests for Integer.parseUnsignedInt(String) using only public observable results
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {

    public static void main(String[] args) {
        int failures = 0;

        failures += expectValue("0", 0);
        failures += expectValue("1", 1);
        failures += expectValue("2147483647", 2147483647);
        failures += expectValue("2147483648", -2147483648);
        failures += expectValue("4294967295", -1);
        failures += expectValue("+5", 5);

        failures += expectNFE("4294967296");
        failures += expectNFE("-1");
        failures += expectNFE("-0");
        failures += expectNFE("+");
        failures += expectNFE("");
        failures += expectNFE("abc");
        failures += expectNFE("12a");
        failures += expectNFE(null);

        if (failures != 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static int expectValue(String input, int expected) {
        try {
            int actual = Integer.parseUnsignedInt(input);
            if (actual != expected) {
                System.err.println("parseUnsignedInt(\"" + input + "\") expected "
                        + expected + " but was " + actual);
                return 1;
            }
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Unexpected NumberFormatException for \"" + input + "\": " + e);
            return 1;
        }
    }

    private static int expectNFE(String input) {
        try {
            int actual = Integer.parseUnsignedInt(input);
            System.err.println("Expected NumberFormatException for \"" + input
                    + "\" but returned " + actual);
            return 1;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
