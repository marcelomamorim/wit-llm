/*
 * @test
 * @summary Test sun.security.util.DerValue.getUTF8String() method
 * @run main DerValueWitupTest_Pc56caee89552
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_Pc56caee89552 {
    public static void main(String[] args) throws Exception {
        testGetUTF8StringWithValidTag();
        testGetUTF8StringWithInvalidTag();
    }

    private static void testGetUTF8StringWithValidTag() throws Exception {
        // tag_UTF8String is 0x0C according to DER universal tags
        final byte tag_UTF8String = 0x0C;
        String testString = "Hello UTF8";
        byte[] utf8Bytes = testString.getBytes("UTF8");

        // Construct DerValue with tag_UTF8String and utf8Bytes
        DerValue dv = new DerValue(tag_UTF8String, utf8Bytes);

        String result = dv.getUTF8String();
        if (!testString.equals(result)) {
            throw new RuntimeException("Expected '" + testString + "' but got '" + result + "'");
        }
    }

    private static void testGetUTF8StringWithInvalidTag() {
        // Use a tag different from tag_UTF8String (0x0C), e.g. tag_Integer (0x02)
        final byte tag_UTF8String = 0x0C;
        final byte tag_Integer = 0x02;
        byte[] dummyData = new byte[] {0x01, 0x02};

        DerValue dv = new DerValue(tag_Integer, dummyData);

        try {
            dv.getUTF8String();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not UTF-8") || !msg.contains(Byte.toString(tag_Integer))) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
