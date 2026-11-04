/*
 * @test
 * @summary Verifies that NormalizerBase.setIndex positions the iterator and returns the current character
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import java.text.CharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSetIndexReturnsCharAtIndex() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);
        check(nb.setIndex(1) == 'b', "setIndex(1) should return 'b'");
    }

    static void testSetIndexZero() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);
        check(nb.setIndex(0) == 'a', "setIndex(0) should return 'a'");
    }

    static void testSetIndexAtEndReturnsDone() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);
        check(nb.setIndex(3) == CharacterIterator.DONE, "setIndex(length) should return DONE");
    }

    static void testSetIndexRepositionsIterator() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);
        nb.setIndex(2);
        check(nb.current() == 'c', "current() after setIndex(2) should be 'c'");
        check(nb.setIndex(0) == 'a', "setIndex(0) after moving should return 'a'");
        check(nb.current() == 'a', "current() after setIndex(0) should be 'a'");
    }

    public static void main(String[] args) {
        testSetIndexReturnsCharAtIndex();
        testSetIndexZero();
        testSetIndexAtEndReturnsDone();
        testSetIndexRepositionsIterator();
    }
}
