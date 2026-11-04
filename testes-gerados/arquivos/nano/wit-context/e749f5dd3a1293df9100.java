/*
 * @test
 * @summary Tests for java.time.temporal.TemporalAdjusters
 * @run main TemporalAdjustersWitupTest
 */

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testOfDateAdjuster_NullInput();
        testOfDateAdjuster_ValidInput();
    }

    private static void testOfDateAdjuster_NullInput() {
        try {
            TemporalAdjusters.ofDateAdjuster(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOfDateAdjuster_ValidInput() {
        UnaryOperator<LocalDate> adjuster = date -> date.plusDays(1);
        try {
            TemporalAdjuster ta = TemporalAdjusters.ofDateAdjuster(adjuster);
            LocalDate date = LocalDate.of(2023, 1, 1);
            LocalDate result = date.with(ta);
            if (!result.equals(LocalDate.of(2023, 1, 2))) {
                System.err.println("Failed: Unexpected result for valid adjuster");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid input: " + e);
        }
    }
}
