/* @test    @summary Test DerValue.getGeneralString() method for correct ASCII string extraction and exception handling    @run main DerValueWitupTest_P4792ebe39f46 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P4792ebe39f46 {

    public static void main(String[] args) {
        testValidGeneralString();
        testInvalidTagThrows();
    }

    private static void testValidGeneralString() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            DerValue dv = new DerValue(DerValue.tag_GeneralString, dataBytes);
            String result = dv.getGeneralString();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidGeneralString", e);
        }
    }

    private static void testInvalidTagThrows() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue((byte)0xFF, dataBytes); // invalid tag
            dv.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not GeneralString")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
