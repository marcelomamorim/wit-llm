/*
 * @test
 * @summary Test java.time.Year.get(TemporalField) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testGetWithSupportedFields();
        testGetWithUnsupportedField();
    }

    private static void testGetWithSupportedFields() {
        Year y = Year.of(2020);

        // YEAR field should return the year value
        int yearValue = y.get(ChronoField.YEAR);
        if (yearValue != 2020) {
            throw new AssertionError("Expected 2020 for YEAR field but got " + yearValue);
        }

        // YEAR_OF_ERA field should return 2020 for AD era
        int yoeValue = y.get(ChronoField.YEAR_OF_ERA);
        if (yoeValue != 2020) {
            throw new AssertionError("Expected 2020 for YEAR_OF_ERA field but got " + yoeValue);
        }

        // ERA field should return 1 for AD
        int eraValue = y.get(ChronoField.ERA);
        if (eraValue != 1) {
            throw new AssertionError("Expected 1 for ERA field but got " + eraValue);
        }
    }

    private static void testGetWithUnsupportedField() {
        Year y = Year.of(2020);

        // Define a TemporalField that is unsupported by Year
        TemporalField unsupportedField = ChronoField.DAY_OF_MONTH;

        try {
            y.get(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported field");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }
}
