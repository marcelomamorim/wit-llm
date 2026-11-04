/*
 * @test
 * @summary Test sun.security.util.DerValue.getBMPString() behavior and exception
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBMPStringWithValidTag();
        testGetBMPStringWithInvalidTag();
    }

    private static void testGetBMPStringWithValidTag() throws Exception {
        // tag_BMPString is 30 (0x1E)
        byte tagBMPString = 30;
        // Construct a DerValue with tag = tag_BMPString and valid BMPString bytes
        // The constructor DerValue(byte tag, byte[] data) sets tag and data
        // Use a simple BMPString "AB" encoded in UnicodeBigUnmarked (UTF-16BE)
        byte[] bmpStringBytes = new byte[] {0, 'A', 0, 'B'}; // 'A' and 'B' in UTF-16BE
        DerValue dv = new DerValue(tagBMPString, bmpStringBytes);

        String result = dv.getBMPString();
        if (!"AB".equals(result)) {
            throw new RuntimeException("Expected 'AB' but got '" + result + "'");
        }
    }

    private static void testGetBMPStringWithInvalidTag() {
        // Use a tag different from tag_BMPString (30)
        byte invalidTag = DerValue.tag_Integer; // 2
        byte[] dummyData = new byte[] {0x00};
        DerValue dv = new DerValue(invalidTag, dummyData);

        try {
            dv.getBMPString();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not BMP")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
