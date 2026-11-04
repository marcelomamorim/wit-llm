/*
 * @test
 * @summary Witup-generated checks for HijrahEra.getDisplayName
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testNonNullStyleReturnsNonEmptyName();
        testNullStyleThrowsNullPointerException();
    }

    static void testNonNullStyleReturnsNonEmptyName() {
        HijrahEra era = HijrahEra.of(1);
        String name = era.getDisplayName(TextStyle.SHORT, Locale.US);
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty for non-null style");
        }
    }

    static void testNullStyleThrowsNullPointerException() {
        HijrahEra era = HijrahEra.of(1);
        boolean thrown = false;
        try {
            era.getDisplayName(null, Locale.US);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null TextStyle");
        }
    }
}
