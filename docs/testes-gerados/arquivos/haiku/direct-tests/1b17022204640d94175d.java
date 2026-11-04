/*
 * @test
 * @summary Basic observable behavior of sun.util.calendar.Era constructor and getters
 * @modules java.base/sun.util.calendar
 * @run main EraWitupTest
 */

import java.util.Locale;
import sun.util.calendar.Era;

public class EraWitupTest {

    public static void main(String[] args) {
        testGettersReturnConstructorValues();
        testDisplayNameIgnoresLocaleAndReturnsName();
        testNullNameIsReturnedAsNull();
        testEpochSinceDoesNotThrow();
    }

    static void testGettersReturnConstructorValues() {
        Era era = new Era("BeforeCommonEra", "B.C.E.", 0L, false);
        check("BeforeCommonEra".equals(era.getName()), "getName mismatch");
        check("B.C.E.".equals(era.getAbbreviation()), "getAbbreviation mismatch");
    }

    static void testDisplayNameIgnoresLocaleAndReturnsName() {
        Era era = new Era("Heisei", "H", 0L, true);
        check("Heisei".equals(era.getDisplayName(Locale.US)), "getDisplayName(US) mismatch");
        check("Heisei".equals(era.getDisplayName(Locale.JAPAN)), "getDisplayName(JAPAN) mismatch");
    }

    static void testNullNameIsReturnedAsNull() {
        Era era = new Era(null, "X", 0L, false);
        check(era.getName() == null, "null name should be returned as null");
        check("X".equals(era.getAbbreviation()), "abbreviation should be preserved with null name");
    }

    static void testEpochSinceDoesNotThrow() {
        Era era = new Era("Meiji", "M", -3242800000000L, true);
        check("Meiji".equals(era.getName()), "constructor with negative since failed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
