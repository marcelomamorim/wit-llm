/* @test    @summary Test for DerValue.getUTF8String() method to verify correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetUTF8StringWithValidTag();
        testGetUTF8StringWithInvalidTag();
    }

    private static void testGetUTF8StringWithValidTag() {
        try {
            byte[] utf8Bytes = "test string".getBytes("UTF8");
            // Create DerValue with tag for UTF8String (0x0C)
            DerValue dv = new DerValue((byte)0x0C, utf8Bytes);
            String result = dv.getUTF8String();
            if (!"test string".equals(result)) {
                throw new AssertionError("Expected 'test string', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testGetUTF8StringWithInvalidTag() {
        try {
            // Create DerValue with a tag different from UTF8String (e.g., 0x01 for BOOLEAN)
            byte[] data = {0x01, 0x01, 0x00};
            DerValue dv = new DerValue(data);
            dv.getUTF8String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("DerValue.getUTF8String, not UTF-8")) {
                throw new AssertionError("Unexpected IOException message: " + message);
            }
        }
    }
}
