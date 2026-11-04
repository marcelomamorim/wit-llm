/*
 * @test
 * @summary Verifies that NormalizerBase.setText(CharacterIterator) replaces the iterated text and resets the iteration state
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import java.text.StringCharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetTextReplacesContent();
        testSetTextResetsIterationState();
        testSetTextWithEmptyIterator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetTextReplacesContent() {
        NormalizerBase n = new NormalizerBase("abc", NormalizerBase.NFC);
        n.setText(new StringCharacterIterator("xyz"));
        check(n.first() == 'x', "first() after setText should be 'x' but was " + n.first());
    }

    static void testSetTextResetsIterationState() {
        NormalizerBase n = new NormalizerBase("ab", NormalizerBase.NFC);
        n.next();
        n.setText(new StringCharacterIterator("xyz"));
        check(n.first() == 'x', "first() after setText should reset to 'x' but was " + n.first());
        check(n.next() == 'y', "next() after reset should be 'y'");
    }

    static void testSetTextWithEmptyIterator() {
        NormalizerBase n = new NormalizerBase("abc", NormalizerBase.NFC);
        n.setText(new StringCharacterIterator(""));
        n.setText(new StringCharacterIterator("q"));
        check(n.first() == 'q', "first() after second setText should be 'q' but was " + n.first());
    }
}
