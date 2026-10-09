/* @test    @summary Test getT61String method for DerValue class    @run main DerValueWitupTest_P25bd22797b4a */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P25bd22797b4a {

    public static void main(String[] args) {
        testGetT61StringWithValidTag();
        testGetT61StringWithInvalidTag();
    }

    private static void testGetT61StringWithValidTag() {
        try {
            byte[] dataBytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // "Hello" in ISO-8859-1
            DerValue dv = new DerValue(DerValue.tag_OctetString, dataBytes);
            // For the purpose of this test, forcibly set tag to T61 (0x14)
            // Note: DerValue does not provide a public setter, so we simulate by reflection or assume tag is correct
            // Since reflection is discouraged, we simulate by creating a DerValue with correct tag
            // but as the constructor is not public, we assume the constructor is accessible for test
            // Alternatively, we can test with a DerValue constructed with correct tag and data
            DerValue dvT61 = new DerValue((byte)0x14, new String(dataBytes, "ISO-8859-1"));
            String result = dvT61.getT61String();
            if (!"Hello".equals(result)) {
                throw new AssertionError("Expected 'Hello', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testGetT61StringWithInvalidTag() {
        try {
            byte[] dataBytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // "Hello" in ISO-8859-1
            DerValue dv = new DerValue((byte)0x01, dataBytes); // tag != 20
            dv.getT61String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("DerValue.getT61String, not T61")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
