/* @test    @summary Test DerValue.getOctetString() method for default behavior and exception handling    @run main DerValueWitupTest_P5b7d18c28e39 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P5b7d18c28e39 {

    public static void main(String[] args) {
        testGetOctetStringNormal();
        testGetOctetStringNotOctetString();
    }

    private static void testGetOctetStringNormal() {
        try {
            byte[] octets = {0x01, 0x02, 0x03};
            // Create DerValue with tag OctetString and data as octets
            DerValue dv = new DerValue(DerValue.tag_OctetString, octets);
            byte[] result = dv.getOctetString();
            assert result.length == octets.length : "Length mismatch";
            for (int i = 0; i < octets.length; i++) {
                assert result[i] == octets[i] : "Content mismatch at index " + i;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal case: " + e);
        }
    }

    private static void testGetOctetStringNotOctetString() {
        try {
            byte invalidTag = 0x05; // NULL tag
            DerValue dv = new DerValue(invalidTag, new byte[]{0x00});
            dv.getOctetString();
            throw new AssertionError("Expected IOException for invalid tag, but none thrown.");
        } catch (IOException e) {
            // Expected exception
            assert e.getMessage().contains("not an Octet String") : "Unexpected exception message: " + e.getMessage();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
