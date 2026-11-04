/*
 * @test
 * @summary Witup-style tests for MathContext(String) parsing and validation
 * @run main MathContextWitupTest
 */

public class MathContextWitupTest {

    public static void main(String[] args) {
        testValidStrings();
        testNullString();
        testInvalidFormats();
        testNegativePrecision();
        testInvalidRoundingMode();
        testZeroPrecision();
    }

    static void testValidStrings() {
        MathContext mc = new MathContext("precision=7 roundingMode=HALF_EVEN");
        check(mc.equals(MathContext.DECIMAL32), "precision=7 HALF_EVEN should equal DECIMAL32");
        check(mc.toString().equals("precision=7 roundingMode=HALF_EVEN"), "toString mismatch: " + mc);

        MathContext mc64 = new MathContext("precision=16 roundingMode=HALF_EVEN");
        check(mc64.equals(MathContext.DECIMAL64), "precision=16 HALF_EVEN should equal DECIMAL64");

        MathContext other = new MathContext("precision=7 roundingMode=HALF_UP");
        check(!other.equals(MathContext.DECIMAL32), "different rounding mode must not be equal");
    }

    static void testNullString() {
        try {
            new MathContext((String) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null string");
    }

    static void testInvalidFormats() {
        expectIAE("");
        expectIAE("roundingMode=HALF_UP");
        expectIAE("precision=7");
        expectIAE("precision=7 HALF_UP");
        expectIAE("precision=abc roundingMode=HALF_UP");
        expectIAE("precision=7 roundingMode=");
    }

    static void testNegativePrecision() {
        expectIAE("precision=-1 roundingMode=HALF_UP");
    }

    static void testInvalidRoundingMode() {
        expectIAE("precision=7 roundingMode=BOGUS");
    }

    static void testZeroPrecision() {
        MathContext mc = new MathContext("precision=0 roundingMode=UP");
        check(mc.equals(new MathContext(0, java.math.RoundingMode.UP)),
              "precision=0 UP should equal new MathContext(0, UP)");
    }

    static void expectIAE(String s) {
        try {
            new MathContext(s);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for: " + s);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }
}
