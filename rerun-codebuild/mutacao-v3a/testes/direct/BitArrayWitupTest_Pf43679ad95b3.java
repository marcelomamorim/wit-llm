/* @test    @summary Test for sun.security.util.BitArray.get method    @run main BitArrayWitupTest_Pf43679ad95b3 */

import sun.security.util.BitArray;

public class BitArrayWitupTest_Pf43679ad95b3 {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
        testGetWithIndexGreaterThanLength();
        testGetWithAllBitsSet();
        testGetWithAllBitsClear();
    }

    static void testGetWithValidIndex() {
        byte[] bytes = {(byte)0b00000001, (byte)0b00000010}; // bits: 00000001 00000010
        BitArray bitArray = new BitArray(16, bytes);
        boolean bit0 = bitArray.get(0); // should be false
        boolean bit1 = bitArray.get(1); // should be false
        boolean bit7 = bitArray.get(7); // should be true
        boolean bit8 = bitArray.get(8); // should be false
        boolean bit15 = bitArray.get(15); // should be false
        assert !bit0 : "Expected false for bit 0";
        assert !bit1 : "Expected false for bit 1";
        assert bit7 : "Expected true for bit 7";
        assert !bit8 : "Expected false for bit 8";
        assert !bit15 : "Expected false for bit 15";
    }

    static void testGetWithNegativeIndex() {
        byte[] bytes = {0};
        BitArray bitArray = new BitArray(8, bytes);
        try {
            bitArray.get(-1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative index";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithIndexEqualToLength() {
        byte[] bytes = {0};
        BitArray bitArray = new BitArray(8, bytes);
        try {
            bitArray.get(8);
            assert false : "Expected ArrayIndexOutOfBoundsException for index equal to length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithIndexGreaterThanLength() {
        byte[] bytes = {0};
        BitArray bitArray = new BitArray(8, bytes);
        try {
            bitArray.get(9);
            assert false : "Expected ArrayIndexOutOfBoundsException for index greater than length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithAllBitsSet() {
        byte[] bytes = {(byte)0xFF}; // all bits set
        BitArray bitArray = new BitArray(8, bytes);
        for (int i = 0; i < 8; i++) {
            assert bitArray.get(i) : "Expected true for bit " + i;
        }
    }

    static void testGetWithAllBitsClear() {
        byte[] bytes = {0}; // all bits clear
        BitArray bitArray = new BitArray(8, bytes);
        for (int i = 0; i < 8; i++) {
            assert !bitArray.get(i) : "Expected false for bit " + i;
        }
    }
}
