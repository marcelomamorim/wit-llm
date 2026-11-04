/*
 * @test
 * @summary Verify DerInputStream(byte[]) rejects null and inputs shorter than two bytes, and accepts a minimal two-byte input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        expectIOException(new byte[0]);
        expectIOException(new byte[1]);
        expectNullPointerException();
        new DerInputStream(new byte[] {0x05, 0x00});
    }

    private static void expectIOException(byte[] data) {
        try {
            new DerInputStream(data);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for length " + data.length);
    }

    private static void expectNullPointerException() {
        try {
            new DerInputStream(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null input", e);
        }
        throw new RuntimeException("Expected NullPointerException for null input");
    }
}
