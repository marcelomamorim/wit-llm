/* @test    @summary Test for DerValue.getBMPString() method to verify correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidBMPString();
        testInvalidTagThrowsIOException();
    }

    private static void testValidBMPString() {
        try {
            // Create a DerValue with tag BMPString (0x1E) and valid data bytes
            byte tagBMPString = 0x1E;
            byte[] dataBytes = {0x00, 0x41, 0x00, 0x42}; // Represents "AB" in UnicodeBigUnmarked
            DerValue dv = new DerValue(tagBMPString, dataBytes);
            String result = dv.getBMPString();
            if (!"AB".equals(result)) {
                throw new AssertionError("Expected 'AB', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in valid BMPString test: " + e);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            // Create a DerValue with an invalid tag (not BMPString)
            byte invalidTag = 0x05; // NULL tag
            byte[] dataBytes = {0x00};
            DerValue dv = new DerValue(invalidTag, dataBytes);
            dv.getBMPString();
            throw new AssertionError("Expected IOException was not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not BMP")) {
                throw new AssertionError("IOException message mismatch: " + e.getMessage());
            }
        }
    }
}
