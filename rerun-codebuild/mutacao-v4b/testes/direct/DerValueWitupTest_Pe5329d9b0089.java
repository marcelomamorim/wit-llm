/* @test
   @summary Tests getGeneralString() method of DerValue for correct ASCII string extraction and exception handling.
   @run main DerValueWitupTest_Pe5329d9b0089
 */
import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pe5329d9b0089 {
    public static void main(String[] args) throws Exception {
        testValidGeneralString();
        testInvalidTagThrowsIOException();
    }

    private static void testValidGeneralString() throws Exception {
        byte[] dataBytes = "TestString".getBytes("ASCII");
        DerValue dv = new DerValue(DerValue.tag_GeneralString, dataBytes);
        String result = dv.getGeneralString();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            DerValue dv = new DerValue((byte)0x00, new byte[]{0x01, 0x02});
            dv.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
        }
    }
}
