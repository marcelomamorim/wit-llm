/* @test    @summary Test for DerValue.getGeneralString() method.
 * @run main DerValueWitupTest_Pbcb83136a13d
 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pbcb83136a13d {

    public static void main(String[] args) {
        testGetGeneralStringWithValidTag();
        testGetGeneralStringWithInvalidTag();
    }

    private static void testGetGeneralStringWithValidTag() {
        try {
            byte tag = DerValue.tag_OctetString; // Using a valid tag for GeneralString
            byte[] dataBytes = "TestString".getBytes("ASCII");
            DerValue dv = new DerValue(tag, dataBytes);
            String result = dv.getGeneralString();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testGetGeneralStringWithInvalidTag() {
        try {
            byte invalidTag = (byte) (DerValue.tag_OctetString + 1); // Invalid tag for GeneralString
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue(invalidTag, dataBytes);
            dv.getGeneralString();
            throw new AssertionError("Expected IOException was not thrown for invalid tag");
        } catch (IOException e) {
            String message = e.getMessage();
            if (!message.contains("not GeneralString")) {
                throw new AssertionError("Unexpected IOException message: " + message);
            }
        }
    }
}
