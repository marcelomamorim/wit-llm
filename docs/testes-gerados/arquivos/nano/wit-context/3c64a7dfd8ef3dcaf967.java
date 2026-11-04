/* @test    @summary Test getIA5String method for DerValue class    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetIA5StringWithValidTag();
        testGetIA5StringWithInvalidTag();
    }

    private static void testGetIA5StringWithValidTag() {
        try {
            byte[] dataBytes = {0x01, 0x02}; // arbitrary data
            DerValue dv = new DerValue((byte) 0x16, dataBytes); // tag 0x16 (IA5String)
            String result = dv.getIA5String();
            if (result == null || result.isEmpty()) {
                throw new AssertionError("Expected non-empty string for valid IA5String tag");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid IA5String tag: " + e);
        }
    }

    private static void testGetIA5StringWithInvalidTag() {
        try {
            byte[] dataBytes = {0x01, 0x02};
            DerValue dv = new DerValue((byte) 0x02, dataBytes); // tag 0x02 (INTEGER)
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown");
        } catch (IOException e) {
            String expectedMessage = "DerValue.getIA5String, not IA5 " + (byte) 0x02;
            if (!e.getMessage().contains(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
