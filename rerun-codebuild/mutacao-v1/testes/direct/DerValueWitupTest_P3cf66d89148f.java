/* @test    @summary Test DerValue.getUTF8String() method for correct behavior and exception handling    @run main DerValueWitupTest_P3cf66d89148f */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P3cf66d89148f {

    public static void main(String[] args) {
        testValidUTF8String();
        testInvalidTagThrows();
        System.exit(0);
    }

    private static void testValidUTF8String() {
        try {
            byte[] utf8Bytes = {0x0C, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F}; // Tag=0x0C (UTF8String), length=5, data="Hello"
            DerValue dv = new DerValue(utf8Bytes);
            String result = dv.getUTF8String();
            if (!"Hello".equals(result)) {
                throw new AssertionError("Expected 'Hello', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid UTF8 string test", e);
        }
    }

    private static void testInvalidTagThrows() {
        try {
            byte[] invalidBytes = {0x05, 0x00}; // Tag=0x05 (NULL), length=0
            DerValue dv = new DerValue(invalidBytes);
            dv.getUTF8String();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not UTF-8")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
