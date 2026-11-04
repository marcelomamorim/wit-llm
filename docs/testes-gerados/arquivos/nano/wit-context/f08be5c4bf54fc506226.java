/*
 * @test
 * @summary Test for LocalDate.parse(CharSequence)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testParseWithNullText();
        testParseWithNullFormatter();
    }

    public static void testParseWithNullText() {
        try {
            LocalDate.parse((CharSequence) null);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testParseWithNullFormatter() {
        CharSequence validText = "2023-03-15";
        try {
            // Since parse(text) calls parse(text, ISO_LOCAL_DATE), passing null as formatter is not directly possible here.
            // Instead, test the parse(CharSequence, DateTimeFormatter) with null formatter.
            LocalDate.parse(validText, null);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
