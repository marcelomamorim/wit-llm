/* @test    @summary Test constructor of BitArray with valid and invalid lengths    @run main BitArrayWitupTest */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testValidLength();
        testNegativeLength();
    }

    static void testValidLength() {
        int length = 10;
        BitArray ba = new BitArray(length);
        assert ba != null : "BitArray instance should not be null";
        try {
            int getLength = getFieldLength(ba);
            assert getLength == length : "Expected length " + length + " but got " + getLength;
        } catch (Exception e) {
            throw new RuntimeException("Failed to access length field", e);
        }
    }

    static void testNegativeLength() {
        boolean exceptionThrown = false;
        try {
            new BitArray(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative length";
    }

    // Reflection helper to access private 'length' field
    static int getFieldLength(BitArray ba) throws Exception {
        java.lang.reflect.Field field = BitArray.class.getDeclaredField("length");
        field.setAccessible(true);
        return field.getInt(ba);
    }
}
