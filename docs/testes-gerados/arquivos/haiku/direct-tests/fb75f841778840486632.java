/*
 * @test
 * @summary Tests Year.get(TemporalField) for supported fields, ERA/YEAR_OF_ERA mapping and unsupported fields
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearWitupTest {

    public static void main(String[] args) {
        checkYearField();
        checkEraAndYearOfEra();
        checkConsistencyWithGetLong();
        checkUnsupportedFields();
    }

    static void checkYearField() {
        check(Year.of(2000).get(ChronoField.YEAR) == 2000, "YEAR of 2000");
        check(Year.of(0).get(ChronoField.YEAR) == 0, "YEAR of 0");
        check(Year.of(-5).get(ChronoField.YEAR) == -5, "YEAR of -5");
        check(Year.of(Year.MAX_VALUE).get(ChronoField.YEAR) == Year.MAX_VALUE, "YEAR of MAX_VALUE");
        check(Year.of(Year.MIN_VALUE).get(ChronoField.YEAR) == Year.MIN_VALUE, "YEAR of MIN_VALUE");
    }

    static void checkEraAndYearOfEra() {
        check(Year.of(2000).get(ChronoField.ERA) == 1, "ERA of 2000 should be CE (1)");
        check(Year.of(2000).get(ChronoField.YEAR_OF_ERA) == 2000, "YEAR_OF_ERA of 2000");
        check(Year.of(1).get(ChronoField.ERA) == 1, "ERA of 1");
        check(Year.of(1).get(ChronoField.YEAR_OF_ERA) == 1, "YEAR_OF_ERA of 1");
        check(Year.of(0).get(ChronoField.ERA) == 0, "ERA of 0 should be BCE (0)");
        check(Year.of(0).get(ChronoField.YEAR_OF_ERA) == 1, "YEAR_OF_ERA of 0 should be 1");
        check(Year.of(-1).get(ChronoField.ERA) == 0, "ERA of -1 should be BCE (0)");
        check(Year.of(-1).get(ChronoField.YEAR_OF_ERA) == 2, "YEAR_OF_ERA of -1 should be 2");
        check(Year.of(-5).get(ChronoField.YEAR_OF_ERA) == 6, "YEAR_OF_ERA of -5 should be 6");
    }

    static void checkConsistencyWithGetLong() {
        int[] years = {-1000, -1, 0, 1, 1999, 2000, 2018};
        ChronoField[] fields = {ChronoField.YEAR, ChronoField.ERA, ChronoField.YEAR_OF_ERA};
        for (int y : years) {
            Year year = Year.of(y);
            for (ChronoField f : fields) {
                check(year.get(f) == (int) year.getLong(f),
                      "get must equal getLong for " + y + " " + f);
            }
        }
    }

    static void checkUnsupportedFields() {
        expectUnsupported(Year.of(2000), ChronoField.MONTH_OF_YEAR);
        expectUnsupported(Year.of(2000), ChronoField.DAY_OF_MONTH);
        expectUnsupported(Year.of(2000), ChronoField.HOUR_OF_DAY);
    }

    static void expectUnsupported(Year year, TemporalField field) {
        boolean thrown = false;
        try {
            year.get(field);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "expected UnsupportedTemporalTypeException for " + field);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
