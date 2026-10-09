/* @test
   @summary Testa o método skip de BufferedReader para comportamentos básicos e limites.
   @run main BufferedReaderWitupTest_Pcdb77716c6ea
 */
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_Pcdb77716c6ea {
    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
        testSkipBeyondEnd();
    }

    private static void testSkipNegative() {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.skip(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(0);
        if (skipped != 0) {
            throw new AssertionError("Skip zero should return 0, got " + skipped);
        }
        int ch = br.read();
        if (ch != 'a') {
            throw new AssertionError("Expected to read 'a', got " + (char) ch);
        }
    }

    private static void testSkipPositive() throws IOException {
        StringReader sr = new StringReader("abcdef");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(2);
        if (skipped != 2) {
            throw new AssertionError("Expected to skip 2, got " + skipped);
        }
        int ch = br.read();
        if (ch != 'c') {
            throw new AssertionError("Expected to read 'c', got " + (char) ch);
        }
    }

    private static void testSkipBeyondEnd() throws IOException {
        StringReader sr = new StringReader("xyz");
        BufferedReader br = new BufferedReader(sr);
        long skipped = br.skip(10);
        if (skipped != 3) {
            throw new AssertionError("Skipping beyond end should skip remaining chars, expected 3, got " + skipped);
        }
        int ch = br.read();
        if (ch != -1) {
            throw new AssertionError("Expected end of stream (-1), got " + ch);
        }
    }
}
