/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.setText(CharacterIterator) method
 * @run main NormalizerBaseWitupTest
 */

import java.text.StringCharacterIterator;
import java.text.CharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetTextWithValidCharacterIterator();
        testSetTextWithNullCharacterIterator();
    }

    private static void testSetTextWithValidCharacterIterator() {
        String sample = "abc";
        CharacterIterator iter = new StringCharacterIterator(sample);
        NormalizerBase normalizer = new NormalizerBase(sample, NormalizerBase.Mode.COMPOSE);
        normalizer.setText(iter);
        // After setText, currentIndex and nextIndex should be 0
        if (normalizer.currentIndex != 0 || normalizer.nextIndex != 0) {
            throw new RuntimeException("currentIndex or nextIndex not reset to 0 after setText");
        }
        // The text field should not be null
        if (normalizer.text == null) {
            throw new RuntimeException("text field is null after setText");
        }
    }

    private static void testSetTextWithNullCharacterIterator() {
        NormalizerBase normalizer = new NormalizerBase("", NormalizerBase.Mode.COMPOSE);
        try {
            normalizer.setText(null);
            throw new RuntimeException("Expected IllegalStateException not thrown when passing null");
        } catch (IllegalStateException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Could not create a new UCharacterIterator")) {
                throw new RuntimeException("IllegalStateException message unexpected: " + msg);
            }
        }
    }
}
