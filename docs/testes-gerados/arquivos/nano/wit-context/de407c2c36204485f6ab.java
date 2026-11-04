/* @test    @summary Test getIA5String method for DerInputStream with various scenarios    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidIA5String();
        testReadStringEOF();
        testReadStringInvalidTag();
    }

    private static void testValidIA5String() throws IOException {
        byte[] data = new byte[] {0x16, 0x03, 0x41, 0x42, 0x43}; // Tag=0x16 (IA5), length=3, data="ABC"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getIA5String();
        if (!"ABC".equals(result)) {
            throw new AssertionError("Expected 'ABC', got: " + result);
        }
    }

    private static void testReadStringEOF() {
        byte[] data = new byte[] {0x16, 0x01}; // Tag=0x16, length=1, but no data
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getIA5String();
            throw new AssertionError("Expected IOException due to EOF");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testReadStringInvalidTag() throws IOException {
        byte[] data = new byte[] {0x02, 0x01, 0x01}; // Tag=0x02 (INTEGER), length=1
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getIA5String();
        if (result != null) {
            throw new AssertionError("Expected null or exception for invalid tag");
        }
    }
}
