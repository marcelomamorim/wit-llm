/*
 * @test
 * @summary Deterministic observable-behavior tests for the sun.util.calendar.Era constructor
 * @modules java.base/sun.util.calendar
 * @run main EraWitupTest
 */

import sun.util.calendar.Era;
import java.util.Locale;

public class EraWitupTest {

    public static void main(String[] args) {
        testConstructorStoresNameAndAbbreviationEpoch();
        testConstructorStoresNameAndAbbreviationUtcFlag();
        testConstructorAcceptsPositiveSince();
        testDisplayNameMatchesConstructorName();
    }

    static void testConstructorStoresNameAndAbbreviationEpoch() {
        Era era = new Era("Heisei", "H", 0L, true);
        check("Heisei".equals(era.getName()), "getName mismatch");
        check("H".equals(era.getAbbreviation()), "getAbbreviation mismatch");
    }

    static void testConstructorStoresNameAndAbbreviationUtcFlag() {
        Era era = new Era("Showa", "S", 0L, false);
        check("Showa".equals(era.getName()), "getName mismatch for UTC era");
        check("S".equals(era.getAbbreviation()), "getAbbreviation mismatch for UTC era");
    }

    static void testConstructorAcceptsPositiveSince() {
        Era era = new Era("Reiwa", "R", 1556668800000L, true);
        check("Reiwa".equals(era.getName()), "getName mismatch for positive since");
        check("R".equals(era.getAbbreviation()), "getAbbreviation mismatch for positive since");
    }

    static void testDisplayNameMatchesConstructorName() {
        Era era = new Era("BeforeCommonEra", "B.C.E.", 0L, false);
        check("BeforeCommonEra".equals(era.getDisplayName(Locale.US)),
              "getDisplayName mismatch");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
