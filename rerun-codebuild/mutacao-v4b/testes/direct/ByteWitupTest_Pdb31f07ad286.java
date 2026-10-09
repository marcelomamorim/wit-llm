/* @test
   @summary Tests Byte.decode(String) for valid and invalid inputs.
   @run main ByteWitupTest_Pdb31f07ad286
 */
import java.lang.Byte;
public class ByteWitupTest_Pdb31f07ad286 {
    public static void main(String[] args) {
        testValidDecodes();
        testInvalidDecodes();
    }
    private static void testValidDecodes() {
        String[] validInputs = {"127", "-128", "0", "  42", "+42", "0x7F", "0x80", "0x00"};
        for (String s : validInputs) {
            try {
                Byte b = Byte.decode(s);
                System.out.println("decode(" + s + ") = " + b);
            } catch (NumberFormatException e) {
                throw new AssertionError("Expected valid decode for: " + s, e);
            }
        }
    }
    private static void testInvalidDecodes() {
        String[] invalidInputs = {"abc", "128", "-129", "0xGHI", "0x80000000", ""};
        for (String s : invalidInputs) {
            try {
                Byte.decode(s);
                throw new AssertionError("Expected NumberFormatException for: " + s);
            } catch (NumberFormatException e) {
                // expected
            }
        }
    }
}
