/*
 * @test
 * @summary Tests IOUtils.readNBytes observable behavior for negative, zero, prefix, premature EOF and buffer growth cases
 * @modules java.base/sun.security.util
 * @run main IOUtilsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.IOUtils;

public class IOUtilsWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLengthThrowsIOException();
        testZeroLengthReturnsEmptyArray();
        testReadsOnlyRequestedPrefix();
        testExactLengthReadsAllBytes();
        testPrematureEofThrowsEOFException();
        testLargerLengthAcrossBufferGrowth();
    }

    static void testNegativeLengthThrowsIOException() {
        try {
            IOUtils.readNBytes(new ByteArrayInputStream(new byte[4]), -1);
            throw new AssertionError("expected IOException for negative length");
        } catch (IOException e) {
            if (e instanceof EOFException) {
                throw new AssertionError("expected plain IOException, got EOFException");
            }
            if (e.getMessage() == null || !e.getMessage().startsWith("length cannot be negative")) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testZeroLengthReturnsEmptyArray() throws IOException {
        byte[] r = IOUtils.readNBytes(new ByteArrayInputStream(new byte[] {1, 2}), 0);
        if (r.length != 0) {
            throw new AssertionError("expected empty array, length=" + r.length);
        }
    }

    static void testReadsOnlyRequestedPrefix() throws IOException {
        byte[] r = IOUtils.readNBytes(new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5}), 3);
        if (!Arrays.equals(r, new byte[] {1, 2, 3})) {
            throw new AssertionError("unexpected prefix: " + Arrays.toString(r));
        }
    }

    static void testExactLengthReadsAllBytes() throws IOException {
        byte[] expected = {10, 20, 30, 40};
        byte[] r = IOUtils.readNBytes(new ByteArrayInputStream(expected), 4);
        if (!Arrays.equals(r, expected)) {
            throw new AssertionError("unexpected content: " + Arrays.toString(r));
        }
    }

    static void testPrematureEofThrowsEOFException() {
        try {
            IOUtils.readNBytes(new ByteArrayInputStream(new byte[] {1, 2, 3}), 5);
            throw new AssertionError("expected EOFException on premature EOF");
        } catch (EOFException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("expected EOFException, got " + e);
        }
    }

    static void testLargerLengthAcrossBufferGrowth() throws IOException {
        int total = 3000;
        int len = 2500;
        byte[] input = new byte[total];
        for (int i = 0; i < total; i++) {
            input[i] = (byte) (i * 31);
        }
        byte[] r = IOUtils.readNBytes(new ByteArrayInputStream(input), len);
        if (r.length != len) {
            throw new AssertionError("expected length " + len + ", got " + r.length);
        }
        if (!Arrays.equals(r, Arrays.copyOf(input, len))) {
            throw new AssertionError("content mismatch after buffer growth");
        }
    }
}
