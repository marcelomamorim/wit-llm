/*
 * @test
 * @summary Test for Period.addTo(Temporal) method, focusing on null input handling.
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testAddToWithNull();
    }

    public static void testAddToWithNull() {
        Period period = Period.of(1, 2, 3);
        try {
            period.addTo(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes.
            System.out.println("NullPointerException correctly thrown.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
