/*
 * @test
 * @summary Checks MinguoEra.getDisplayName(TextStyle, Locale) for ROC and BEFORE_ROC eras
 * @run main MinguoEraWitupTest
 */

import java.time.chrono.MinguoEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class MinguoEraWitupTest {

    public static void main(String[] args) {
        testRocDisplayNameNonEmpty();
        testBeforeRocDisplayNameNonEmpty();
        testDisplayNameDeterministic();
        testEraDisplayNamesDiffer();
        testOfReturnsSameConstants();
    }

    static void testRocDisplayNameNonEmpty() {
        for (TextStyle style : TextStyle.values()) {
            String name = MinguoEra.ROC.getDisplayName(style, Locale.US);
            if (name == null || name.isEmpty()) {
                throw new AssertionError("ROC display name empty for style " + style);
            }
        }
    }

    static void testBeforeRocDisplayNameNonEmpty() {
        MinguoEra before = MinguoEra.of(0);
        for (TextStyle style : TextStyle.values()) {
            String name = before.getDisplayName(style, Locale.US);
            if (name == null || name.isEmpty()) {
                throw new AssertionError("BEFORE_ROC display name empty for style " + style);
            }
        }
    }

    static void testDisplayNameDeterministic() {
        String first = MinguoEra.ROC.getDisplayName(TextStyle.FULL, Locale.US);
        String second = MinguoEra.ROC.getDisplayName(TextStyle.FULL, Locale.US);
        if (!first.equals(second)) {
            throw new AssertionError("Non-deterministic display name: " + first + " vs " + second);
        }
    }

    static void testEraDisplayNamesDiffer() {
        String roc = MinguoEra.ROC.getDisplayName(TextStyle.FULL, Locale.US);
        String before = MinguoEra.of(0).getDisplayName(TextStyle.FULL, Locale.US);
        if (roc.equals(before)) {
            throw new AssertionError("ROC and BEFORE_ROC display names should differ: " + roc);
        }
    }

    static void testOfReturnsSameConstants() {
        if (MinguoEra.of(1) != MinguoEra.ROC) {
            throw new AssertionError("of(1) should return ROC");
        }
        if (MinguoEra.of(0) == MinguoEra.ROC) {
            throw new AssertionError("of(0) should not return ROC");
        }
    }
}
