/* @test    @summary Test DerInputStream.getBytes(byte[] val) method for correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBytesNormal();
        testGetBytesShortRead();
    }

    private static void testGetBytesNormal() throws IOException {
        byte[] data = {0x01, 0x02, 0x03, 0x04};
        DerInputStream dis = new DerInputStream(data);
        byte[] buffer = new byte[data.length];
        dis.getBytes(buffer);
        boolean isEqual = java.util.Arrays.equals(data, buffer);
        if (!isEqual) {
            throw new AssertionError("getBytes did not copy data correctly");
        }
    }

    private static void testGetBytesShortRead() {
        byte[] data = {0x0A, 0x0B};
        try {
            DerInputStream dis = new DerInputStream(data);
            byte[] buffer = {0x00, 0x00, 0x00};
            dis.getBytes(buffer);
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!e.getMessage().equals("Short read of DER octet string")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
