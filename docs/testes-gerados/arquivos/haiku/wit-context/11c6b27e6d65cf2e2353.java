/*
 * @test
 * @summary Witup-derived checks for NormalizerBase.setIndex on the current checkout
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.NFC);

        int r0 = nb.setIndex(0);
        if (r0 != 0) {
            throw new RuntimeException("setIndex(0) expected 0 but was " + r0);
        }

        int r1 = nb.setIndex(1);
        if (r1 != 1) {
            throw new RuntimeException("setIndex(1) expected 1 but was " + r1);
        }
        if (nb.current() != 'b') {
            throw new RuntimeException("current() after setIndex(1) expected 'b' but was " + nb.current());
        }

        int r2 = nb.setIndex(2);
        if (r2 != 2) {
            throw new RuntimeException("setIndex(2) expected 2 but was " + r2);
        }
        if (nb.current() != 'c') {
            throw new RuntimeException("current() after setIndex(2) expected 'c' but was " + nb.current());
        }

        expectIllegalArgument(nb, -1);
        expectIllegalArgument(nb, 4);
    }

    private static void expectIllegalArgument(NormalizerBase nb, int index) {
        try {
            nb.setIndex(index);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("setIndex(" + index + ") expected IllegalArgumentException");
    }
}
