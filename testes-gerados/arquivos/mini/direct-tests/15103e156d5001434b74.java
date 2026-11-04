/*
 * @test
 * @summary Test java.util.Calendar.getDisplayNames behavior for various styles and locales
 * @run main java.util.CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {
    public static void main(String[] args) throws Exception {
        testInvalidFieldReturnsNull();
        testAllStylesReturnsNonNullMap();
        testStandaloneStyleFallback();
        testNarrowFormatStyleFallback();
        testShortLongStyleDelegation();
    }

    private static void testInvalidFieldReturnsNull() {
        Calendar cal = Calendar.getInstance();
        // Use an invalid field (e.g., -1) to trigger checkDisplayNameParams failure
        Map<String, Integer> result = cal.getDisplayNames(-1, Calendar.ALL_STYLES, Locale.US);
        if (result != null) {
            throw new RuntimeException("Expected null for invalid field parameter");
        }
    }

    private static void testAllStylesReturnsNonNullMap() {
        Calendar cal = Calendar.getInstance();
        Map<String, Integer> map = cal.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        if (map == null || map.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty map for ALL_STYLES");
        }
    }

    private static void testStandaloneStyleFallback() {
        Calendar cal = Calendar.getInstance();
        // Use a standalone style (Calendar.STANDALONE) if defined, else use a style known to be standalone
        // JDK 11 defines Calendar.STANDALONE = 256
        final int STANDALONE_STYLE = 256;
        Map<String, Integer> map = cal.getDisplayNames(Calendar.MONTH, STANDALONE_STYLE, Locale.US);
        if (map == null || map.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty map for standalone style fallback");
        }
    }

    private static void testNarrowFormatStyleFallback() {
        Calendar cal = Calendar.getInstance();
        // Use narrow format style (Calendar.NARROW_FORMAT = 512)
        final int NARROW_FORMAT_STYLE = 512;
        Map<String, Integer> map = cal.getDisplayNames(Calendar.MONTH, NARROW_FORMAT_STYLE, Locale.US);
        if (map == null || map.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty map for narrow format style fallback");
        }
    }

    private static void testShortLongStyleDelegation() {
        Calendar cal = Calendar.getInstance();
        // SHORT = 1, LONG = 2
        Map<String, Integer> shortMap = cal.getDisplayNames(Calendar.MONTH, Calendar.SHORT, Locale.US);
        Map<String, Integer> longMap = cal.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        if (shortMap == null || shortMap.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty map for SHORT style");
        }
        if (longMap == null || longMap.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty map for LONG style");
        }
    }
}
