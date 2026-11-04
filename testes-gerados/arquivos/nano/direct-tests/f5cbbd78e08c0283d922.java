/* @test    @summary Test Byte.valueOf(String s) method with valid and invalid inputs    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidInputs();
    }

    private static void testValidInputs() {
        String[] validStrings = {"0", "127", "-128", "-1", "100"};
        for (String s : validStrings) {
            Byte b = Byte.valueOf(s);
            assert b != null : "Byte.valueOf(" + s + ") returned null";
            System.out.println("Byte.valueOf(\"" + s + "\") = " + b);
        }
    }

    private static void testInvalidInputs() {
        String[] invalidStrings = {"128", "-129", "abc", "", " "};
        for (String s : invalidStrings) {
            try {
                Byte b = Byte.valueOf(s);
                assert false : "Expected NumberFormatException for input: " + s;
            } catch (NumberFormatException e) {
                System.out.println("Correctly caught exception for input: " + s);
            }
        }
    }
}
