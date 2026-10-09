/* @test
   @summary Test BufferedReader.mark() method for correct behavior and exception handling.
   @run main BufferedReaderWitupTest_P61ac87410b78
 */
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderWitupTest_P61ac87410b78 {
    public static void main(String[] args) {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
    }

    private static void testMarkWithValidLimit() {
        String data = "Line1\nLine2\nLine3";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            br.mark(10);
            int ch = br.read();
            if (ch != 'L') {
                throw new AssertionError("Expected 'L' at start after mark, got: " + (char) ch);
            }
            br.reset();
            ch = br.read();
            if (ch != 'L') {
                throw new AssertionError("After reset, expected 'L', got: " + (char) ch);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testMarkWithNegativeLimit() {
        String data = "Test";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            boolean thrown = false;
            try {
                br.mark(-1);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for negative limit");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testMarkWithZeroLimit() {
        String data = "Sample";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            br.mark(0);
            int ch = br.read();
            if (ch != 'S') {
                throw new AssertionError("Expected 'S' at start after mark with zero limit, got: " + (char) ch);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
