/*
 * @test
 * @summary Test UCharacterIterator.getInstance(StringBuffer) basic behavior
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        // Test with non-empty StringBuffer
        StringBuffer sb = new StringBuffer("abc");
        UCharacterIterator it = UCharacterIterator.getInstance(sb);
        if (it == null) {
            throw new RuntimeException("getInstance returned null for non-empty StringBuffer");
        }
        // The iterator should return characters in order
        int c1 = it.next();
        int c2 = it.next();
        int c3 = it.next();
        int c4 = it.next();
        if (c1 != 'a' || c2 != 'b' || c3 != 'c' || c4 != UCharacterIterator.DONE) {
            throw new RuntimeException("Iterator did not return expected characters");
        }

        // Test with empty StringBuffer
        StringBuffer empty = new StringBuffer();
        UCharacterIterator itEmpty = UCharacterIterator.getInstance(empty);
        if (itEmpty == null) {
            throw new RuntimeException("getInstance returned null for empty StringBuffer");
        }
        int cEmpty = itEmpty.next();
        if (cEmpty != UCharacterIterator.DONE) {
            throw new RuntimeException("Iterator on empty StringBuffer should return DONE immediately");
        }

        // Test with null argument - expected to throw NullPointerException or IllegalArgumentException
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
            throw new RuntimeException("Expected exception not thrown for null argument");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for null argument: " + e);
        }
    }
}
