/*
 * @test
 * @summary Test sun.security.util.DerValue.getIA5String() behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetIA5StringWithValidTag();
        testGetIA5StringWithInvalidTag();
    }

    private static void testGetIA5StringWithValidTag() throws Exception {
        // tag_IA5String is 22 (0x16) as per DER universal tag for IA5String
        byte tagIA5 = 22;
        String asciiStr = "HelloIA5";
        DerValue dv = new DerValue(tagIA5, asciiStr);
        String result = dv.getIA5String();
        if (!asciiStr.equals(result)) {
            throw new AssertionError("Expected IA5String '" + asciiStr + "' but got '" + result + "'");
        }
    }

    private static void testGetIA5StringWithInvalidTag() {
        // Use a tag different from 22 to trigger IOException
        byte invalidTag = 0x01; // tag_Boolean
        String dummy = "dummy";
        DerValue dv = new DerValue(invalidTag, dummy);
        try {
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not IA5")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
