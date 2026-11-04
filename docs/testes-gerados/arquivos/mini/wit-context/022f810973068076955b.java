/*
 * @test
 * @summary Test sun.security.util.DerValue.getUTF8String() behavior and exception
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUTF8StringWithValidUTF8StringTag();
        testGetUTF8StringWithInvalidTagThrowsIOException();
    }

    private static void testGetUTF8StringWithValidUTF8StringTag() throws Exception {
        // tag_UTF8String is 12 (0x0C) as per DER spec and source code
        byte utf8Tag = 12;
        String testString = "testUTF8";
        DerValue dv = new DerValue(utf8Tag, testString);

        String result = dv.getUTF8String();
        if (!testString.equals(result)) {
            throw new AssertionError("Expected '" + testString + "' but got '" + result + "'");
        }
    }

    private static void testGetUTF8StringWithInvalidTagThrowsIOException() {
        // Use a tag different from 12 to trigger IOException
        byte invalidTag = 10; // tag_Enumerated for example
        String testString = "ignored";
        DerValue dv = new DerValue(invalidTag, testString);

        try {
            dv.getUTF8String();
            throw new AssertionError("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not UTF-8")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
