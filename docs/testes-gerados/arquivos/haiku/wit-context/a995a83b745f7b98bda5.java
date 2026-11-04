/*
 * @test
 * @summary Witup-derived checks for DerInputStream.getDerValue() public behavior
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyInputThrowsIOException();
        testValidEmptySequenceReturnsValue();
        testTruncatedContentThrowsIOException();
        testLengthOfLengthTooLargeThrowsIOException();
    }

    static void testEmptyInputThrowsIOException() {
        expectIOException(new byte[0]);
    }

    static void testValidEmptySequenceReturnsValue() throws IOException {
        DerInputStream in = new DerInputStream(new byte[] {0x30, 0x00});
        DerValue v = in.getDerValue();
        if (v == null) {
            throw new RuntimeException("getDerValue returned null for valid input");
        }
    }

    static void testTruncatedContentThrowsIOException() {
        expectIOException(new byte[] {0x30, 0x05, 0x01});
    }

    static void testLengthOfLengthTooLargeThrowsIOException() {
        expectIOException(new byte[] {0x30, (byte) 0x85, 0x01, 0x02, 0x03, 0x04, 0x05});
    }

    static void expectIOException(byte[] data) {
        try {
            DerInputStream in = new DerInputStream(data);
            in.getDerValue();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }
}
