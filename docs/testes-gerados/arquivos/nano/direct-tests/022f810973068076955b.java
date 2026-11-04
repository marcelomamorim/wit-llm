/* @test    @summary Test DerValue.getUTF8String() method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidUTF8String();
        testInvalidTagThrows();
    }

    static void testValidUTF8String() {
        try {
            byte[] utf8Bytes = {0x0C, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F}; // Tag=0x0C (UTF8String), "Hello"
            DerValue dv = new DerValue(utf8Bytes);
            String result = dv.getUTF8String();
            if (!"Hello".equals(result)) {
                throw new AssertionError("Expected 'Hello', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testInvalidTagThrows() {
        try {
            byte[] invalidBytes = {0x05, 0x00}; // Tag=0x05 (NULL), no data
            DerValue dv = new DerValue(invalidBytes);
            dv.getUTF8String();
            throw new AssertionError("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not UTF-8")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
