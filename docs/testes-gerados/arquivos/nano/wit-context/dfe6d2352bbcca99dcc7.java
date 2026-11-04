/*
 * @test
 * @summary Tests for sun.util.calendar.JulianCalendar.setEra(Era era)
 * @run main JulianCalendarWitupTest
 */

import sun.util.calendar.JulianCalendar;
import sun.util.calendar.Era;

public class JulianCalendarWitupTest {

    public static void main(String[] args) {
        testSetEraWithNull();
        testSetEraWithValidEras();
        testSetEraWithInvalidEra();
    }

    private static void testSetEraWithNull() {
        JulianCalendar cal = new JulianCalendar();
        try {
            cal.setEra(null);
            System.out.println("testSetEraWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testSetEraWithNull: PASSED");
        } catch (Exception e) {
            System.out.println("testSetEraWithNull: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testSetEraWithValidEras() {
        JulianCalendar cal = new JulianCalendar();
        Era[] eras = {
            new Era("BeforeCommonEra", "B.C.E.", Long.MIN_VALUE, false),
            new Era("CommonEra", "C.E.", -62135709175808L, true)
        };
        boolean passed = true;
        for (Era era : eras) {
            try {
                cal.setEra(era);
                System.out.println("setEra with " + era.getName() + " succeeded.");
            } catch (Exception e) {
                System.out.println("setEra with " + era.getName() + " failed: " + e);
                passed = false;
            }
        }
        if (passed) {
            System.out.println("testSetEraWithValidEras: PASSED");
        } else {
            System.out.println("testSetEraWithValidEras: FAILED");
        }
    }

    private static void testSetEraWithInvalidEra() {
        JulianCalendar cal = new JulianCalendar();
        Era invalidEra = new Era("InvalidEra", "INV", 0L, false) {
            @Override
            public String getName() {
                return "InvalidEra";
            }
        };
        try {
            cal.setEra(invalidEra);
            System.out.println("testSetEraWithInvalidEra: FAILED - Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetEraWithInvalidEra: PASSED");
        } catch (Exception e) {
            System.out.println("testSetEraWithInvalidEra: FAILED - Unexpected exception: " + e);
        }
    }
}
