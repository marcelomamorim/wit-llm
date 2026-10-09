/* @test
   @summary Tests DerValue.getUTF8String() method for correct behavior.
   @run main DerValueWitupTest_P7874ea918173
 */
import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P7874ea918173 {
    public static void main(String[] args) throws Exception {
        testValidUTF8String();
        testInvalidTagThrows();
    }

    private static void testValidUTF8String() throws Exception {
        String expected = "Hello, UTF8!";
        byte[] utf8Bytes = expected.getBytes("UTF8");
        DerValue dv = new DerValue((byte)0x0C, utf8Bytes); // 0x0C is tag_UTF8String
        String result = dv.getUTF8String();
        if (!expected.equals(result)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testInvalidTagThrows() throws Exception {
        byte[] data = {0x01, 0x01, 0x00}; // tag for BOOLEAN
        DerValue dv = new DerValue(data);
        try {
            dv.getUTF8String();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
            if (!e.getMessage().contains("not UTF-8")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
