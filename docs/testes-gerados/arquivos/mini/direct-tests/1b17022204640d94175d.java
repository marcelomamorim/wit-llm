/*
 * @test
 * @summary Test Era constructor and basic accessors
 * @run main EraWitupTest
 */

import sun.util.calendar.Era;
import java.util.Locale;

public class EraWitupTest {
    public static void main(String[] args) {
        // Test data
        String name = "TestEra";
        String abbr = "TE";
        long since = 0L; // epoch
        boolean localTime = false;

        Era era = new Era(name, abbr, since, localTime);

        // Check getName returns the name passed
        if (!era.getName().equals(name)) {
            throw new AssertionError("getName() returned '" + era.getName() + "', expected '" + name + "'");
        }

        // Check getAbbreviation returns the abbreviation passed
        if (!era.getAbbreviation().equals(abbr)) {
            throw new AssertionError("getAbbreviation() returned '" + era.getAbbreviation() + "', expected '" + abbr + "'");
        }

        // Check getDisplayName returns the name (locale ignored in current implementation)
        if (!era.getDisplayName(Locale.US).equals(name)) {
            throw new AssertionError("getDisplayName() returned '" + era.getDisplayName(Locale.US) + "', expected '" + name + "'");
        }

        // Check getSince returns the since value passed
        if (era.getSince() != since) {
            throw new AssertionError("getSince() returned " + era.getSince() + ", expected " + since);
        }

        // Check isLocalTime returns the localTime value passed
        if (era.isLocalTime() != localTime) {
            throw new AssertionError("isLocalTime() returned " + era.isLocalTime() + ", expected " + localTime);
        }

        // Check toString contains the name
        String toStr = era.toString();
        if (toStr == null || !toStr.contains(name)) {
            throw new AssertionError("toString() does not contain name: " + toStr);
        }
    }
}
