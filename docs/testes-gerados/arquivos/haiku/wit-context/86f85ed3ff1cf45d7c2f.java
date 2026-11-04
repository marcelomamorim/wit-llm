/*
 * @test
 * @summary Verifies DerValue.getBoolean() decoding and its IOException paths for wrong tag and wrong length
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        if (!new DerValue(new byte[]{0x01, 0x01, (byte)0xFF}).getBoolean()) {
            throw new RuntimeException("expected true for 0xFF");
        }
        if (!new DerValue(new byte[]{0x01, 0x01, 0x01}).getBoolean()) {
            throw new RuntimeException("expected true for 0x01");
        }
        if (new DerValue(new byte[]{0x01, 0x01, 0x00}).getBoolean()) {
            throw new RuntimeException("expected false for 0x00");
        }
        expectIOException(new byte[]{0x02, 0x01, 0x00});
        expectIOException(new byte[]{0x04, 0x01, 0x00});
        expectIOException(new byte[]{0x01, 0x02, 0x00, 0x00});
        expectIOException(new byte[]{0x01, 0x00});
    }

    static void expectIOException(byte[] encoded) throws Exception {
        try {
            new DerValue(encoded).getBoolean();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for encoding");
    }
}
