/*
 * @test
 * @summary Tests for java.time.chrono.JapaneseDate.of method
 * @run main JapaneseDateWitupTest
 */

import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullEra();
        testInvalidDate();
    }

    static void testValidInputs() {
        try {
            JapaneseEra era = JapaneseEra.HEISEI;
            int yearOfEra = 30;
            int month = 5;
            int dayOfMonth = 15;
            JapaneseDate date = JapaneseDate.of(era, yearOfEra, month, dayOfMonth);
            if (date == null) {
                throw new AssertionError("Expected non-null date for valid inputs");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testNullEra() {
        try {
            JapaneseDate.of(null, 10, 1, 1);
            throw new AssertionError("Expected NullPointerException for null era");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException, got: " + e);
        }
    }

    static void testInvalidDate() {
        try {
            JapaneseEra era = JapaneseEra.HEISEI;
            int yearOfEra = 30;
            int invalidMonth = 13; // invalid month
            int dayOfMonth = 10;
            JapaneseDate.of(era, yearOfEra, invalidMonth, dayOfMonth);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (DateTimeException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected DateTimeException, got: " + e);
        }
        try {
            JapaneseEra era = JapaneseEra.HEISEI;
            int yearOfEra = 30;
            int month = 2;
            int invalidDay = 30; // invalid day for February
            JapaneseDate.of(era, yearOfEra, month, invalidDay);
            // Depending on validation, this may throw or not; assume it throws
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (DateTimeException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected DateTimeException, got: " + e);
        }
    }
}
