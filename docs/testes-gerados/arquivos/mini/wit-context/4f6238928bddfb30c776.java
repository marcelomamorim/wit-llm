/*
 * @test
 * @summary Test MathContext(String val) constructor for valid and invalid inputs
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testBadFormatString();
        testNegativePrecision();
    }

    private static void testValidString() {
        MathContext mc = new MathContext("precision=5 roundingMode=HALF_UP");
        if (mc.getPrecision() != 5) {
            throw new RuntimeException("Expected precision 5 but got " + mc.getPrecision());
        }
        if (mc.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected roundingMode HALF_UP but got " + mc.getRoundingMode());
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

    private static void testBadFormatString() {
        String[] badInputs = {
            "",
            "precision=5roundingMode=HALF_UP",
            "precision=5 roundingModeHALF_UP",
            "prec=5 roundingMode=HALF_UP",
            "precision=5 rounding=HALF_UP",
            "precision=abc roundingMode=HALF_UP",
            "precision=5 roundingMode=UNKNOWN"
        };
        for (String input : badInputs) {
            try {
                new MathContext(input);
                throw new RuntimeException("Expected IllegalArgumentException for input: " + input);
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
