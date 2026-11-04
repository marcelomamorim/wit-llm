/* @test    @summary Test for DerValue.getUnalignedBitString() method to verify exception handling when tag is not a BIT STRING    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetUnalignedBitStringThrowsIOException();
    }

    private static void testGetUnalignedBitStringThrowsIOException() {
        byte[] dummyData = {0x30, 0x03, 0x02, 0x01, 0x05}; // Arbitrary ASN.1 sequence, not a BIT STRING
        DerValue dv = null;
        try {
            dv = new DerValue(dummyData);
        } catch (IOException e) {
            // Fail the test if constructor throws
            throw new RuntimeException("Failed to construct DerValue", e);
        }
        // Set the tag to a value different from tag_BitString (0x03)
        dv.tag = (byte) 0x02; // INTEGER tag
        try {
            dv.getUnalignedBitString();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("not a bit string")) {
                throw new AssertionError("IOException message mismatch: " + message);
            }
            // Test passes if IOException with expected message is thrown
        }
    }
}
