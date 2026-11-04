/* @test    @summary Basic constructor test for NormalizerBase class    @run main NormalizerBaseWitupTest */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testConstructorWithStringAndMode();
        testConstructorWithStringAndModeAndOpt();
    }

    private static void testConstructorWithStringAndMode() {
        String sampleText = "Sample";
        Mode mode = Mode.NFC;
        try {
            NormalizerBase nb = new NormalizerBase(sampleText, mode);
            if (nb == null) {
                throw new AssertionError("NormalizerBase instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor with String and Mode threw an exception: " + e);
        }
    }

    private static void testConstructorWithStringAndModeAndOpt() {
        String sampleText = "Sample";
        Mode mode = Mode.NFD;
        int options = 0;
        try {
            NormalizerBase nb = new NormalizerBase(sampleText, mode, options);
            if (nb == null) {
                throw new AssertionError("NormalizerBase instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor with String, Mode, and options threw an exception: " + e);
        }
    }
}
