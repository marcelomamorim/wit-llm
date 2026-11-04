/*
 * @test
 * @summary Witup-generated checks for Calendar.Builder.set(int, int)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testNegativeFieldThrowsIllegalArgument();
        testFieldEqualToFieldCountThrowsIllegalArgument();
        testValidFieldReturnsSameBuilder();
        testSetAfterSetInstantThrowsIllegalState();
    }

    static void testNegativeFieldThrowsIllegalArgument() {
        try {
            new Calendar.Builder().set(-1, 0);
            throw new AssertionError("expected IllegalArgumentException for field -1");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testFieldEqualToFieldCountThrowsIllegalArgument() {
        try {
            new Calendar.Builder().set(17, 0);
            throw new AssertionError("expected IllegalArgumentException for field 17");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testValidFieldReturnsSameBuilder() {
        Calendar.Builder builder = new Calendar.Builder();
        Calendar.Builder result = builder.set(Calendar.YEAR, 2000);
        if (result != builder) {
            throw new AssertionError("set(int, int) should return the same Builder");
        }
    }

    static void testSetAfterSetInstantThrowsIllegalState() {
        Calendar.Builder builder = new Calendar.Builder().setInstant(0L);
        try {
            builder.set(Calendar.YEAR, 2000);
            throw new AssertionError("expected IllegalStateException after setInstant");
        } catch (IllegalStateException expected) {
        }
    }
}
