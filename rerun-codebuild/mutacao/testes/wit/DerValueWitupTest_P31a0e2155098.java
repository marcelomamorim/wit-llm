/* @test    @summary Test for DerValue.getGeneralString() method to verify exception handling and normal behavior    @run main DerValueWitupTest_P31a0e2155098 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P31a0e2155098 {

    public static void main(String[] args) throws Exception {
        testGetGeneralStringWithValidTag();
        testGetGeneralStringWithInvalidTag();
    }

    private static void testGetGeneralStringWithValidTag() throws Exception {
        byte[] dataBytes = {0x41, 0x42, 0x43}; // "ABC" in ASCII
        DerValue dv = new DerValue((byte)0x1A, dataBytes); // tag for GeneralString
        String result = dv.getGeneralString();
        if (!"ABC".equals(result)) {
            throw new AssertionError("Expected 'ABC', but got: " + result);
        }
    }

    private static void testGetGeneralStringWithInvalidTag() {
        byte[] dataBytes = {0x41, 0x42, 0x43}; // "ABC" in ASCII
        DerValue dv = new DerValue((byte)0x02, dataBytes); // tag not for GeneralString
        try {
            dv.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag, but no exception was thrown.");
        } catch (IOException e) {
            String expectedMessage = "DerValue.getGeneralString, not GeneralString " + (byte)0x02;
            if (!e.getMessage().contains(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
