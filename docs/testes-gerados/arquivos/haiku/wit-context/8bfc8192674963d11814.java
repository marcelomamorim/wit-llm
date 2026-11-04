/*
 * @test
 * @summary Verify DecimalFormat.setRoundingMode rejects null without changing state and affects formatting
 * @run main DecimalFormatWitupTest
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalFormatWitupTest {

    public static void main(String[] args) {
        testNullRoundingModeThrowsNPE();
        testNullDoesNotChangeExistingRoundingMode();
        testRoundingModeIsStored();
        testRoundingModeAffectsFormatting();
    }

    static void testNullRoundingModeThrowsNPE() {
        DecimalFormat df = new DecimalFormat("0");
        boolean thrown = false;
        try {
            df.setRoundingMode(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null rounding mode");
        }
    }

    static void testNullDoesNotChangeExistingRoundingMode() {
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.UP);
        try {
            df.setRoundingMode(null);
        } catch (NullPointerException e) {
            // expected
        }
        if (df.getRoundingMode() != RoundingMode.UP) {
            throw new RuntimeException("Rounding mode changed after rejected null: "
                    + df.getRoundingMode());
        }
    }

    static void testRoundingModeIsStored() {
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.DOWN);
        if (df.getRoundingMode() != RoundingMode.DOWN) {
            throw new RuntimeException("Expected DOWN but was " + df.getRoundingMode());
        }
    }

    static void testRoundingModeAffectsFormatting() {
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String up = df.format(2.5);
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        String even = df.format(2.5);
        if (!"3".equals(up)) {
            throw new RuntimeException("HALF_UP expected \"3\" but was \"" + up + "\"");
        }
        if (!"2".equals(even)) {
            throw new RuntimeException("HALF_EVEN expected \"2\" but was \"" + even + "\"");
        }
    }
}
