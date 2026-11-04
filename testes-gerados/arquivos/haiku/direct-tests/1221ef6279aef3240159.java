/*
 * @test
 * @summary Verify argument validation and fluent return behavior of Calendar.Builder.set(int, int)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilderForValidField();
        testNegativeFieldRejected();
        testFieldCountRejected();
        testFieldCountMinusOneRejected();
    }

    static void testReturnsSameBuilderForValidField() {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r = b.set(Calendar.YEAR, 2020);
        if (r != b) {
            throw new RuntimeException("set(YEAR, 2020) must return this builder");
        }
        Calendar.Builder r2 = r.set(Calendar.MONTH, Calendar.JANUARY);
        if (r2 != b) {
            throw new RuntimeException("set(MONTH, JANUARY) must return this builder");
        }
    }

    static void testNegativeFieldRejected() {
        Calendar.Builder b = new Calendar.Builder();
        try {
            b.set(-1, 0);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("set(-1, 0) must throw IllegalArgumentException");
    }

    static void testFieldCountRejected() {
        Calendar.Builder b = new Calendar.Builder();
        try {
            b.set(Calendar.FIELD_COUNT, 0);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("set(FIELD_COUNT, 0) must throw IllegalArgumentException");
    }

    static void testFieldCountMinusOneRejected() {
        Calendar.Builder b = new Calendar.Builder();
        try {
            b.set(Calendar.FIELD_COUNT - 1, 0);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("FIELD_COUNT - 1 is a valid field index and must not be rejected", e);
        }
    }
}
