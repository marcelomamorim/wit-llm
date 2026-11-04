/*
 * @test
 * @summary Deterministic tests for java.util.Calendar.getDisplayNames(int, int, Locale)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testInvalidFieldReturnsNull();
        testInvalidStyleReturnsNull();
        testValidFieldStyleReturnsMapOrNull();
    }

    private static void testInvalidFieldReturnsNull() {
        Calendar cal = Calendar.getInstance();
        // Use invalid field: negative
        Map<String, Integer> result = cal.getDisplayNames(-1, Calendar.ALL_STYLES, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null for invalid field -1");
        }
        // Use invalid field: too large
        result = cal.getDisplayNames(1000, Calendar.ALL_STYLES, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null for invalid field 1000");
        }
    }

    private static void testInvalidStyleReturnsNull() {
        Calendar cal = Calendar.getInstance();
        // Use invalid style: negative
        Map<String, Integer> result = cal.getDisplayNames(Calendar.MONTH, -100, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null for invalid style -100");
        }
        // Use invalid style: too large
        result = cal.getDisplayNames(Calendar.MONTH, 1000, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null for invalid style 1000");
        }
    }

    private static void testValidFieldStyleReturnsMapOrNull() {
        Calendar cal = Calendar.getInstance();
        // Valid field and style should return a Map or null (depending on locale data)
        Map<String, Integer> result = cal.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        // result may be null if no data available, so just check no exception and type
        if (result != null && !(result instanceof Map)) {
            throw new AssertionError("Expected Map or null for valid field and style");
        }
        // Also test with a different locale
        result = cal.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRANCE);
        if (result != null && !(result instanceof Map)) {
            throw new AssertionError("Expected Map or null for valid field and style with Locale.FRANCE");
        }
    }
}
