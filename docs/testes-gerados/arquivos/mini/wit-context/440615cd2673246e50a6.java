/*
 * @test
 * @summary Test getDisplayName method of ThaiBuddhistEra for valid and null arguments
 * @run main ThaiBuddhistEraWitupTest
 */

import java.time.chrono.ThaiBuddhistEra;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.DateTimeException;

public class ThaiBuddhistEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameValid();
        testGetDisplayNameNullStyle();
        testGetDisplayNameNullLocale();
    }

    private static void testGetDisplayNameValid() {
        // Test with valid TextStyle and Locale
        ThaiBuddhistEra eraBE = ThaiBuddhistEra.BE;
        String nameFull = eraBE.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameFull == null || nameFull.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for FULL style");
        }
        String nameShort = eraBE.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for SHORT style");
        }
    }

    private static void testGetDisplayNameNullStyle() {
        // According to source, passing null style should throw NullPointerException
        ThaiBuddhistEra eraBE = ThaiBuddhistEra.BE;
        try {
            eraBE.getDisplayName(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null style");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetDisplayNameNullLocale() {
        // Passing null locale is allowed and should not throw
        ThaiBuddhistEra eraBE = ThaiBuddhistEra.BE;
        String name = eraBE.getDisplayName(TextStyle.FULL, null);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for null locale");
        }
    }
}
