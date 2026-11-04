/*
 * @test
 * @summary Test NormalizerBase constructor with String, Mode and int options
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        testConstructorBasic();
        testConstructorWithNullString();
        testConstructorWithDifferentModes();
        testConstructorWithOptions();
    }

    private static void testConstructorBasic() {
        NormalizerBase nb = new NormalizerBase("abc", Mode.NFC, 0);
        if (nb == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testConstructorWithNullString() {
        NormalizerBase nb = new NormalizerBase(null, Mode.NFD, 0);
        if (nb == null) {
            throw new RuntimeException("Constructor returned null with null string");
        }
    }

    private static void testConstructorWithDifferentModes() {
        NormalizerBase nbNFC = new NormalizerBase("test", Mode.NFC, 0);
        NormalizerBase nbNFD = new NormalizerBase("test", Mode.NFD, 0);
        NormalizerBase nbNFKC = new NormalizerBase("test", Mode.NFKC, 0);
        NormalizerBase nbNFKD = new NormalizerBase("test", Mode.NFKD, 0);
        if (nbNFC == null || nbNFD == null || nbNFKC == null || nbNFKD == null) {
            throw new RuntimeException("Constructor returned null for some Mode");
        }
    }

    private static void testConstructorWithOptions() {
        NormalizerBase nbOpt0 = new NormalizerBase("opt", Mode.NFC, 0);
        NormalizerBase nbOpt1 = new NormalizerBase("opt", Mode.NFC, 1);
        NormalizerBase nbOptNegative = new NormalizerBase("opt", Mode.NFC, -1);
        if (nbOpt0 == null || nbOpt1 == null || nbOptNegative == null) {
            throw new RuntimeException("Constructor returned null for some options");
        }
    }
}
