/*
 * @test
 * @summary Test sun.security.util.DerValue.getBMPString() method behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBMPStringWithValidBMPStringTag();
        testGetBMPStringWithInvalidTag();
    }

    private static void testGetBMPStringWithValidBMPStringTag() throws Exception {
        // Construct a DerValue with tag_BMPString and valid BMPString bytes
        // tag_BMPString is 0x1E according to DerValue source
        byte tagBMPString = DerValue.tag_BMPString;
        // The UTF-16BE encoding of "AB" is 0x0041 0x0042
        byte[] bmpStringBytes = new byte[] {0x00, 0x41, 0x00, 0x42};

        DerValue dv = new DerValue(tagBMPString, bmpStringBytes);

        String result = dv.getBMPString();

        if (!"AB".equals(result)) {
            throw new RuntimeException("Expected 'AB' but got '" + result + "'");
        }
    }

    private static void testGetBMPStringWithInvalidTag() {
        // Construct a DerValue with a tag different from tag_BMPString
        byte invalidTag = DerValue.tag_Integer; // 0x02
        byte[] dummyData = new byte[] {0x01};

        DerValue dv = new DerValue(invalidTag, dummyData);

        try {
            dv.getBMPString();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not BMP")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
