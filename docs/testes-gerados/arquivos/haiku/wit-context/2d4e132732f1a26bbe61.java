/*
 * @test
 * @summary Witup-derived tests for MinguoEra.getDisplayName(TextStyle, Locale)
 * @run main MinguoEraWitupTest
 */

import java.time.chrono.MinguoEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class MinguoEraWitupTest {

    public static void main(String[] args) throws Exception {
        testNullStyleThrowsNPE();
        testNullLocaleThrowsNPE();
        testDisplayNamesAreNonEmptyAndDistinct();
        testDisplayNameIsDeterministic();
    }

    static void testNullStyleThrowsNPE() {
        try {
            MinguoEra.ROC.getDisplayName(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null style (ROC)");
        } catch (NullPointerException expected) {
        }
        try {
            MinguoEra.BEFORE_ROC.getDisplayName(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null style (BEFORE_ROC)");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullLocaleThrowsNPE() {
        try {
            MinguoEra.ROC.getDisplayName(TextStyle.FULL, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }
    }

    static void testDisplayNamesAreNonEmptyAndDistinct() {
        String roc = MinguoEra.ROC.getDisplayName(TextStyle.FULL, Locale.US);
        String before = MinguoEra.BEFORE_ROC.getDisplayName(TextStyle.FULL, Locale.US);
        if (roc == null || roc.isEmpty()) {
            throw new RuntimeException("ROC display name must not be null or empty");
        }
        if (before == null || before.isEmpty()) {
            throw new RuntimeException("BEFORE_ROC display name must not be null or empty");
        }
        if (roc.equals(before)) {
            throw new RuntimeException("ROC and BEFORE_ROC display names should differ: " + roc);
        }
    }

    static void testDisplayNameIsDeterministic() {
        String first = MinguoEra.of(1).getDisplayName(TextStyle.SHORT, Locale.US);
        String second = MinguoEra.of(1).getDisplayName(TextStyle.SHORT, Locale.US);
        if (first == null || !first.equals(second)) {
            throw new RuntimeException("Display name should be stable across calls: " + first + " vs " + second);
        }
    }
}
