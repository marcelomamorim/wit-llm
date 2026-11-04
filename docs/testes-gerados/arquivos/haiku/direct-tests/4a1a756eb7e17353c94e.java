/*
 * @test
 * @summary Verifies DerInputStream.getInteger decodes DER INTEGER values and rejects a wrong tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new byte[]{0x02, 0x01, 0x05}, 5);
        check(new byte[]{0x02, 0x01, 0x00}, 0);
        check(new byte[]{0x02, 0x01, (byte) 0xFF}, -1);
        check(new byte[]{0x02, 0x02, 0x00, (byte) 0x80}, 128);
        checkIOException(new byte[]{0x04, 0x01, 0x05});
        checkIOException(new byte[0]);
    }

    static void check(byte[] data, int expected) throws IOException {
        int value = new DerInputStream(data).getInteger();
        if (value != expected) {
            throw new RuntimeException("expected " + expected + " but got " + value);
        }
    }

    static void checkIOException(byte[] data) {
        try {
            new DerInputStream(data).getInteger();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("IOException expected");
    }
}
