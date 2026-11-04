/* @test    @summary Test NormalizerBase.setIndex method for correct behavior and boundary conditions    @run main NormalizerBaseWitupTest */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetIndexWithinBounds();
        testSetIndexAtZero();
        testSetIndexAtMax();
        testSetIndexBelowZero();
        testSetIndexAboveMax();
    }

    private static void testSetIndexWithinBounds() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int index = 2;
            int result = nb.setIndex(index);
            if (result != nb.current()) {
                throw new AssertionError("setIndex within bounds failed: expected " + nb.current() + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testSetIndexWithinBounds: " + e);
        }
    }

    private static void testSetIndexAtZero() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int index = 0;
            int result = nb.setIndex(index);
            if (result != nb.current()) {
                throw new AssertionError("setIndex at zero failed: expected " + nb.current() + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testSetIndexAtZero: " + e);
        }
    }

    private static void testSetIndexAtMax() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int maxIndex = Integer.MAX_VALUE;
            int result = nb.setIndex(maxIndex);
            if (result != nb.current()) {
                throw new AssertionError("setIndex at max failed: expected " + nb.current() + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testSetIndexAtMax: " + e);
        }
    }

    private static void testSetIndexBelowZero() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int index = -1;
            int result = nb.setIndex(index);
            if (result != nb.current()) {
                throw new AssertionError("setIndex below zero failed: expected " + nb.current() + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testSetIndexBelowZero: " + e);
        }
    }

    private static void testSetIndexAboveMax() {
        try {
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            int index = Integer.MAX_VALUE + 1L > Integer.MAX_VALUE ? Integer.MAX_VALUE : Integer.MAX_VALUE + 1;
            int result = nb.setIndex(index);
            if (result != nb.current()) {
                throw new AssertionError("setIndex above max failed: expected " + nb.current() + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testSetIndexAboveMax: " + e);
        }
    }
}
