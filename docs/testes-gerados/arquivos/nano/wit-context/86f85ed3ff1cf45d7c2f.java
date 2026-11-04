/* @test    @summary Test getBoolean() method for DerValue class    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetBooleanWithTagNotBoolean();
        testGetBooleanWithInvalidLength();
        testGetBooleanWithValidBooleanTrue();
        testGetBooleanWithValidBooleanFalse();
    }

    private static void testGetBooleanWithTagNotBoolean() {
        try {
            byte[] buf = {0x05, 0x01, 0x00}; // NULL tag
            DerValue dv = new DerValue(buf);
            dv.getBoolean();
            System.err.println("Failed: Expected IOException for non-BOOLEAN tag");
        } catch (IOException e) {
            // Expected exception
            System.out.println("Passed: getBoolean with non-BOOLEAN tag throws IOException");
        }
    }

    private static void testGetBooleanWithInvalidLength() {
        try {
            byte[] buf = {0x01, 0x02, 0x00, 0x00}; // BOOLEAN tag with length 2
            DerValue dv = new DerValue(buf);
            dv.getBoolean();
            System.err.println("Failed: Expected IOException for invalid length");
        } catch (IOException e) {
            // Expected exception
            System.out.println("Passed: getBoolean with invalid length throws IOException");
        }
    }

    private static void testGetBooleanWithValidBooleanTrue() {
        try {
            byte[] buf = {0x01, 0x01, 0x01}; // BOOLEAN tag with length 1, value 0x01
            DerValue dv = new DerValue(buf);
            boolean result = dv.getBoolean();
            if (result) {
                System.out.println("Passed: getBoolean with value true");
            } else {
                System.err.println("Failed: getBoolean with value true returned false");
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for valid true boolean: " + e);
        }
    }

    private static void testGetBooleanWithValidBooleanFalse() {
        try {
            byte[] buf = {0x01, 0x01, 0x00}; // BOOLEAN tag with length 1, value 0x00
            DerValue dv = new DerValue(buf);
            boolean result = dv.getBoolean();
            if (!result) {
                System.out.println("Passed: getBoolean with value false");
            } else {
                System.err.println("Failed: getBoolean with value false returned true");
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for valid false boolean: " + e);
        }
    }
}
