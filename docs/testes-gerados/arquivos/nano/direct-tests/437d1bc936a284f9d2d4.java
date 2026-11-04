/* @test    @summary Test DerValue.getBitString method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidBitString();
        testInvalidTag();
    }

    static void testValidBitString() {
        try {
            byte[] data = {0x03, 0x02, 0x01, 0x00}; // Tag=0x03, Length=2, Content={0x01, 0x00}
            DerValue dv = new DerValue(data);
            byte[] result = dv.getBitString(true);
            if (result == null || result.length != 2 || result[0] != 0x01 || result[1] != 0x00) {
                throw new AssertionError("Unexpected result for valid bit string");
            }
        } catch (IOException e) {
            throw new AssertionError("Exception in testValidBitString: " + e);
        }
    }

    static void testInvalidTag() {
        try {
            byte[] data = {0x04, 0x01, 0x00}; // Tag=0x04 (OCTET STRING), Length=1, Content={0x00}
            DerValue dv = new DerValue(data);
            dv.getBitString(true);
            throw new AssertionError("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("not a bit string")) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }
}
