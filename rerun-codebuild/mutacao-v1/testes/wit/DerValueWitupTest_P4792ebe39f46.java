/* @test    @summary Test for DerValue.getGeneralString() method to verify exception handling and normal behavior    @run main DerValueWitupTest_P4792ebe39f46 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P4792ebe39f46 {

    public static void main(String[] args) {
        testGetGeneralStringWithInvalidTag();
        testGetGeneralStringWithValidTag();
    }

    private static void testGetGeneralStringWithInvalidTag() {
        try {
            // Create a DerValue with a tag different from tag_GeneralString (assumed to be 0x1A or 26)
            byte invalidTag = 0x1B; // 27 in decimal, which is the tag that triggers IOException
            byte[] dataBytes = {0x01, 0x02}; // arbitrary data
            DerValue dv = new DerValue(invalidTag, dataBytes);
            dv.getGeneralString();
            throw new AssertionError("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("not GeneralString")) {
                throw new AssertionError("Unexpected IOException message: " + message);
            }
            // Test passes if IOException with expected message is thrown
        }
    }

    private static void testGetGeneralStringWithValidTag() {
        try {
            // Create a DerValue with a valid tag for GeneralString (assumed to be 0x1A or 26)
            byte validTag = 0x1A; // 26 in decimal
            byte[] dataBytes = {0x41, 0x42, 0x43}; // "ABC" in ASCII
            DerValue dv = new DerValue(validTag, dataBytes);
            String result = dv.getGeneralString();
            if (!"ABC".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }
}
