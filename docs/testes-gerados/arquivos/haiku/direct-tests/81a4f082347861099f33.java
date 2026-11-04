/*
 * @test
 * @summary Tests Year.minusYears for normal values, zero, negative arguments, range overflow and Long.MIN_VALUE
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        int failures = 0;
        failures += check("subtract positive", () -> Year.of(2020).minusYears(5).getValue() == 2015);
        failures += check("subtract zero", () -> Year.of(2020).minusYears(0).getValue() == 2020);
        failures += check("subtract negative", () -> Year.of(2020).minusYears(-3).getValue() == 2023);
        failures += check("crosses zero", () -> Year.of(0).minusYears(1).getValue() == -1);
        failures += check("subtract to min boundary", () -> Year.of(Year.MIN_VALUE + 1).minusYears(1).getValue() == Year.MIN_VALUE);
        failures += checkThrows("below min boundary", () -> Year.of(Year.MIN_VALUE).minusYears(1));
        failures += checkThrows("above max boundary", () -> Year.of(Year.MAX_VALUE).minusYears(-1));
        failures += checkThrows("Long.MIN_VALUE argument", () -> Year.of(2020).minusYears(Long.MIN_VALUE));
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private interface Check {
        boolean run();
    }

    private interface Action {
        void run();
    }

    private static int check(String name, Check c) {
        try {
            if (c.run()) {
                return 0;
            }
            System.err.println("FAILED: " + name);
            return 1;
        } catch (RuntimeException e) {
            System.err.println("FAILED with exception: " + name + " " + e);
            return 1;
        }
    }

    private static int checkThrows(String name, Action a) {
        try {
            a.run();
            System.err.println("FAILED: no exception: " + name);
            return 1;
        } catch (DateTimeException e) {
            return 0;
        }
    }
}
