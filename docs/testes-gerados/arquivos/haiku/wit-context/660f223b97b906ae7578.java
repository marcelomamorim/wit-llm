/*
 * @test
 * @summary Checks DerInputStream.getBMPString() decoding and IOException behavior on invalid DER input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkValid(new byte[] {0x1E, 0x04, 0x00, 0x41, 0x00, 0x42}, "AB");
        checkValid(new byte[] {0x1E, 0x00}, "");
        checkValid(new byte[] {0x1E, 0x02, 0x00, 0x7A}, "z");
        checkIOException(new byte[] {0x0C, 0x02, 0x00, 0x41});
        checkIOException(new byte[] {0x1E, 0x04, 0x00, 0x41});
        checkIOException(new byte[0]);
    }

    static void checkValid(byte[] data, String expected) throws IOException {
        DerInputStream in = new DerInputStream(data);
        String actual = in.getBMPString();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void checkIOException(byte[] data) {
        try {
            new DerInputStream(data).getBMPString();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for invalid DER input");
    }
}
