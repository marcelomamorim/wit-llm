/* @test    @summary Test getIA5String() method of DerInputStream for valid and invalid data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIA5String();
        testInvalidIA5String();
    }

    private static void testValidIA5String() throws IOException {
        byte[] data = {0x16, 0x05, 'H', 'e', 'l', 'l', 'o'}; // Tag 0x16 (IA5String), length 5, "Hello"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getIA5String();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    private static void testInvalidIA5String() {
        byte[] data = {0x16, 0x02, (byte)0xFF, (byte)0xFF}; // Tag 0x16, length 2, invalid IA5 characters
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getIA5String();
            throw new AssertionError("Expected IOException for invalid IA5 characters");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
