/* @test    @summary Test getT61String method for DerValue class    @run main DerValueWitupTest_P39964a9070d1 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P39964a9070d1 {

    public static void main(String[] args) throws Exception {
        testGetT61StringWithValidTag();
        testGetT61StringWithInvalidTag();
    }

    private static void testGetT61StringWithValidTag() throws Exception {
        byte[] dataBytes = {0x41, 0x42, 0x43}; // "ABC" in ISO-8859-1
        // tag_T61String is 20 (0x14)
        byte tagT61String = 0x14;
        DerValue dv = new DerValue(tagT61String, dataBytes);
        String result = dv.getT61String();
        if (!"ABC".equals(result)) {
            throw new AssertionError("Expected 'ABC', got: " + result);
        }
    }

    private static void testGetT61StringWithInvalidTag() {
        byte[] dataBytes = {0x41, 0x42, 0x43};
        byte invalidTag = 0x01; // BOOLEAN tag
        DerValue dv = new DerValue(invalidTag, dataBytes);
        try {
            dv.getT61String();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            String expectedMessage = "DerValue.getT61String, not T61 " + invalidTag;
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
