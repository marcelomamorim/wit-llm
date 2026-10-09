/* @test    @summary Test DerValue.getIA5String() method for correct behavior and exception handling    @run main DerValueWitupTest_Paa56dd27b0bb */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Paa56dd27b0bb {

    public static void main(String[] args) {
        testValidIA5String();
        testInvalidTagThrowsIOException();
    }

    static void testValidIA5String() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            DerValue dv = new DerValue(DerValue.tag_IA5String, dataBytes);
            String result = dv.getIA5String();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testInvalidTagThrowsIOException() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue((byte)0x10, dataBytes); // invalid tag for IA5String
            dv.getIA5String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("DerValue.getIA5String, not IA5")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
