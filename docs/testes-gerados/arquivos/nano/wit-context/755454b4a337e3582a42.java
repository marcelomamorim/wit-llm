/* @test    @summary Test for getDisplayName method in JapaneseEra class    @run main JapaneseEraWitupTest */

import java.time.chrono.JapaneseEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayName_NullLocale();
        testGetDisplayName_NullStyle();
        testGetDisplayName_ValidInputs();
    }

    private static void testGetDisplayName_NullLocale() {
        try {
            JapaneseEra era = JapaneseEra.MEIJI;
            // Expect NullPointerException when locale is null
            era.getDisplayName(TextStyle.FULL, null);
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null locale: " + e);
        }
    }

    private static void testGetDisplayName_NullStyle() {
        try {
            JapaneseEra era = JapaneseEra.MEIJI;
            // Expect NullPointerException when style is null
            era.getDisplayName(null, Locale.US);
            System.out.println("FAIL: Expected NullPointerException for null style");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null style");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null style: " + e);
        }
    }

    private static void testGetDisplayName_ValidInputs() {
        try {
            JapaneseEra era = JapaneseEra.MEIJI;
            String nameFull = era.getDisplayName(TextStyle.FULL, Locale.US);
            String nameShort = era.getDisplayName(TextStyle.SHORT, Locale.US);
            String nameNarrow = era.getDisplayName(TextStyle.NARROW, Locale.US);
            if (nameFull != null && nameShort != null && nameNarrow != null) {
                System.out.println("PASS: Valid display names retrieved: " + nameFull + ", " + nameShort + ", " + nameNarrow);
            } else {
                System.out.println("FAIL: One of the display names is null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during valid inputs test: " + e);
        }
    }
}
