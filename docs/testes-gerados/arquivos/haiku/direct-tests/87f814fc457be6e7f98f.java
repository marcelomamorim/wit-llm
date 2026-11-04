/*
 * @test
 * @summary Behavioral tests for LineNumberReader.skip(long) using only public API
 * @run main LineNumberReaderWitupTest
 */

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

public class LineNumberReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeSkipThrows();
        testZeroSkipReturnsZero();
        testSkipReturnsRequestedCount();
        testSkipThenReadContinuesAtCorrectPosition();
        testSkipBeyondEndReturnsAvailableCount();
        testSkipOnEmptyReaderReturnsZero();
        testSkipAfterReadContinuesCorrectly();
        testSkipLargerThanInternalBufferLimit();
        testSkipUpdatesLineNumberForTerminators();
    }

    static void testNegativeSkipThrows() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        boolean thrown = false;
        try {
            r.skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for negative skip");
        }
    }

    static void testZeroSkipReturnsZero() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        long n = r.skip(0L);
        if (n != 0L) {
            throw new RuntimeException("Expected 0, got " + n);
        }
        if (r.read() != 'a') {
            throw new RuntimeException("Zero skip must not consume characters");
        }
    }

    static void testSkipReturnsRequestedCount() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abcdef"));
        long n = r.skip(3L);
        if (n != 3L) {
            throw new RuntimeException("Expected 3, got " + n);
        }
    }

    static void testSkipThenReadContinuesAtCorrectPosition() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abcdef"));
        r.skip(3L);
        if (r.read() != 'd') {
            throw new RuntimeException("Expected 'd' after skipping 3 characters");
        }
    }

    static void testSkipBeyondEndReturnsAvailableCount() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        long n = r.skip(10L);
        if (n != 3L) {
            throw new RuntimeException("Expected 3 skipped at end of stream, got " + n);
        }
        if (r.read() != -1) {
            throw new RuntimeException("Expected end of stream after skipping all data");
        }
    }

    static void testSkipOnEmptyReaderReturnsZero() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader(""));
        long n = r.skip(5L);
        if (n != 0L) {
            throw new RuntimeException("Expected 0 on empty reader, got " + n);
        }
    }

    static void testSkipAfterReadContinuesCorrectly() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("abc"));
        if (r.read() != 'a') {
            throw new RuntimeException("Expected 'a' from first read");
        }
        long n = r.skip(1L);
        if (n != 1L) {
            throw new RuntimeException("Expected 1 skipped, got " + n);
        }
        if (r.read() != 'c') {
            throw new RuntimeException("Expected 'c' after skipping 'b'");
        }
    }

    static void testSkipLargerThanInternalBufferLimit() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append('x');
        }
        LineNumberReader r = new LineNumberReader(new StringReader(sb.toString()));
        long n = r.skip(10000L);
        if (n != 10000L) {
            throw new RuntimeException("Expected 10000 skipped, got " + n);
        }
        if (r.read() != -1) {
            throw new RuntimeException("Expected end of stream after skipping 10000 characters");
        }
    }

    static void testSkipUpdatesLineNumberForTerminators() throws IOException {
        LineNumberReader r = new LineNumberReader(new StringReader("a\nb\nc"));
        long n = r.skip(4L);
        if (n != 4L) {
            throw new RuntimeException("Expected 4 skipped, got " + n);
        }
        if (r.getLineNumber() != 2) {
            throw new RuntimeException("Expected line number 2 after skipping two terminators, got " + r.getLineNumber());
        }
    }
}
