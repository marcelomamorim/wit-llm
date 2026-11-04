/*
 * @test
 * @summary Tests for sun.util.calendar.Era constructor
 * @run main EraWitupTest
 */

import sun.util.calendar.Era;
import java.util.Locale;

public class EraWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullName();
        testNullAbbr();
        testNegativeSince();
        testLargeSince();
    }

    static void testValidConstruction() {
        try {
            Era era = new Era("TestEra", "TE", 1625097600000L, true);
            assert "TestEra"equals(era.getName()) : "Name mismatch";
            assert "TE"equals(era.getAbbreviation()) : "Abbreviation mismatch";
            String displayName = era.getDisplayName(Locale.US);
            assert "TestEra"equals(displayName) : "Display name mismatch";
        } catch (Throwable t) {
            throw new AssertionError("Valid construction failed", t);
        }
    }

    static void testNullName() {
        try {
            new Era(null, "AB", 1625097600000L, false);
            // Expect no exception, name can be null, but test for null handling if needed
        } catch (NullPointerException e) {
            throw new AssertionError("Constructor threw NullPointerException for null name", e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null name", t);
        }
    }

    static void testNullAbbr() {
        try {
            new Era("Test", null, 1625097600000L, false);
        } catch (NullPointerException e) {
            throw new AssertionError("Constructor threw NullPointerException for null abbreviation", e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null abbreviation", t);
        }
    }

    static void testNegativeSince() {
        try {
            Era era = new Era("NegativeSince", "NS", -100000L, false);
            assert "NegativeSince"equals(era.getName()) : "Name mismatch";
            assert "NS"equals(era.getAbbreviation()) : "Abbreviation mismatch";
        } catch (Throwable t) {
            throw new AssertionError("Construction with negative since failed", t);
        }
    }

    static void testLargeSince() {
        try {
            long largeSince = Long.MAX_VALUE - 1000L;
            Era era = new Era("LargeSince", "LS", largeSince, true);
            assert "LargeSince"equals(era.getName()) : "Name mismatch";
            assert "LS"equals(era.getAbbreviation()) : "Abbreviation mismatch";
        } catch (Throwable t) {
            throw new AssertionError("Construction with large since failed", t);
        }
    }
}
