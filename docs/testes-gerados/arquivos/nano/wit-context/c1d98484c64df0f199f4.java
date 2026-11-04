/*
 * @test
 * @summary Test for Calendar.setInstant(Date) method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetInstantWithValidDate();
        testSetInstantWithNull();
    }

    private static void testSetInstantWithValidDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date(1638316800000L); // 2021-12-01T00:00:00Z
        try {
            Calendar.Builder builder = new Calendar.Builder();
            builder.setInstant(date);
            System.out.println("testSetInstantWithValidDate: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInstantWithValidDate: FAILED - " + e);
        }
    }

    private static void testSetInstantWithNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setInstant((Date) null);
            System.out.println("testSetInstantWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testSetInstantWithNull: PASSED");
        } catch (Exception e) {
            System.out.println("testSetInstantWithNull: FAILED - Unexpected exception: " + e);
        }
    }
}
