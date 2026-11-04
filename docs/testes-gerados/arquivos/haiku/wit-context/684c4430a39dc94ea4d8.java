/*
 * @test
 * @summary Witup-generated tests for IOUtils.readNBytes
 * @modules java.base/sun.security.util
 * @run main IOUtilsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import sun.security.util.IOUtils;

public class IOUtilsWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLengthThrowsIOException();
        testZeroLengthReturnsEmptyArray();
        testReadsExactRequestedBytes();
        testPrematureEofThrowsEOFException();
    }

    static void testNegativeLengthThrowsIOException() throws IOException {
        boolean thrown = false;
        try {
            IOUtils.readNBytes(new ByteArrayInputStream(new byte[4]), -1);
        } catch (EOFException e) {
            throw new RuntimeException("unexpected EOFException for negative length", e);
        } catch (IOException e) {
            thrown = true;
            if (!"length cannot be negative: -1".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for negative length");
        }
    }

    static void testZeroLengthReturnsEmptyArray() throws IOException {
        byte[] result = IOUtils.readNBytes(new ByteArrayInputStream(new byte[4]), 0);
        if (result == null || result.length != 0) {
            throw new RuntimeException("expected empty array for length 0");
        }
    }

    static void testReadsExactRequestedBytes() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        byte[] result = IOUtils.readNBytes(new ByteArrayInputStream(data), 3);
        if (result.length != 3 || result[0] != 10 || result[1] != 20 || result[2] != 30) {
            throw new RuntimeException("unexpected bytes read");
        }
    }

    static void testPrematureEofThrowsEOFException() throws IOException {
        boolean thrown = false;
        try {
            IOUtils.readNBytes(new ByteArrayInputStream(new byte[3]), 5);
        } catch (EOFException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected EOFException on premature EOF");
        }
    }
}
