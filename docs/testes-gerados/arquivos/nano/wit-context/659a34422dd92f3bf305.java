/*
 * @test
 * @summary Test for ImmutableGregorianDate.addMonth(int n)
 * @run main ImmutableGregorianDateWitupTest
 */

import sun.util.calendar.ImmutableGregorianDate;

public class ImmutableGregorianDateWitupTest {

    public static void main(String[] args) {
        testAddMonthThrowsUnsupported();
        testAddMonthReturnsThis();
    }

    private static void testAddMonthThrowsUnsupported() {
        ImmutableGregorianDate date = createSampleImmutableGregorianDate();
        try {
            date.addMonth(1);
            System.out.println("FAIL: Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: addMonth throws UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testAddMonthReturnsThis() {
        ImmutableGregorianDate date = createSampleImmutableGregorianDate();
        ImmutableGregorianDate result = date.addMonth(1);
        if (result == date) {
            System.out.println("PASS: addMonth returns the same instance");
        } else {
            System.out.println("FAIL: addMonth does not return the same instance");
        }
    }

    private static ImmutableGregorianDate createSampleImmutableGregorianDate() {
        // Create a dummy BaseCalendar.Date instance as a placeholder
        // Since constructor is package-private, we assume a static factory or similar; here, we simulate with null
        // but to keep the test compile, we create a subclass or mock if needed. For simplicity, assume null is acceptable.
        // In real scenario, replace with actual instantiation if accessible.
        return new ImmutableGregorianDate(new sun.util.calendar.BaseCalendar.Date() {
            @Override public Era getEra() { return null; }
            @Override public int getYear() { return 2023; }
            @Override public int getMonth() { return 1; }
            @Override public int getDayOfMonth() { return 1; }
            @Override public boolean isLeapYear() { return false; }
            // Implement other abstract methods as needed, or leave as default if not used
        });
    }
}
