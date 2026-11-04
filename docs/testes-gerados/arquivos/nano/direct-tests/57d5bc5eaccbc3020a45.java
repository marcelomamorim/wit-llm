/* @test    @summary Test DerValue.getBitString() method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidBitString();
        testInvalidTag();
    }

    static void testValidBitString() {
        try {
            byte[] bitStringData = {0x03, 0x02, 0x01, 0x00}; // Tag=0x03, Length=2, Content={0x01, 0x00}
            DerValue dv = new DerValue(bitStringData);
            byte[] result = dv.getBitString();
            if (result == null || result.length != 2 || result[0] != 0x01 || result[1] != 0x00) {
                throw new AssertionError("Unexpected bit string content: " + java.util.Arrays.toString(result));
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testInvalidTag() {
        try {
            byte[] invalidData = {0x02, 0x01, 0x00}; // Tag=0x02 (INTEGER), should throw
            DerValue dv = new DerValue(invalidData);
            dv.getBitString();
            throw new AssertionError("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not a bit string")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
