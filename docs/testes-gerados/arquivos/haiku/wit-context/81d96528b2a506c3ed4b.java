/*
 * @test
 * @summary Verifies DecInputStream.available() via public Base64.Decoder.wrap(): delegation to the underlying stream and IOException after close
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableDelegatesToUnderlyingStream();
        testAvailableZeroForEmptyUnderlyingStream();
        testAvailableThrowsIOExceptionAfterClose();
    }

    private static void testAvailableDelegatesToUnderlyingStream() throws IOException {
        byte[] data = "QUJDREVG".getBytes("US-ASCII");
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        InputStream in = Base64.getDecoder().wrap(bais);
        int avail = in.available();
        if (avail != bais.available() || avail != 8) {
            throw new RuntimeException("Expected 8 available bytes, got " + avail);
        }
    }

    private static void testAvailableZeroForEmptyUnderlyingStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        InputStream in = Base64.getDecoder().wrap(bais);
        int avail = in.available();
        if (avail != 0) {
            throw new RuntimeException("Expected 0 available bytes, got " + avail);
        }
    }

    private static void testAvailableThrowsIOExceptionAfterClose() throws IOException {
        byte[] data = "QUJD".getBytes("US-ASCII");
        InputStream in = Base64.getDecoder().wrap(new ByteArrayInputStream(data));
        in.close();
        try {
            in.available();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException from available() after close()");
    }
}
