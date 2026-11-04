/*
 * @test
 * @summary Witup-generated observable behavior checks for NormalizerBase.current()
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        NormalizerBase empty = new NormalizerBase("", NormalizerBase.NONE);
        check(empty.current() == NormalizerBase.DONE, "current() on empty input must return DONE");

        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NONE);
        check(nb.current() == 'a', "current() must return first code point");
        check(nb.current() == 'a', "current() must be idempotent without next()");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
