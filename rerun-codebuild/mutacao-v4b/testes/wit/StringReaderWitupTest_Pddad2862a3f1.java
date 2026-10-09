/* @test
   @summary Testa o método mark de StringReader para diferentes valores de readAheadLimit.
   @run main StringReaderWitupTest_Pddad2862a3f1
 */
import java.io.StringReader;
import java.io.IOException;
public class StringReaderWitupTest_Pddad2862a3f1 {
    public static void main(String[] args) throws Exception {
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
        testMarkWithPositiveLimit();
    }

    private static void testMarkWithNegativeLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            // expected
            if (!e.getMessage().contains("Read-ahead limit < 0")) {
                throw new AssertionError("Unexpected message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testMarkWithZeroLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(0);
            // no exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero limit", e);
        }
    }

    private static void testMarkWithPositiveLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(10);
            // no exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive limit", e);
        }
    }
}
