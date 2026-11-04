/*
 * @test
 * @summary Tests for java.time.Year.atMonth(Month)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.YearMonth;
import java.time.Month;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthWithNull();
        testAtMonthWithInvalidMonth();
        testAtMonthWithValidMonth();
    }

    private static void testAtMonthWithNull() {
        Year year = Year.of(2020);
        try {
            year.atMonth(null);
            System.out.println("FAIL: Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAtMonthWithInvalidMonth() {
        Year year = Year.of(2020);
        Month invalidMonth = null; // Simulate invalid month, as Month is enum, so invalid value can't be created
        // Since Month is enum, invalid value can't be instantiated; instead, test with null to simulate invalid
        try {
            year.atMonth(invalidMonth);
            System.out.println("FAIL: Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for invalid month");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAtMonthWithValidMonth() {
        Year year = Year.of(2021);
        for (Month month : Month.values()) {
            try {
                YearMonth ym = year.atMonth(month);
                if (ym.getYear() == 2021 && ym.getMonth() == month) {
                    System.out.println("PASS: atMonth with " + month + " returned correct YearMonth");
                } else {
                    System.out.println("FAIL: atMonth with " + month + " returned incorrect YearMonth");
                }
            } catch (Exception e) {
                System.out.println("FAIL: Exception for month " + month + ": " + e);
            }
        }
    }
}
