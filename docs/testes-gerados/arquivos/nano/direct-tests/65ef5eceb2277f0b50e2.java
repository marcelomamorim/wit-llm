/* @test    @summary Test getUTF8String() method of DerInputStream for valid UTF-8 encoded data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidUTF8String();
        testEmptyUTF8String();
        testInvalidUTF8Data();
    }

    static void testValidUTF8String() throws IOException {
        byte[] utf8Data = {0x0C, 0x05, 72, 101, 108, 108, 111}; // Tag 12 (UTF8String), length 5, "Hello"
        DerInputStream dis = new DerInputStream(utf8Data);
        String result = dis.getUTF8String();
        assert "Hello".equals(result) : "Expected 'Hello', got: " + result;
    }

    static void testEmptyUTF8String() throws IOException {
        byte[] utf8Data = {0x0C, 0x00}; // Tag 12, length 0
        DerInputStream dis = new DerInputStream(utf8Data);
        String result = dis.getUTF8String();
        assert "".equals(result) : "Expected empty string, got: " + result;
    }

    static void testInvalidUTF8Data() {
        byte[] invalidData = {0x0C, 0x02, (byte)0xFF, 0x00}; // Invalid UTF-8 bytes
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getUTF8String();
            assert false : "Expected IOException for invalid UTF-8 data";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
