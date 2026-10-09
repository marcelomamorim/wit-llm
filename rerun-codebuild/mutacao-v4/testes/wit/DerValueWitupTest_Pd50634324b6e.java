/* @test
   @summary Testa o método getOctetString() da classe sun.security.util.DerValue.
   @run main DerValueWitupTest_Pd50634324b6e
 */
import java.io.*;
import sun.security.util.DerValue;
public class DerValueWitupTest_Pd50634324b6e {
    public static void main(String[] args) throws Exception {
        testGetOctetStringNormal();
        testGetOctetStringEmpty();
        testGetOctetStringShortBuffer();
        testGetOctetStringConstructed();
    }

    private static void testGetOctetStringNormal() throws Exception {
        byte[] data = {0x04, 0x03, 0x01, 0x02, 0x03}; // tag=0x04 (OCTET STRING), length=3, data=0x01 0x02 0x03
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DerValue dv = new DerValue(bais);
        byte[] result = dv.getOctetString();
        if (result.length != 3 || result[0] != 0x01 || result[1] != 0x02 || result[2] != 0x03) {
            throw new AssertionError("Failed normal getOctetString test");
        }
    }

    private static void testGetOctetStringEmpty() throws Exception {
        byte[] data = {0x04, 0x00}; // tag=0x04, length=0
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DerValue dv = new DerValue(bais);
        byte[] result = dv.getOctetString();
        if (result.length != 0) {
            throw new AssertionError("Failed empty getOctetString test");
        }
    }

    private static void testGetOctetStringShortBuffer() throws Exception {
        byte[] data = {0x04, 0x02, 0x01}; // length=2 but only 1 byte available
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DerValue dv = new DerValue(bais);
        try {
            dv.getOctetString();
            throw new AssertionError("Expected IOException for short buffer");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testGetOctetStringConstructed() throws Exception {
        byte[] nestedData = {0x04, 0x01, 0x05}; // nested octet string
        byte[] data = {0x24, (byte)nestedData.length}; // constructed octet string tag=0x24
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        baos.write(nestedData);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DerValue dv = new DerValue(bais);
        byte[] result = dv.getOctetString();
        if (result.length != 1 || result[0] != 0x05) {
            throw new AssertionError("Failed constructed getOctetString test");
        }
    }
}
