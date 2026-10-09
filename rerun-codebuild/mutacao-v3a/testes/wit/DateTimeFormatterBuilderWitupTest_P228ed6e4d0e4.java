/*
 * @test
 * @summary Tests appendInstant method for valid and invalid fractionalDigits.
 * @run main DateTimeFormatterBuilderWitupTest_P228ed6e4d0e4
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P228ed6e4d0e4 {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalidNegativeOne();
        testAppendInstantInvalidGreaterThanNine();
    }

    static void testAppendInstantValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(0);
            builder.appendInstant(9);
            builder.appendInstant(5);
            System.out.println("testAppendInstantValid passed");
        } catch (Exception e) {
            System.out.println("testAppendInstantValid failed: " + e);
        }
    }

    static void testAppendInstantInvalidNegativeOne() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(-1);
            System.out.println("testAppendInstantInvalidNegativeOne failed: no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testAppendInstantInvalidNegativeOne passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testAppendInstantInvalidNegativeOne failed: " + e);
        }
    }

    static void testAppendInstantInvalidGreaterThanNine() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(10);
            System.out.println("testAppendInstantInvalidGreaterThanNine failed: no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testAppendInstantInvalidGreaterThanNine passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testAppendInstantInvalidGreaterThanNine failed: " + e);
        }
    }
}
