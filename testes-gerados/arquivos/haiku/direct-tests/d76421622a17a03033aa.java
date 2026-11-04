/*
 * @test
 * @summary Verify NormalizerBase.current() returns the code point at the current position without advancing, and DONE for empty input
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testEmptyStringReturnsDone();
        testFirstCodePointReturned();
        testCurrentIsIdempotent();
        testSurrogatePairReturnsFullCodePoint();
    }

    static void testEmptyStringReturnsDone() {
        NormalizerBase nb = new NormalizerBase("", NormalizerBase.NFC);
        check(nb.current() == NormalizerBase.DONE,
              "current() on empty string should return DONE, got " + nb.current());
    }

    static void testFirstCodePointReturned() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);
        check(nb.current() == 'a',
              "current() on \"abc\" should return 'a' (97), got " + nb.current());
    }

    static void testCurrentIsIdempotent() {
        NormalizerBase nb = new NormalizerBase("xy", NormalizerBase.NFC);
        int first = nb.current();
        int second = nb.current();
        check(first == second,
              "repeated current() calls should return the same value: " + first + " vs " + second);
        check(first == 'x',
              "current() on \"xy\" should return 'x', got " + first);
    }

    static void testSurrogatePairReturnsFullCodePoint() {
        String s = "\uD83D\uDE00";
        NormalizerBase nb = new NormalizerBase(s, NormalizerBase.NFC);
        check(nb.current() == 0x1F600,
              "current() should return U+1F600 for surrogate pair, got " + nb.current());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
