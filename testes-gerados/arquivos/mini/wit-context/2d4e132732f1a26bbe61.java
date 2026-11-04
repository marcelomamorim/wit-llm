/*
 * @test
 * @summary Test getDisplayName method of MinguoEra for normal and null style inputs
 * @run main MinguoEraWitupTest
 */

import java.time.chrono.MinguoEra;
import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoChronology;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.DateTimeException;

public class MinguoEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameNormal();
        testGetDisplayNameNullStyle();
    }

    private static void testGetDisplayNameNormal() {
        // Test with valid TextStyle and Locale
        MinguoEra eraROC = MinguoEra.ROC;
        String nameFull = eraROC.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameFull == null || nameFull.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for FULL style");
        }
        String nameShort = eraROC.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for SHORT style");
        }
        // Test the other era BEFORE_ROC
        MinguoEra eraBefore = MinguoEra.BEFORE_ROC;
        String beforeName = eraBefore.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (beforeName == null || beforeName.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for BEFORE_ROC era");
        }
    }

    private static void testGetDisplayNameNullStyle() {
        // According to source, passing null style causes NullPointerException
        MinguoEra era = MinguoEra.ROC;
        try {
            era.getDisplayName(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null TextStyle");
        } catch (NullPointerException expected) {
            // Expected exception
        }
        // Also test null locale is allowed (not throwing) or not
        try {
            String result = era.getDisplayName(TextStyle.FULL, null);
            if (result == null || result.isEmpty()) {
                throw new AssertionError("getDisplayName returned null or empty string for null Locale");
            }
        } catch (Exception ex) {
            throw new AssertionError("Unexpected exception for null Locale: " + ex);
        }
    }
}
