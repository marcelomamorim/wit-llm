/*
 * @test
 * @summary Tests DerValue.getUnalignedBitString for bit string and non-bit-string tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testBitStringUnusedBitsFour();
        testBitStringUnusedBitsZero();
        testNonBitStringTagThrowsIOException();
        testIntegerTagThrowsIOException();
        testRepeatedCallsAreConsistent();
    }

    static void testBitStringUnusedBitsFour() throws Exception {
        byte[] enc = {0x03, 0x02, 0x04, 0x30};
        DerValue v = new DerValue(enc);
        BitArray ba = v.getUnalignedBitString();
        if (ba == null) {
            throw new RuntimeException("null BitArray returned");
        }
        if (ba.length() != 4) {
            throw new RuntimeException("expected length 4 but was " + ba.length());
        }
    }

    static void testBitStringUnusedBitsZero() throws Exception {
        byte[] enc = {0x03, 0x02, 0x00, (byte) 0xA5};
        DerValue v = new DerValue(enc);
        BitArray ba = v.getUnalignedBitString();
        if (ba == null) {
            throw new RuntimeException("null BitArray returned");
        }
        if (ba.length() != 8) {
            throw new RuntimeException("expected length 8 but was " + ba.length());
        }
    }

    static void testNonBitStringTagThrowsIOException() throws Exception {
        byte[] enc = {0x04, 0x01, 0x00};
        DerValue v = new DerValue(enc);
        try {
            v.getUnalignedBitString();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("IOException expected for OCTET STRING tag");
    }

    static void testIntegerTagThrowsIOException() throws Exception {
        DerValue v = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        try {
            v.getUnalignedBitString();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("IOException expected for INTEGER tag");
    }

    static void testRepeatedCallsAreConsistent() throws Exception {
        byte[] enc = {0x03, 0x02, 0x04, 0x30};
        DerValue v = new DerValue(enc);
        int first = v.getUnalignedBitString().length();
        int second = v.getUnalignedBitString().length();
        if (first != second) {
            throw new RuntimeException("inconsistent lengths: " + first + " vs " + second);
        }
    }
}
