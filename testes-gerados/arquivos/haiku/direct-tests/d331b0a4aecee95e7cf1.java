/*
 * @test
 * @summary Tests Calendar.Builder.setCalendarType validation, alias mapping and override behavior
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGregorianAliasIsMapped() {
        Calendar cal = new Calendar.Builder().setCalendarType("gregorian").build();
        check("gregory".equals(cal.getCalendarType()), "gregorian should be mapped to gregory");
        check(cal instanceof GregorianCalendar, "gregory type should build a GregorianCalendar");
    }

    static void testGregoryAccepted() {
        Calendar cal = new Calendar.Builder().setCalendarType("gregory").build();
        check("gregory".equals(cal.getCalendarType()), "gregory type should be preserved");
    }

    static void testReturnsSameBuilder() {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r = b.setCalendarType("gregory");
        check(r == b, "setCalendarType must return this builder");
    }

    static void testRepeatedSameTypeAllowed() {
        Calendar.Builder b = new Calendar.Builder();
        b.setCalendarType("gregory");
        b.setCalendarType("gregorian");
        Calendar cal = b.build();
        check("gregory".equals(cal.getCalendarType()), "same type set twice should not fail");
    }

    static void testNullTypeThrowsNPE() {
        boolean thrown = false;
        try {
            new Calendar.Builder().setCalendarType(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null type should throw NullPointerException");
    }

    static void testUnknownTypeThrowsIAE() {
        boolean thrown = false;
        try {
            new Calendar.Builder().setCalendarType("no-such-calendar");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "unknown calendar type should throw IllegalArgumentException");
    }

    static void testOverrideWithDifferentTypeThrowsISE() {
        Calendar.Builder b = new Calendar.Builder();
        b.setCalendarType("buddhist");
        boolean thrown = false;
        try {
            b.setCalendarType("gregory");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "overriding calendar type should throw IllegalStateException");
    }

    public static void main(String[] args) {
        testGregorianAliasIsMapped();
        testGregoryAccepted();
        testReturnsSameBuilder();
        testRepeatedSameTypeAllowed();
        testNullTypeThrowsNPE();
        testUnknownTypeThrowsIAE();
        testOverrideWithDifferentTypeThrowsISE();
    }
}
