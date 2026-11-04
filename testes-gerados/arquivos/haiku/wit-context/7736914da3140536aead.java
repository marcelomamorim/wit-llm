/*
 * @test
 * @summary Checks UCharacterIterator.getInstance(String) for non-null sources and for the null-source IllegalArgumentException path
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        if (UCharacterIterator.DONE != -1) {
            throw new RuntimeException("DONE expected to be -1 but was " + UCharacterIterator.DONE);
        }

        UCharacterIterator it = UCharacterIterator.getInstance("abc");
        if (it == null) {
            throw new RuntimeException("getInstance(\"abc\") returned null");
        }

        UCharacterIterator empty = UCharacterIterator.getInstance("");
        if (empty == null) {
            throw new RuntimeException("getInstance(\"\") returned null");
        }

        try {
            UCharacterIterator.getInstance((String) null);
            throw new RuntimeException("Expected IllegalArgumentException for null source");
        } catch (IllegalArgumentException expected) {
            // expected path
        }
    }
}
