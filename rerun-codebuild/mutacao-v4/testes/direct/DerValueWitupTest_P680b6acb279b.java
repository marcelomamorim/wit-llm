/* @test
   @summary Tests getIA5String() method of DerValue for correct ASCII string extraction and exception handling.
   @run main DerValueWitupTest_P680b6acb279b
 */
import java.io.*;
import sun.security.util.DerValue;
public class DerValueWitupTest_P680b6acb279b {
    public static void main(String[] args) throws Exception {
        testValidIA5String();
        testInvalidTagThrowsIOException();
    }
    private static void testValidIA5String() throws Exception {
        byte[] dataBytes = "TestString".getBytes("ASCII");
        DerValue dv = new DerValue(DerValue.tag_IA5String, dataBytes);
        String result = dv.getIA5String();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }
    private static void testInvalidTagThrowsIOException() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue((byte)0xFF, dataBytes); // invalid tag
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
        }
    }
}
