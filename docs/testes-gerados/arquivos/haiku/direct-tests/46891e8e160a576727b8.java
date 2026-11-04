/*
 * @test
 * @summary Verifies Calendar.Builder.setWeekDefinition returns the same builder for valid week parameters and rejects out-of-range values with IllegalArgumentException
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r = b.setWeekDefinition(1, 1);
        if (r != b) {
            throw new RuntimeException("setWeekDefinition must return this builder");
        }

        // Boundary values of the valid range 1..7
        check(new Calendar.Builder(), 1, 7);
        check(new Calendar.Builder(), 7, 1);
        check(new Calendar.Builder(), 2, 4);

        // Values outside the valid range must be rejected
        expectIAE(0, 1);
        expectIAE(1, 0);
        expectIAE(8, 1);
        expectIAE(1, 8);
        expectIAE(-1, 1);
        expectIAE(1, -1);

        // Both parameters are validated, so one invalid argument is enough to fail
        expectIAE(0, 0);
        expectIAE(7, 8);
    }

    private static void check(Calendar.Builder builder, int firstDay, int minDays) {
        Calendar.Builder r = builder.setWeekDefinition(firstDay, minDays);
        if (r != builder) {
            throw new RuntimeException("setWeekDefinition must return this builder for " + firstDay + "," + minDays);
        }
    }

    private static void expectIAE(int firstDay, int minDays) {
        boolean thrown = false;
        try {
            new Calendar.Builder().setWeekDefinition(firstDay, minDays);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for " + firstDay + "," + minDays);
        }
    }
}
