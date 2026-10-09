/* @test
   @summary Tests getT61String method of DerValue for correct behavior and exception handling.
   @run main DerValueWitupTest_P4bcb1abad211
 */
import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P4bcb1abad211 {
    public static void main(String[] args) throws Exception {
        testValidT61String();
        testInvalidTagThrowsIOException();
    }

    private static void testValidT61String() throws Exception {
        byte[] dataBytes = { (byte)0xA0, (byte)0x81, (byte)0x80 };
        DerValue dv = new DerValue((byte)0x14, dataBytes); // 0x14 is T61String tag
        dv.tag = DerValue.tag_T61String;
        String result = dv.getT61String();
        if (!"ISO-8859-1".equals(getEncoding())) {
            throw new AssertionError("Encoding mismatch");
        }
        if (!"ISO-8859-1".equals(result)) {
            throw new AssertionError("Unexpected string result: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            DerValue dv = new DerValue();
            dv.tag = (byte)0xFF; // invalid tag
            dv.getT61String();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    private static String getEncoding() {
        return "ISO-8859-1";
    }
}
