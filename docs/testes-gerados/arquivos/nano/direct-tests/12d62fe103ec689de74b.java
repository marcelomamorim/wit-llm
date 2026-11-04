/* @test    @summary Test DerInputStream.getOctetString() method for correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOctetString();
        testInvalidTag();
        testShortRead();
    }

    private static void testValidOctetString() throws IOException {
        byte[] data = new byte[] {
            0x04, 0x03, 0x01, 0x02, 0x03
        };
        DerInputStream dis = new DerInputStream(data);
        byte[] result = dis.getOctetString();
        assert result.length == 3 : "Expected length 3";
        assert result[0] == 0x01 && result[1] == 0x02 && result[2] == 0x03 : "Unexpected octet string content";
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] {
            0x05, 0x00
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getOctetString();
            assert false : "Expected IOException due to invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("not an octet string") : "Unexpected exception message: " + e.getMessage();
        }
    }

    private static void testShortRead() throws IOException {
        byte[] data = new byte[] {
            0x04, 0x02, 0x01
        };
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getOctetString();
            assert false : "Expected IOException due to short read";
        } catch (IOException e) {
            assert e.getMessage().contains("Short read") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
