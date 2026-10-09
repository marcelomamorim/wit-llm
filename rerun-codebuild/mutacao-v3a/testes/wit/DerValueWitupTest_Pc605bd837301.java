/* @test    @summary Tests DerValue.getIA5String() method for normal and exceptional behavior    @run main DerValueWitupTest_Pc605bd837301 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pc605bd837301 {

    public static void main(String[] args) {
        testValidIA5String();
        testInvalidTagThrowsIOException();
    }

    private static void testValidIA5String() {
        try {
            byte[] dataBytes = "TestString".getBytes("ASCII");
            DerValue dv = new DerValue(DerValue.tag_OctetString, dataBytes);
            // forcibly set tag to IA5String (assuming tag_IA5String = 22)
            dv.tag = 22;
            String result = dv.getIA5String();
            if (!"TestString".equals(result)) {
                throw new AssertionError("Expected 'TestString', got '" + result + "'");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid IA5String test: " + e);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            byte[] dataBytes = "Invalid".getBytes("ASCII");
            DerValue dv = new DerValue(DerValue.tag_OctetString, dataBytes);
            // set tag to a value different from IA5String (assuming tag_IA5String = 22)
            dv.tag = 0x04; // OCTET STRING, but test as if not IA5
            dv.getIA5String();
            throw new AssertionError("Expected IOException was not thrown for invalid tag");
        } catch (IOException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
