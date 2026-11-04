/*
 * @test
 * @summary Verifies DecimalFormat.setRoundingMode observable behavior and null handling
 * @run main DecimalFormatWitupTest
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatWitupTest {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.US));

        df.setRoundingMode(RoundingMode.HALF_UP);
        check(df.getRoundingMode() == RoundingMode.HALF_UP, "getRoundingMode after HALF_UP");
        check("3".equals(df.format(2.5)), "HALF_UP 2.5 expected 3 but was " + df.format(2.5));

        df.setRoundingMode(RoundingMode.HALF_EVEN);
        check("2".equals(df.format(2.5)), "HALF_EVEN 2.5 expected 2 but was " + df.format(2.5));
        check("4".equals(df.format(3.5)), "HALF_EVEN 3.5 expected 4 but was " + df.format(3.5));

        df.setRoundingMode(RoundingMode.DOWN);
        check(df.getRoundingMode() == RoundingMode.DOWN, "getRoundingMode after DOWN");
        check("2".equals(df.format(2.9)), "DOWN 2.9 expected 2 but was " + df.format(2.9));

        boolean thrown = false;
        try {
            df.setRoundingMode(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setRoundingMode(null) must throw NullPointerException");
        check(df.getRoundingMode() == RoundingMode.DOWN,
              "rounding mode must be unchanged after null argument");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
