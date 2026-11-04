/* @test    @summary Test DerValue.getGeneralString() method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidGeneralString();
        testInvalidTagThrowsIOException();
    }

    static void testValidGeneralString() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            // tag for GeneralString is 0x1B (27)
            byte generalStringTag = 0x1B;
            DerValue dv = new DerValue(generalStringTag, dataBytes);
            String result = dv.getGeneralString();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidGeneralString", e);
        }
    }

    static void testInvalidTagThrowsIOException() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            // Use a tag different from tag_GeneralString (0x1B)
            byte invalidTag = 0x10;
            DerValue dv = new DerValue(invalidTag, dataBytes);
            dv.getGeneralString();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("DerValue.getGeneralString")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
