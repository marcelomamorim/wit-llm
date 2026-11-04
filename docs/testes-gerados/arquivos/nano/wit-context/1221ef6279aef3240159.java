/*
 * @test
 * @summary Tests for java.util.Calendar class, focusing on the set(int, int) method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetWithInvalidField();
        testSetWithValidField();
        testSetWhenInstantSetThrows();
        testSetWithNextStateThrows();
    }

    private static void testSetWithInvalidField() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(-1, 10);
            System.out.println("FAIL: Expected IllegalArgumentException for field -1");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for field -1");
        }
        try {
            cal.set(Calendar.FIELD_COUNT, 10);
            System.out.println("FAIL: Expected IllegalArgumentException for field >= FIELD_COUNT");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for field >= FIELD_COUNT");
        }
    }

    private static void testSetWithValidField() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.YEAR, 2020);
            if (cal.get(Calendar.YEAR) == 2020) {
                System.out.println("PASS: Successfully set YEAR to 2020");
            } else {
                System.out.println("FAIL: YEAR not set correctly");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetWhenInstantSetThrows() {
        Calendar cal = Calendar.getInstance();
        // Use reflection or subclass to simulate isInstantSet() returning true
        // Since isInstantSet() is protected, we cannot call directly; instead, simulate by subclassing
        Calendar calMock = new Calendar() {
            @Override
            protected boolean isInstantSet() {
                return true;
            }
        };
        try {
            calMock.set(Calendar.YEAR, 2021);
            System.out.println("FAIL: Expected IllegalStateException when instant is set");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException when instant is set");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetWithNextStateThrows() {
        Calendar cal = Calendar.getInstance();
        // Simulate internal state where nextStamp == COMPUTED and fields != null
        // Since internal fields are private, we cannot set directly; assume method behavior
        // We test by calling set with valid field but simulate the condition by subclassing
        Calendar calMock = new Calendar() {
            @Override
            protected boolean isInstantSet() {
                return false;
            }
            @Override
            protected boolean hasNextStampComputed() {
                return true;
            }
            @Override
            protected boolean fieldsNotNull() {
                return true;
            }
            @Override
            protected int getNextStamp() {
                return -1; // simulate COMPUTED
            }
        };
        try {
            calMock.set(Calendar.YEAR, 2022);
            System.out.println("FAIL: Expected IllegalStateException when nextStamp == COMPUTED");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException for nextStamp == COMPUTED");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
