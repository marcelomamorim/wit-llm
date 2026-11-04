/*
 * @test
 * @summary Test sun.security.util.DerValue.getGeneralString() method
 * @run main sun.security.util.DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetGeneralStringSuccess();
        testGetGeneralStringWrongTag();
    }

    private static void testGetGeneralStringSuccess() throws Exception {
        // tag_GeneralString is 27 (0x1B) as per DER universal tag for GeneralString
        final byte tag_GeneralString = 0x1B;
        String asciiStr = "HelloGeneralString";
        byte[] asciiBytes = asciiStr.getBytes("ASCII");

        DerValue dv = new DerValue(tag_GeneralString, asciiStr);
        String result = dv.getGeneralString();

        if (!asciiStr.equals(result)) {
            throw new RuntimeException("Expected '" + asciiStr + "' but got '" + result + "'");
        }
    }

    private static void testGetGeneralStringWrongTag() {
        // Use a tag different from tag_GeneralString (27)
        final byte tag_GeneralString = 0x1B;
        final byte wrongTag = 0x02; // INTEGER tag
        String asciiStr = "ShouldFail";

        DerValue dv = new DerValue(wrongTag, asciiStr);

        try {
            dv.getGeneralString();
            throw new RuntimeException("Expected IOException for wrong tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not GeneralString")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
