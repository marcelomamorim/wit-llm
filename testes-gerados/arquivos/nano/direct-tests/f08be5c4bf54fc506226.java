/*
 * @test
 * @summary Tests the parse method of LocalDate for various valid and invalid inputs.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testParseValidDate();
        testParseInvalidDate();
        testParseNullInput();
    }

    static void testParseValidDate() {
        String dateStr = "2023-03-15";
        LocalDate date = LocalDate.parse(dateStr);
        assert date.getYear() == 2023 : "Year should be 2023";
        assert date.getMonthValue() == 3 : "Month should be 3";
        assert date.getDayOfMonth() == 15 : "Day should be 15";
    }

    static void testParseInvalidDate() {
        String invalidDateStr = "2023-02-30";
        try {
            LocalDate.parse(invalidDateStr);
            assert false : "Parsing invalid date should throw DateTimeParseException";
        } catch (DateTimeParseException e) {
            // expected
        }
    }

    static void testParseNullInput() {
        try {
            LocalDate.parse((CharSequence) null);
            assert false : "Parsing null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
