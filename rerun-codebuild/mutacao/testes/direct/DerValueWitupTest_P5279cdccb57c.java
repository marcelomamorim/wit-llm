/* @test    @summary Test DerValue.getOctetString() method for various DER-encoded octet strings    @run main DerValueWitupTest_P5279cdccb57c */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest_P5279cdccb57c {

    public static void main(String[] args) throws Exception {
        testEmptyOctetString();
        testSimpleOctetString();
        testConstructedOctetString();
        testInvalidTag();
    }

    static void testEmptyOctetString() throws IOException {
        byte[] encoded = new byte[] { (byte)0x04, (byte)0x00 };
        DerValue dv = new DerValue(encoded);
        byte[] result = dv.getOctetString();
        assert result.length == 0 : "Empty octet string should have length 0";
    }

    static void testSimpleOctetString() throws IOException {
        byte[] data = { 0x01, 0x02, 0x03 };
        byte[] encoded = new byte[] { (byte)0x04, (byte)data.length };
        byte[] fullEncoded = new byte[2 + data.length];
        fullEncoded[0] = (byte)0x04;
        fullEncoded[1] = (byte)data.length;
        System.arraycopy(data, 0, fullEncoded, 2, data.length);
        DerValue dv = new DerValue(fullEncoded);
        byte[] result = dv.getOctetString();
        assert java.util.Arrays.equals(result, data) : "Octet string content mismatch";
    }

    static void testConstructedOctetString() throws IOException {
        byte[] innerData = { 0x0A, 0x0B };
        byte[] innerEncoded = new byte[] { (byte)0x04, (byte)innerData.length };
        byte[] innerFull = new byte[2 + innerData.length];
        innerFull[0] = (byte)0x04;
        innerFull[1] = (byte)innerData.length;
        System.arraycopy(innerData, 0, innerFull, 2, innerData.length);
        byte[] outerData = innerFull;
        byte[] outerEncoded = new byte[2 + outerData.length];
        outerEncoded[0] = (byte)0x24; // constructed octet string tag
        outerEncoded[1] = (byte)outerData.length;
        System.arraycopy(outerData, 0, outerEncoded, 2, outerData.length);
        DerValue dv = new DerValue(outerEncoded);
        byte[] result = dv.getOctetString();
        assert java.util.Arrays.equals(result, innerData) : "Constructed octet string content mismatch";
    }

    static void testInvalidTag() {
        byte[] invalidEncoded = new byte[] { (byte)0x05, (byte)0x01, 0x00 };
        try {
            DerValue dv = new DerValue(invalidEncoded);
            dv.getOctetString();
            assert false : "Expected IOException for invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("not an Octet String") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
