/*
 * @test
 * @summary Test sun.security.util.DerValue.getIA5String() method
 * @run main DerValueWitupTest_P65f1c49cec9c
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P65f1c49cec9c {
    public static void main(String[] args) throws Exception {
        testGetIA5StringValid();
        testGetIA5StringInvalidTag();
    }

    private static void testGetIA5StringValid() throws Exception {
        // tag_IA5String is 0x16 (decimal 22) according to DER universal tags
        final byte tag_IA5String = 0x16;
        byte[] asciiBytes = { 'H', 'e', 'l', 'l', 'o' };
        DerValue dv = new DerValue(tag_IA5String, new String(asciiBytes));
        String result = dv.getIA5String();
        if (!"Hello".equals(result)) {
            throw new RuntimeException("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testGetIA5StringInvalidTag() {
        // Use a tag different from tag_IA5String to trigger IOException
        final byte tag_IA5String = 0x16;
        final byte wrongTag = 0x02; // INTEGER tag
        try {
            DerValue dv = new DerValue(wrongTag, "Hello");
            dv.getIA5String();
            throw new RuntimeException("Expected IOException for wrong tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not IA5")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
