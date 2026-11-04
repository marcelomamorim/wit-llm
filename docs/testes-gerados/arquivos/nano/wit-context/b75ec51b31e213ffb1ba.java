/*
 * @test
 * @summary Testa o método setLanguageTag de Locale para comportamentos observáveis.
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.spi.LocaleNameProvider;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testSetLanguageTagWithValidInput();
        testSetLanguageTagWithMalformedInput();
    }

    private static void testSetLanguageTagWithValidInput() {
        Builder builder = new Builder();
        String validTag = "en-US";
        try {
            builder.setLanguageTag(validTag);
            System.out.println("PASS: setLanguageTag with valid input.");
        } catch (Exception e) {
            System.err.println("FAIL: setLanguageTag with valid input threw exception: " + e);
        }
    }

    private static void testSetLanguageTagWithMalformedInput() {
        Builder builder = new Builder();
        String malformedTag = "invalid-tag";
        try {
            builder.setLanguageTag(malformedTag);
            System.err.println("FAIL: setLanguageTag with malformed input did not throw exception.");
        } catch (IllformedLocaleException e) {
            System.out.println("PASS: setLanguageTag with malformed input threw IllformedLocaleException.");
        } catch (Exception e) {
            System.err.println("FAIL: setLanguageTag with malformed input threw unexpected exception: " + e);
        }
    }
}
