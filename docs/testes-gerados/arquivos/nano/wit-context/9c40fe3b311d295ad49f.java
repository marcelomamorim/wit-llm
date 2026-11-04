/*
 * @test
 * @summary Test for Calendar.setWeekDate method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetWeekDateThrowsException();
    }

    public static void testSetWeekDateThrowsException() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setWeekDate(2023, 10, 3);
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, teste passa
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
