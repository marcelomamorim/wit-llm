/* @test    @summary Test setText method of NormalizerBase for CharacterIterator input    @run main NormalizerBaseWitupTest */

import java.text.CharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetTextWithValidCharacterIterator();
        testSetTextWithNullCharacterIterator();
    }

    private static void testSetTextWithValidCharacterIterator() {
        String sampleText = "Sample text";
        CharacterIterator iterator = new java.text.StringCharacterIterator(sampleText);
        NormalizerBase normalizer = new NormalizerBase();
        normalizer.setText(iterator);
        // No exception means success
        System.out.println("testSetTextWithValidCharacterIterator passed.");
    }

    private static void testSetTextWithNullCharacterIterator() {
        try {
            NormalizerBase normalizer = new NormalizerBase();
            normalizer.setText(null);
            // Expecting an IllegalStateException
            System.err.println("testSetTextWithNullCharacterIterator failed: no exception thrown.");
        } catch (IllegalStateException e) {
            System.out.println("testSetTextWithNullCharacterIterator passed.");
        } catch (Exception e) {
            System.err.println("testSetTextWithNullCharacterIterator failed: unexpected exception: " + e);
        }
    }
}
