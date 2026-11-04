/*
 * @test
 * @summary Tests BufferedReader.mark(int) argument validation, closed-stream behavior and mark/reset round trip
 * @run main BufferedReaderWitupTest
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLimitThrows();
        testZeroLimitAllowed();
        testMarkResetRestoresChars();
        testMarkAtEndResetsToEnd();
        testMarkOnClosedThrows();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeLimitThrows() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        boolean thrown = false;
        try {
            br.mark(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "mark(-1) must throw IllegalArgumentException");
        check(br.read() == 'a', "reader must remain usable after rejected mark");
        br.close();
    }

    static void testZeroLimitAllowed() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        br.mark(0);
        check(br.read() == 'a', "mark(0) must be accepted and read must return 'a'");
        br.close();
    }

    static void testMarkResetRestoresChars() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("hello"));
        check(br.read() == 'h', "first char should be 'h'");
        br.mark(10);
        check(br.read() == 'e', "char after mark should be 'e'");
        check(br.read() == 'l', "second char after mark should be 'l'");
        br.reset();
        check(br.read() == 'e', "after reset, read should return 'e' again");
        check(br.read() == 'l', "after reset, next read should return 'l'");
        check(br.read() == 'l', "third char should be 'l'");
        check(br.read() == 'o', "fourth char should be 'o'");
        check(br.read() == -1, "end of stream expected");
        br.close();
    }

    static void testMarkAtEndResetsToEnd() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("ab"));
        check(br.read() == 'a', "first char should be 'a'");
        check(br.read() == 'b', "second char should be 'b'");
        br.mark(1);
        check(br.read() == -1, "end of stream expected after mark at end");
        br.reset();
        check(br.read() == -1, "reset to end mark should still report end of stream");
        br.close();
    }

    static void testMarkOnClosedThrows() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("abc"));
        br.close();
        boolean thrown = false;
        try {
            br.mark(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "mark on closed reader must throw IOException");
    }
}
