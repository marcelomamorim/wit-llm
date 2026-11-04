/*
 * @test
 * @summary Test MathContext(String) constructor for valid and invalid inputs
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidFormat();
        testNegativePrecision();
    }

    private static void testValidString() {
        MathContext mc = new MathContext("precision=5 roundingMode=HALF_UP");
        if (mc.precision != 5) {
            throw new RuntimeException("Expected precision 5 but got " + mc.precision);
        }
        if (mc.roundingMode != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected roundingMode HALF_UP but got " + mc.roundingMode);
        }
    }

    private static void testNullString() {
        try {
            new MathContext((String) null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testInvalidFormat() {
        String[] invalids = {
            "prec=5 roundingMode=HALF_UP",
            "precision=5 round=HALF_UP",
            "precision=5roundingMode=HALF_UP",
            "precision=5 roundingMode=INVALID",
            "precision=5",
            "roundingMode=HALF_UP",
            "",
            "precision=5 roundingMode="
        };
        for (String s : invalids) {
            try {
                new MathContext(s);
                throw new RuntimeException("Expected IllegalArgumentException for input: '" + s + "'");
            } catch (IllegalArgumentException iae) {
                // expected
            }
        }
    }

    private static void testNegativePrecision() {
        try {
            new MathContext("precision=-1 roundingMode=HALF_UP");
            throw new RuntimeException("Expected IllegalArgumentException for negative precision");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
