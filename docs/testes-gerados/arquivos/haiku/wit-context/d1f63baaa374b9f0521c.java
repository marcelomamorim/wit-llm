/*
 * @test
 * @summary Witup-derived checks for DerInputStream(byte[], int, int) constructor argument validation
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] der = {0x02, 0x01, 0x05};

        new DerInputStream(der, 0, der.length);
        new DerInputStream(der, 0, 2);
        new DerInputStream(new byte[] {0x05, 0x00}, 0, 2);

        expectIOException(new byte[] {0x02}, 0, 1);
        expectIOException(new byte[] {0x02, 0x01}, 1, 1);
        expectIOException(new byte[] {0x02, 0x01, 0x05, 0x00}, 2, 3);
        expectIOException(new byte[] {0x02, 0x01}, 1, 2);
    }

    private static void expectIOException(byte[] data, int offset, int len) {
        try {
            new DerInputStream(data, offset, len);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for offset=" + offset
                + ", len=" + len + ", data.length=" + data.length);
    }
}
