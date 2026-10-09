/* @test
   @summary Tests the mark method of StringReader for valid and invalid inputs.
   @run main StringReaderWitupTest_Pddad2862a3f1
 */
import java.io.StringReader;
import java.io.IOException;
public class StringReaderWitupTest_Pddad2862a3f1 {
    public static void main(String[] args) throws Exception {
        testMarkWithValidInput();
        testMarkWithNegativeInput();
    }

    private static void testMarkWithValidInput() throws IOException {
        String s = "Hello World";
        StringReader sr = new StringReader(s);
        sr.mark(10);
        // No exception expected
        System.out.println("mark with valid input passed");
    }

    private static void testMarkWithNegativeInput() {
        String s = "Test";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println("mark with negative input correctly threw IllegalArgumentException");
        }
    }
}
