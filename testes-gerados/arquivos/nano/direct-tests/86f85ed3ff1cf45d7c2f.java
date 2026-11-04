/* @test    @summary Test DerValue.getBoolean() method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetBooleanWithValidBooleanTrue();
        testGetBooleanWithValidBooleanFalse();
        testGetBooleanWithInvalidTag();
        testGetBooleanWithInvalidLength();
    }

    static void testGetBooleanWithValidBooleanTrue() {
        try {
            byte[] booleanTrueBytes = { DerValue.tag_Boolean, 0x01, 0xFF };
            DerValue dv = new DerValue(booleanTrueBytes);
            boolean result = dv.getBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testGetBooleanWithValidBooleanFalse() {
        try {
            byte[] booleanFalseBytes = { DerValue.tag_Boolean, 0x01, 0x00 };
            DerValue dv = new DerValue(booleanFalseBytes);
            boolean result = dv.getBoolean();
            if (result != false) {
                throw new AssertionError("Expected false, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testGetBooleanWithInvalidTag() {
        try {
            byte[] invalidTagBytes = { (byte)0xFF, 0x01, 0x00 };
            DerValue dv = new DerValue(invalidTagBytes);
            dv.getBoolean();
            throw new AssertionError("Expected IOException due to invalid tag, but none was thrown.");
        } catch (IOException e) {
            if (!e.getMessage().contains("not a BOOLEAN")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    static void testGetBooleanWithInvalidLength() {
        try {
            byte[] invalidLengthBytes = { DerValue.tag_Boolean, 0x02, 0x00, 0x00 };
            DerValue dv = new DerValue(invalidLengthBytes);
            dv.getBoolean();
            throw new AssertionError("Expected IOException due to invalid length, but none was thrown.");
        } catch (IOException e) {
            if (!e.getMessage().contains("invalid length")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
