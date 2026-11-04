/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.setText(CharacterIterator) for null input and normal usage
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;
import java.text.StringCharacterIterator;
import java.text.CharacterIterator;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        testSetTextNull();
        testSetTextValid();
    }

    private static void testSetTextNull() {
        try {
            NormalizerBase nb = new NormalizerBase("", Mode.COMPOSE);
            nb.setText(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IllegalStateException e) {
            // Also acceptable if UCharacterIterator.getInstance returns null and triggers IllegalStateException
        }
    }

    private static void testSetTextValid() {
        NormalizerBase nb = new NormalizerBase("abc", Mode.COMPOSE);
        CharacterIterator ci = new StringCharacterIterator("test");
        nb.setText(ci);
        // No exception expected, state should be reset
        // We cannot access internal state, so just call setText again to check no exception
        nb.setText(new StringCharacterIterator(""));
    }
}
