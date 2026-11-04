/*
 * @test
 * @summary Tests the public MathContext(String) constructor: valid parsing, NPE for null, and IAE for malformed or invalid input
 * @run main MathContextWitupTest
 */

public class MathContextWitupTest {

    public static void main(String[] args) {
        MathContext mc = new MathContext("precision=5 roundingMode=HALF_UP");
        check(mc.getPrecision() == 5, "precision should be 5");
        check(mc.getRoundingMode() == RoundingMode.HALF_UP, "roundingMode should be HALF_UP");

        mc = new MathContext("precision=0 roundingMode=CEILING");
        check(mc.getPrecision() == 0, "precision 0 should be accepted");
        check(mc.getRoundingMode() == RoundingMode.CEILING, "roundingMode should be CEILING");

        mc = new MathContext("precision=34 roundingMode=HALF_EVEN");
        check(mc.equals(MathContext.DECIMAL128), "string form should equal DECIMAL128");

        expectNPE(null);
        expectIAE("");
        expectIAE("precision=5");
        expectIAE("prec=5 roundingMode=HALF_UP");
        expectIAE("precision=-1 roundingMode=HALF_UP");
        expectIAE("precision=x roundingMode=HALF_UP");
        expectIAE("precision=99999999999 roundingMode=HALF_UP");
        expectIAE("precision=5 mode=HALF_UP");
        expectIAE("precision=5 roundingMode=");
        expectIAE("precision=5 roundingMode=BOGUS");
        expectIAE("precision=5 roundingMode=HALF_UP extra");
    }

    static void expectNPE(String s) {
        boolean thrown = false;
        try {
            new MathContext(s);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NullPointerException expected for null input");
    }

    static void expectIAE(String s) {
        boolean thrown = false;
        try {
            new MathContext(s);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected for input: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
