/*
 * @test
 * @summary Tests DerInputStream.getBitString for a valid DER BIT STRING and for a wrong tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] valid = {0x03, 0x02, 0x00, (byte) 0xAB};
        DerInputStream in = new DerInputStream(valid);
        byte[] bits = in.getBitString();
        if (bits.length != 1 || bits[0] != (byte) 0xAB) {
            throw new RuntimeException("unexpected bit string content");
        }

        byte[] wrongTag = {0x02, 0x01, 0x00};
        boolean thrown = false;
        try {
            new DerInputStream(wrongTag).getBitString();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for wrong tag");
        }
    }
}
