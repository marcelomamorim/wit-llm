/* @test    @summary Test for DerValue.getGeneralString() method    @run main DerValueWitupTest_Pbcb83136a13d */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_Pbcb83136a13d {

    public static void main(String[] args) {
        testGetGeneralStringWithValidData();
        testGetGeneralStringWithInvalidTag();
    }

    private static void testGetGeneralStringWithValidData() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            DerValue dv = new DerValue(DerValue.tag_GeneralString, dataBytes);
            String result = dv.getGeneralString();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid data test", e);
        }
    }

    private static void testGetGeneralStringWithInvalidTag() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue((byte)0x00, dataBytes); // invalid tag
            dv.getGeneralString();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("not GeneralString")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Expected exception, test passes
        }
    }
}
