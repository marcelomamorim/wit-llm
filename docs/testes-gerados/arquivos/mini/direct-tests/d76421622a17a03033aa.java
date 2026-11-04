/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.current() method behavior
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import java.text.StringCharacterIterator;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testCurrentReturnsCodePointWhenBufferPosLessThanBufferLength();
        testCurrentReturnsDoneWhenBufferPosNotLessThanBufferLengthAndNextNormalizeFalse();
    }

    private static void testCurrentReturnsCodePointWhenBufferPosLessThanBufferLength() {
        // Create NormalizerBase with a string and mode, buffer will be initialized
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.Mode.NFC);

        // The bufferPos is initially 0, buffer length is 3, so current() should return codePointAt(0)
        int expected = nb.buffer.codePointAt(nb.bufferPos);
        int actual = nb.current();

        if (actual != expected) {
            throw new AssertionError("current() did not return expected code point when bufferPos < buffer.length(): expected " + expected + ", got " + actual);
        }
    }

    private static void testCurrentReturnsDoneWhenBufferPosNotLessThanBufferLengthAndNextNormalizeFalse() {
        // Create a NormalizerBase with empty string so buffer length is 0
        NormalizerBase nb = new NormalizerBase("", NormalizerBase.Mode.NFC);

        // Manually set bufferPos to 0 (equal to buffer length 0) to force else branch
        nb.bufferPos = 0;

        // Override nextNormalize() to return false by subclassing
        NormalizerBase nbOverride = new NormalizerBase("", NormalizerBase.Mode.NFC) {
            @Override
            protected boolean nextNormalize() {
                return false;
            }
        };
        nbOverride.bufferPos = 0;

        int result = nbOverride.current();
        if (result != NormalizerBase.DONE) {
            throw new AssertionError("current() did not return DONE when bufferPos >= buffer.length() and nextNormalize() returns false: got " + result);
        }
    }
}
