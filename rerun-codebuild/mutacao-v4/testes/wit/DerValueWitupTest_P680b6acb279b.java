/* @test
   @summary Tests getIA5String method for DerValue class.
   @run main DerValueWitupTest_P680b6acb279b
 */
import java.io.*;
import sun.security.util.DerValue;
public class DerValueWitupTest_P680b6acb279b {
    public static void main(String[] args) throws Exception {
        testValidTag();
        testInvalidTag();
    }
    private static void testValidTag() throws Exception {
        byte[] dataBytes = "TestString".getBytes("ASCII");
        DerValue dv = new DerValue(DerValue.tag_OctetString, dataBytes);
        dv.tag = DerValue.tag_OctetString; // Simulate correct tag
        String result = dv.getIA5String();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }
    private static void testInvalidTag() {
        try {
            DerValue dv = new DerValue(DerValue.tag_Integer, new byte[]{0x01});
            dv.tag = 0x15; // Invalid tag for IA5
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
        }
    }
}
