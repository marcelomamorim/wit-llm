/*
 * @test
 * @summary Checks UCharacterIterator.getInstance(StringBuffer) for a valid buffer and for the null argument
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testValidStringBufferReturnsIterator();
        testDoneConstant();
        testNullStringBufferThrowsIllegalArgumentException();
    }

    private static void testValidStringBufferReturnsIterator() {
        UCharacterIterator it = UCharacterIterator.getInstance(new StringBuffer("abc"));
        if (it == null) {
            throw new RuntimeException("getInstance(StringBuffer) returned null for a valid buffer");
        }
    }

    private static void testDoneConstant() {
        if (UCharacterIterator.DONE != -1) {
            throw new RuntimeException("DONE expected to be -1 but was " + UCharacterIterator.DONE);
        }
    }

    private static void testNullStringBufferThrowsIllegalArgumentException() {
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null StringBuffer");
    }
}
