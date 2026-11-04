/*
 * @test
 * @summary Verifies skip on the Reader returned by Reader.nullReader(): returns 0 while open and throws IOException after close
 * @run main ReaderWitupTest
 */
import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipWhileOpenReturnsZero();
        testSkipAfterCloseThrowsIOException();
        testSkipZeroWhileOpenReturnsZero();
    }

    static void testSkipWhileOpenReturnsZero() throws IOException {
        Reader r = Reader.nullReader();
        long skipped = r.skip(10L);
        check(skipped == 0L, "skip(10) on open nullReader should return 0 but was " + skipped);
        r.close();
    }

    static void testSkipZeroWhileOpenReturnsZero() throws IOException {
        Reader r = Reader.nullReader();
        long skipped = r.skip(0L);
        check(skipped == 0L, "skip(0) on open nullReader should return 0 but was " + skipped);
        r.close();
    }

    static void testSkipAfterCloseThrowsIOException() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        boolean thrown = false;
        try {
            r.skip(5L);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "skip after close should throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
