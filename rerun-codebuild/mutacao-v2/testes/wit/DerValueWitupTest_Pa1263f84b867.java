/*
 * @test
 * @summary Test sun.security.util.DerValue.getGeneralString() behavior
 * @run main DerValueWitupTest_Pa1263f84b867
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_Pa1263f84b867 {
    public static void main(String[] args) throws Exception {
        testGetGeneralStringWithValidTag();
        testGetGeneralStringWithInvalidTag();
    }

    private static void testGetGeneralStringWithValidTag() throws Exception {
        // tag_GeneralString is 27 (0x1B) as per DerValue source
        byte generalStringTag = 27;
        String testString = "TestASCII";
        // Construct DerValue with tag and ASCII string
        DerValue dv = new DerValue(generalStringTag, testString);

        String result = dv.getGeneralString();
        if (!testString.equals(result)) {
            throw new AssertionError("Expected '" + testString + "' but got '" + result + "'");
        }
    }

    private static void testGetGeneralStringWithInvalidTag() {
        // Use a tag different from 27 to trigger IOException
        byte invalidTag = DerValue.tag_Integer; // 2
        String testString = "ignored";
        DerValue dv = new DerValue(invalidTag, testString);

        try {
            dv.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception, verify message contains indication of tag
            if (!e.getMessage().contains("not GeneralString")) {
                throw new AssertionError("IOException message unexpected: " + e.getMessage());
            }
        }
    }
}
