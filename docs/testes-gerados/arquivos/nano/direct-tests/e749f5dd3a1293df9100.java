/*
 * @test
 * @summary Test of TemporalAdjusters.ofDateAdjuster method
 * @run main TemporalAdjustersWitupTest
 */

import static java.time.temporal.TemporalAdjusters.ofDateAdjuster;
import static java.time.LocalDate.of;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testOfDateAdjusterWithNextDay();
        testOfDateAdjusterWithSpecificDay();
    }

    static void testOfDateAdjusterWithNextDay() {
        TemporalAdjuster adjuster = ofDateAdjuster(date -> date.plusDays(1));
        LocalDate date = of(2023, 3, 15);
        Temporal result = adjuster.adjustInto(date);
        LocalDate expected = date.plusDays(1);
        assert result.equals(expected) : "Expected " + expected + " but got " + result;
    }

    static void testOfDateAdjusterWithSpecificDay() {
        TemporalAdjuster adjuster = ofDateAdjuster(date -> date.with(DAY_OF_MONTH, 1));
        LocalDate date = of(2023, 3, 15);
        Temporal result = adjuster.adjustInto(date);
        LocalDate expected = date.with(DAY_OF_MONTH, 1);
        assert result.equals(expected) : "Expected " + expected + " but got " + result;
    }
}
