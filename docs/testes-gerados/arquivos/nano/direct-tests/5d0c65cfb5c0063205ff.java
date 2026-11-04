/*
 * @test
 * @summary Tests for LocalDate.until method to ensure correct period calculation.
 * @run main LocalDateWitupTest
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testUntilSameDate();
        testUntilNextDay();
        testUntilPreviousDay();
        testUntilDifferentMonths();
        testUntilDifferentYears();
    }

    static void testUntilSameDate() {
        LocalDate date1 = LocalDate.of(2023, 3, 15);
        ChronoLocalDate date2 = LocalDate.of(2023, 3, 15);
        Period p = date1.until(date2);
        assert p.getYears() == 0 : "Expected 0 years";
        assert p.getMonths() == 0 : "Expected 0 months";
        assert p.getDays() == 0 : "Expected 0 days";
    }

    static void testUntilNextDay() {
        LocalDate date1 = LocalDate.of(2023, 3, 15);
        ChronoLocalDate date2 = LocalDate.of(2023, 3, 16);
        Period p = date1.until(date2);
        assert p.getYears() == 0 : "Expected 0 years";
        assert p.getMonths() == 0 : "Expected 0 months";
        assert p.getDays() == 1 : "Expected 1 day";
    }

    static void testUntilPreviousDay() {
        LocalDate date1 = LocalDate.of(2023, 3, 16);
        ChronoLocalDate date2 = LocalDate.of(2023, 3, 15);
        Period p = date1.until(date2);
        assert p.getYears() == 0 : "Expected 0 years";
        assert p.getMonths() == 0 : "Expected 0 months";
        assert p.getDays() == -1 : "Expected -1 days";
    }

    static void testUntilDifferentMonths() {
        LocalDate date1 = LocalDate.of(2023, 1, 31);
        ChronoLocalDate date2 = LocalDate.of(2023, 3, 1);
        Period p = date1.until(date2);
        assert p.getYears() == 0 : "Expected 0 years";
        assert p.getMonths() == 1 : "Expected 1 month";
        assert p.getDays() == 1 : "Expected 1 day";
    }

    static void testUntilDifferentYears() {
        LocalDate date1 = LocalDate.of(2020, 2, 29);
        ChronoLocalDate date2 = LocalDate.of(2021, 2, 28);
        Period p = date1.until(date2);
        assert p.getYears() == 1 : "Expected 1 year";
        assert p.getMonths() == 0 : "Expected 0 months";
        assert p.getDays() == -1 : "Expected -1 days";
    }
}
