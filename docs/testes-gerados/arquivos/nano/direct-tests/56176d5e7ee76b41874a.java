/* @test    @summary Test getGeneralString() method of DerInputStream for valid and invalid data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidGeneralString();
        testInvalidGeneralString();
    }

    private static void testValidGeneralString() throws IOException {
        byte[] validData = {0x1B, 0x41, 0x42, 0x43}; // Tag 27 (GeneralString), data "ABC"
        DerInputStream dis = new DerInputStream(validData);
        String result = dis.getGeneralString();
        if (!"ABC".equals(result)) {
            throw new AssertionError("Expected 'ABC', got: " + result);
        }
    }

    private static void testInvalidGeneralString() {
        byte[] invalidData = {0x00, 0x41, 0x42}; // Invalid tag 0
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
