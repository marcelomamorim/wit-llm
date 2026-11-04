/*
 * @test
 * @summary Verifies getUnalignedBitString rejects non-BIT STRING tags and decodes a valid BIT STRING
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testRejectsOctetStringTag();
        testRejectsIntegerTag();
        testRejectsTagFromConstructor();
        testDecodesValidBitString();
    }

    static void testRejectsOctetStringTag() throws Exception {
        DerValue v = new DerValue(new byte[] {0x04, 0x01, 0x05});
        expectIOException(v);
    }

    static void testRejectsIntegerTag() throws Exception {
        DerValue v = new DerValue(new byte[] {0x02, 0x01, 0x05});
        expectIOException(v);
    }

    static void testRejectsTagFromConstructor() throws Exception {
        DerValue v = new DerValue(DerValue.tag_Null, new byte[0]);
        expectIOException(v);
    }

    static void testDecodesValidBitString() throws Exception {
        DerValue v = new DerValue(new byte[] {0x03, 0x02, 0x04, (byte) 0xF0});
        BitArray bits = v.getUnalignedBitString();
        if (bits == null) {
            throw new RuntimeException("getUnalignedBitString returned null for a valid BIT STRING");
        }
        if (bits.length() != 4) {
            throw new RuntimeException("Expected 4 unaligned bits but got " + bits.length());
        }
    }

    static void expectIOException(DerValue v) {
        try {
            v.getUnalignedBitString();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-BIT STRING tag " + v.tag);
    }
}
