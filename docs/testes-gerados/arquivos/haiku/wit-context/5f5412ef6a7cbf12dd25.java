/*
 * @test
 * @summary Basic behavior of UCharacterIterator.getInstance(CharacterIterator)
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        CharacterIterator ci = new StringCharacterIterator("abc");
        UCharacterIterator it = UCharacterIterator.getInstance(ci);
        if (it == null) {
            throw new RuntimeException("getInstance returned null");
        }
        UCharacterIterator other = UCharacterIterator.getInstance(ci);
        if (it == other) {
            throw new RuntimeException("expected a new instance per call");
        }
        if (UCharacterIterator.DONE != -1) {
            throw new RuntimeException("DONE mismatch");
        }
    }
}
