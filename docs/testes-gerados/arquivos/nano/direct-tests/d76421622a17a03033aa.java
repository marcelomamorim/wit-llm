/* @test    @summary Test NormalizerBase.current() method for correct code point retrieval and boundary conditions    @run main NormalizerBaseWitupTest */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testCurrentWithBufferAndNext();
        testCurrentAtBufferEnd();
        testCurrentWithEmptyBuffer();
    }

    private static void testCurrentWithBufferAndNext() {
        // Setup a NormalizerBase with a buffer containing multiple code points
        String text = "abc";
        NormalizerBase nb = new NormalizerBase(text, NormalizerBase.Mode.NFC);
        // Advance buffer position to 1 (second code point)
        nb.bufferPos = 1;
        int codePoint = nb.current();
        int expectedCodePoint = text.codePointAt(1);
        if (codePoint != expectedCodePoint) {
            throw new AssertionError("Expected code point: " + expectedCodePoint + ", but got: " + codePoint);
        }
    }

    private static void testCurrentAtBufferEnd() {
        String text = "xyz";
        NormalizerBase nb = new NormalizerBase(text, NormalizerBase.Mode.NFC);
        // Set bufferPos to last position
        nb.bufferPos = text.length() - 1;
        int codePoint = nb.current();
        int expectedCodePoint = text.codePointAt(nb.bufferPos);
        if (codePoint != expectedCodePoint) {
            throw new AssertionError("Expected code point: " + expectedCodePoint + ", but got: " + codePoint);
        }
    }

    private static void testCurrentWithEmptyBuffer() {
        String text = "";
        NormalizerBase nb = new NormalizerBase(text, NormalizerBase.Mode.NFC);
        nb.bufferPos = 0;
        int codePoint = nb.current();
        if (codePoint != -1) { // DONE is typically -1
            throw new AssertionError("Expected DONE (-1), but got: " + codePoint);
        }
    }
}
