/* @test    @summary Test DerValue.getUTF8String() method for normal and exceptional behavior    @run main DerValueWitupTest_P3cf66d89148f */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P3cf66d89148f {

    public static void main(String[] args) {
        testValidUTF8String();
        testInvalidTagThrowsIOException();
    }

    private static void testValidUTF8String() {
        try {
            byte[] utf8Bytes = {0x0C, 0x07, 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x21}; // Tag=12, "Hello!"
            DerValue dv = new DerValue(utf8Bytes);
            String result = dv.getUTF8String();
            if (!"Hello!".equals(result)) {
                throw new AssertionError("Expected 'Hello!', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidUTF8String", e);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            byte[] invalidBytes = {0x02, 0x01, 0x01}; // Tag=2 (INTEGER), data=1
            DerValue dv = new DerValue(invalidBytes);
            dv.getUTF8String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not UTF-8")) {
                throw new AssertionError("IOException message mismatch: " + msg);
            }
        }
    }
}
