/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.current() method behavior
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import java.text.StringCharacterIterator;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        testCurrentReturnsCodePointWhenBufferPosLessThanLength();
        testCurrentReturnsDONEWhenBufferPosNotLessThanLengthAndNextNormalizeFalse();
    }

    private static void testCurrentReturnsCodePointWhenBufferPosLessThanLength() {
        // Create NormalizerBase with a string and Mode (use Mode.COMPOSE for example)
        NormalizerBase.Mode mode = NormalizerBase.Mode.COMPOSE;
        String testString = "abc";
        NormalizerBase nb = new NormalizerBase(testString, mode);

        // The buffer is initially empty, but nextNormalize() will fill it.
        // We call current() to get the code point at bufferPos.
        int cp = nb.current();

        // The returned code point should be the first code point of the normalized buffer.
        // Since input is "abc" and Mode.COMPOSE, expect 'a' code point (97)
        if (cp != 'a') {
            throw new AssertionError("Expected code point 'a' (97), got: " + cp);
        }
    }

    private static void testCurrentReturnsDONEWhenBufferPosNotLessThanLengthAndNextNormalizeFalse() {
        // Create a NormalizerBase with empty string so nextNormalize() returns false
        NormalizerBase.Mode mode = NormalizerBase.Mode.COMPOSE;
        String emptyString = "";
        NormalizerBase nb = new NormalizerBase(emptyString, mode);

        // current() should return DONE constant when bufferPos >= buffer.length() and nextNormalize() is false
        int result = nb.current();

        if (result != NormalizerBase.DONE) {
            throw new AssertionError("Expected DONE constant (" + NormalizerBase.DONE + ") but got: " + result);
        }
    }
}
