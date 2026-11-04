/*
 * @test
 * @summary Test sun.security.util.DerValue.getT61String() behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetT61StringThrowsOnWrongTag();
        testGetT61StringReturnsCorrectString();
    }

    private static void testGetT61StringThrowsOnWrongTag() {
        // Create DerValue with tag not equal to tag_T61String (20)
        // Use tag_Integer (2) to trigger IOException
        byte[] dummyData = {0x01};
        DerValue dv = new DerValue((byte)2, dummyData);
        try {
            dv.getT61String();
            throw new RuntimeException("Expected IOException not thrown for wrong tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not T61")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    private static void testGetT61StringReturnsCorrectString() throws Exception {
        // Create DerValue with tag_T61String (20) and data representing ISO-8859-1 string
        // The constructor DerValue(byte tag, byte[] data) sets tag and data
        byte tagT61 = 20; // tag_T61String
        byte[] isoBytes = {(byte)0xC7, (byte)0xE7, (byte)0xF1}; // Ççñ in ISO-8859-1
        DerValue dv = new DerValue(tagT61, isoBytes);
        String result = dv.getT61String();
        // The expected string is new String(isoBytes, "ISO-8859-1")
        String expected = new String(isoBytes, "ISO-8859-1");
        if (!result.equals(expected)) {
            throw new RuntimeException("getT61String returned unexpected string: " + result);
        }
    }
}
