/* @test    @summary Test DerValue.getT61String() method for correct behavior and exception handling    @run main DerValueWitupTest_P39964a9070d1 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P39964a9070d1 {

    public static void main(String[] args) {
        testValidT61String();
        testInvalidTagThrows();
    }

    private static void testValidT61String() {
        try {
            byte[] t61Bytes = {0x14, 0x03, 0x41, 0x42, 0x43}; // Tag 0x14 (T61String), length 3, data "ABC"
            DerValue dv = new DerValue(t61Bytes);
            dv.tag = DerValue.tag_T61String; // Set tag explicitly to T61String
            String result = dv.getT61String();
            if (!"ABC".equals(result)) {
                throw new AssertionError("Expected 'ABC', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testInvalidTagThrows() {
        try {
            byte[] invalidBytes = {0x02, 0x01, 0x00}; // Tag 0x02 (INTEGER), length 1, data 0x00
            DerValue dv = new DerValue(invalidBytes);
            dv.tag = 0x05; // Set tag to NULL explicitly
            dv.getT61String();
            throw new AssertionError("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("DerValue.getT61String, not T61")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
