/*
 * @test
 * @summary Black-box checks of CipherInputStream.read() using a pass-through NullCipher
 * @run main CipherInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.CipherInputStream;
import javax.crypto.NullCipher;

public class CipherInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyInputReturnsMinusOne();
        testSequentialBytesAndEof();
        testReturnedValueIsUnsigned();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyInputReturnsMinusOne() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try (CipherInputStream cis = new CipherInputStream(in, new NullCipher())) {
            check(cis.read() == -1, "empty input must yield -1");
        }
    }

    static void testSequentialBytesAndEof() throws IOException {
        byte[] data = {0x01, 0x02, 0x03};
        InputStream in = new ByteArrayInputStream(data);
        try (CipherInputStream cis = new CipherInputStream(in, new NullCipher())) {
            check(cis.read() == 1, "first byte mismatch");
            check(cis.read() == 2, "second byte mismatch");
            check(cis.read() == 3, "third byte mismatch");
            check(cis.read() == -1, "expected end of stream");
        }
    }

    static void testReturnedValueIsUnsigned() throws IOException {
        byte[] data = {(byte) 0xFF, (byte) 0x80};
        InputStream in = new ByteArrayInputStream(data);
        try (CipherInputStream cis = new CipherInputStream(in, new NullCipher())) {
            check(cis.read() == 255, "0xFF must be returned as 255");
            check(cis.read() == 128, "0x80 must be returned as 128");
            check(cis.read() == -1, "expected end of stream after unsigned values");
        }
    }
}
