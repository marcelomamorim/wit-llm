/* @test
   @summary Test getOctetString() method of DerValue for various scenarios.
   @run main DerValueWitupTest_Pd50634324b6e
 */
import java.io.*;
import sun.security.util.DerValue;
public class DerValueWitupTest_Pd50634324b6e {
    public static void main(String[] args) throws Exception {
        testEmptyOctetString();
        testSimpleOctetString();
        testConstructedOctetString();
    }

    private static void testEmptyOctetString() throws Exception {
        byte[] data = {0x04, 0x00}; // OCTET STRING with length 0
        DerValue dv = new DerValue(data);
        byte[] result = dv.getOctetString();
        if (result.length != 0) {
            throw new AssertionError("Expected empty byte array");
        }
    }

    private static void testSimpleOctetString() throws Exception {
        byte[] value = {0x01, 0x02, 0x03};
        byte[] data = new byte[2 + value.length];
        data[0] = 0x04; // OCTET STRING tag
        data[1] = (byte) value.length; // length
        System.arraycopy(value, 0, data, 2, value.length);
        DerValue dv = new DerValue(data);
        byte[] result = dv.getOctetString();
        if (result.length != value.length) {
            throw new AssertionError("Incorrect length of octet string");
        }
        for (int i = 0; i < value.length; i++) {
            if (result[i] != value[i]) {
                throw new AssertionError("Byte mismatch at index " + i);
            }
        }
    }

    private static void testConstructedOctetString() throws Exception {
        // Constructed octet string with two octet strings inside
        byte[] inner1 = {0x01, 0x02};
        byte[] inner2 = {0x03, 0x04};
        byte[] innerBytes = new byte[inner1.length + inner2.length];
        System.arraycopy(inner1, 0, innerBytes, 0, inner1.length);
        System.arraycopy(inner2, 0, innerBytes, inner1.length, inner2.length);
        byte[] data = new byte[2 + innerBytes.length];
        data[0] = 0x24; // constructed OCTET STRING tag (0x20 + 0x04)
        data[1] = (byte) innerBytes.length; // length
        System.arraycopy(innerBytes, 0, data, 2, innerBytes.length);
        DerValue dv = new DerValue(data);
        byte[] result = dv.getOctetString();
        if (result.length != innerBytes.length) {
            throw new AssertionError("Incorrect length for constructed octet string");
        }
        for (int i = 0; i < innerBytes.length; i++) {
            if (result[i] != innerBytes[i]) {
                throw new AssertionError("Byte mismatch at index " + i);
            }
        }
    }
}
