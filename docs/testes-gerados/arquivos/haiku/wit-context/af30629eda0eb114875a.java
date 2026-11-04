/*
 * @test
 * @summary Verify TemporalAdjusters.lastInMonth returns the last given weekday and rejects null
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        TemporalAdjuster monday = TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY);
        LocalDate feb = LocalDate.of(2024, 2, 10).with(monday);
        if (!feb.equals(LocalDate.of(2024, 2, 26))) {
            throw new RuntimeException("Expected 2024-02-26 but got " + feb);
        }

        TemporalAdjuster friday = TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY);
        LocalDate mar = LocalDate.of(2024, 3, 15).with(friday);
        if (!mar.equals(LocalDate.of(2024, 3, 29))) {
            throw new RuntimeException("Expected 2024-03-29 but got " + mar);
        }

        boolean thrown = false;
        try {
            TemporalAdjusters.lastInMonth(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null dayOfWeek");
        }
    }
}
