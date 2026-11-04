/* @test    @summary Test for MinguoEra.getDisplayName method handling null and null ERA cases    @run main MinguoEraWitupTest */

import static java.time.temporal.ChronoField.ERA;
import java.time.chrono.MinguoEra;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class MinguoEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithNullStyle();
        testGetDisplayNameWithNullEra();
    }

    private static void testGetDisplayNameWithNullStyle() {
        MinguoEra era = MinguoEra.of(1); // valid era
        try {
            String result = era.getDisplayName(null, Locale.US);
            System.out.println("Unexpected success: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null style");
        }
    }

    private static void testGetDisplayNameWithNullEra() {
        MinguoEra era = MinguoEra.of(1); // valid era
        try {
            String result = era.getDisplayName(TextStyle.FULL, null);
            System.out.println("Unexpected success: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null locale");
        }
    }
}
