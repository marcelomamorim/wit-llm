/* @test    @summary Test DerValue.getIA5String() method for correct behavior and exception handling    @run main DerValueWitupTest_P3ea94df657c7 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P3ea94df657c7 {

    public static void main(String[] args) {
        testValidIA5String();
        testInvalidTagThrows();
    }

    private static void testValidIA5String() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            // tag for IA5String is 0x16 (22 in decimal)
            byte ia5Tag = 0x16;
            DerValue dv = new DerValue(ia5Tag, dataBytes);
            String result = dv.getIA5String();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid IA5String test", e);
        }
    }

    private static void testInvalidTagThrows() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            // Use a tag that is not IA5String, e.g., 0x01 (BOOLEAN)
            byte invalidTag = 0x01;
            DerValue dv = new DerValue(invalidTag, dataBytes);
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not IA5")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
            // Expected exception, test passes
        }
    }
}
