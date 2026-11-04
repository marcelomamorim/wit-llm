/*
 * @test
 * @summary Verifies DerInputStream.getEnumerated() decoding and tag error handling
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkValue(new byte[]{0x0A, 0x01, 0x05}, 5);
        checkValue(new byte[]{0x0A, 0x01, 0x00}, 0);
        checkValue(new byte[]{0x0A, 0x01, (byte) 0xFF}, -1);
        checkValue(new byte[]{0x0A, 0x02, 0x01, 0x00}, 256);

        checkTagError(new byte[]{0x02, 0x01, 0x05});
        checkTagError(new byte[]{0x30, 0x00});

        byte[] buf = {0x00, 0x0A, 0x01, 0x07};
        DerInputStream in = new DerInputStream(buf, 1, 3, true);
        int v = in.getEnumerated();
        if (v != 7) {
            throw new RuntimeException("offset variant: expected 7 but got " + v);
        }
    }

    private static void checkValue(byte[] data, int expected) throws IOException {
        DerInputStream in = new DerInputStream(data);
        int v = in.getEnumerated();
        if (v != expected) {
            throw new RuntimeException("expected " + expected + " but got " + v);
        }
    }

    private static void checkTagError(byte[] data) {
        DerInputStream in;
        try {
            in = new DerInputStream(data);
        } catch (IOException e) {
            throw new RuntimeException("unexpected constructor failure", e);
        }
        try {
            in.getEnumerated();
            throw new RuntimeException("expected IOException for wrong tag");
        } catch (IOException expected) {
            // expected: Enumerated tag error
        }
    }
}
