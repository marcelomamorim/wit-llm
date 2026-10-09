/* @test    @summary Test for DerValue.getOctetString() method    @run main DerValueWitupTest_P5279cdccb57c */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_P5279cdccb57c {

    public static void main(String[] args) throws Exception {
        testGetOctetStringWithValidOctetString();
        testGetOctetStringWithInvalidTag();
        testGetOctetStringWithZeroLength();
        testGetOctetStringShortBuffer();
    }

    static void testGetOctetStringWithValidOctetString() throws Exception {
        byte[] data = {DerValue.tag_OctetString, 0x03, 0x01, 0x02, 0x03};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DerValue dv = new DerValue(bais);
        byte[] result = dv.getOctetString();
        if (result.length != 3 || result[0] != 0x01 || result[1] != 0x02 || result[2] != 0x03) {
            throw new AssertionError("Failed valid octet string test");
        }
    }

    static void testGetOctetStringWithInvalidTag() {
        byte[] data = {0x05, 0x00}; // NULL tag
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            DerValue dv = new DerValue(bais);
            dv.getOctetString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
        }
    }

    static void testGetOctetStringWithZeroLength() throws Exception {
        byte[] data = {DerValue.tag_OctetString, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DerValue dv = new DerValue(bais);
        byte[] result = dv.getOctetString();
        if (result.length != 0) {
            throw new AssertionError("Failed zero length octet string test");
        }
    }

    static void testGetOctetStringShortBuffer() {
        byte[] data = {DerValue.tag_OctetString, 0x02, 0x01}; // length 2 but only 1 byte available
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            DerValue dv = new DerValue(bais);
            dv.getOctetString();
            throw new AssertionError("Expected IOException for short buffer");
        } catch (IOException e) {
            // expected
        }
    }
}
