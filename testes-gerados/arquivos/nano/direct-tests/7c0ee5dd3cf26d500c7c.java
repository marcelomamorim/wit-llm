/* @test    @summary Test DerValue.getBMPString() method for correct behavior and exception handling    @run main DerValueWitupTest */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidBMPString();
        testInvalidTag();
    }

    private static void testValidBMPString() {
        try {
            byte[] bmpBytes = {0x00, 0x41, 0x00, 0x42, 0x00, 0x43}; // Represents "ABC" in BMPString (big endian)
            DerValue dv = new DerValue(bmpBytes);
            dv.tag = DerValue.tag_BMPString; // Set tag to BMPString
            String result = dv.getBMPString();
            if (!"ABC".equals(result)) {
                throw new AssertionError("Expected 'ABC', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testInvalidTag() {
        try {
            byte[] data = {0x00, 0x41};
            DerValue dv = new DerValue(data);
            dv.tag = 0x10; // Invalid tag for BMPString
            dv.getBMPString();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            if (!e.getMessage().contains("DerValue.getBMPString, not BMP")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
