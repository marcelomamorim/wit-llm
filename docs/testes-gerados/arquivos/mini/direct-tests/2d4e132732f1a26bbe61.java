/*
 * @test
 * @summary Test getDisplayName method of MinguoEra
 * @run main MinguoEraWitupTest
 */

import java.time.chrono.MinguoEra;
import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoChronology;
import java.time.format.TextStyle;
import java.util.Locale;

public class MinguoEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameROC();
        testGetDisplayNameBEFORE_ROC();
    }

    private static void testGetDisplayNameROC() {
        MinguoEra era = MinguoEra.ROC;
        String nameShort = era.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String nameFull = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for ROC SHORT style");
        }
        if (nameFull == null || nameFull.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for ROC FULL style");
        }
        // The display name should correspond to the era of MinguoDate.of(1,1,1)
        String formatted = MinguoChronology.INSTANCE.date(1, 1, 1).format(
            new java.time.format.DateTimeFormatterBuilder().appendText(java.time.temporal.ChronoField.ERA, TextStyle.SHORT).toFormatter(Locale.ENGLISH));
        if (!nameShort.equals(formatted)) {
            throw new AssertionError("getDisplayName SHORT style does not match expected formatted era");
        }
    }

    private static void testGetDisplayNameBEFORE_ROC() {
        MinguoEra era = MinguoEra.BEFORE_ROC;
        String nameShort = era.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String nameFull = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for BEFORE_ROC SHORT style");
        }
        if (nameFull == null || nameFull.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for BEFORE_ROC FULL style");
        }
        // The display name should correspond to the era of MinguoDate.of(0,1,1)
        String formatted = MinguoChronology.INSTANCE.date(0, 1, 1).format(
            new java.time.format.DateTimeFormatterBuilder().appendText(java.time.temporal.ChronoField.ERA, TextStyle.SHORT).toFormatter(Locale.ENGLISH));
        if (!nameShort.equals(formatted)) {
            throw new AssertionError("getDisplayName SHORT style does not match expected formatted era");
        }
    }
}
