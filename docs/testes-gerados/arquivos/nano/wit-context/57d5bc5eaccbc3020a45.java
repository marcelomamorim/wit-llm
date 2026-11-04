/* @test    @summary Test for DerValue.getBitString() method to verify exception throwing on invalid tag    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetBitStringWithInvalidTag();
    }

    private static void testGetBitStringWithInvalidTag() {
        byte invalidTag = 0x03; // tag for BIT STRING, but we will set an invalid tag
        byte[] dummyBuffer = {0x00};
        DerValue dv = null;
        try {
            dv = new DerValue(invalidTag, dummyBuffer);
        } catch (IOException e) {
            // If constructor throws, test passes as exception is expected
            System.out.println("Constructor threw IOException: " + e.getMessage());
            return;
        }
        try {
            dv.getBitString();
            System.err.println("Expected IOException was not thrown.");
            System.exit(1);
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("not a bit string")) {
                System.out.println("Test passed: IOException thrown with expected message.");
            } else {
                System.err.println("Test failed: IOException thrown but message was unexpected: " + message);
                System.exit(1);
            }
        }
    }
}
