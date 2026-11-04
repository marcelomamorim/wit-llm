/* @test    @summary Test setIndex method for NormalizerBase class    @run main NormalizerBaseWitupTest */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexNegative();
        testSetIndexOutOfBounds();
    }

    private static void testSetIndexValid() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int index = 0;
            int result = nb.setIndex(index);
            if (result != nb.current()) {
                throw new AssertionError("Expected current() to match setIndex result");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetIndexValid: " + e);
        }
    }

    private static void testSetIndexNegative() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            nb.setIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetIndexNegative: " + e);
        }
    }

    private static void testSetIndexOutOfBounds() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int outOfBoundsIndex = nb.current() + 1000;
            nb.setIndex(outOfBoundsIndex);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for out-of-bounds index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetIndexOutOfBounds: " + e);
        }
    }
}
