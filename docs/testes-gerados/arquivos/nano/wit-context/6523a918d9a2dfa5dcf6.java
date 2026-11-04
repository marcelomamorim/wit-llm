/* @test    @summary Test for OffsetTime.parse(CharSequence) method to verify behavior with null inputs.    @run main OffsetTimeWitupTest */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullText();
        testParseWithNullFormatter();
    }

    private static void testParseWithNullText() {
        try {
            OffsetTime.parse((CharSequence) null);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testParseWithNullFormatter() {
        CharSequence validText = "10:15:30+01:00";
        try {
            OffsetTime.parse(validText, (DateTimeFormatter) null);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
