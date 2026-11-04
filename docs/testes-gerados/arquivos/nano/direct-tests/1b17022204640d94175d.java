/*
 * @test
 * @summary Tests the Era constructor for correct initialization and behavior.
 * @run main EraWitupTest
 */

import sun.util.calendar.Era;
import java.util.Locale;

public class EraWitupTest {

    public static void main(String[] args) {
        testEraConstructorWithValidData();
        testEraConstructorWithZeroSince();
        testEraConstructorWithNegativeSince();
        testEraConstructorWithEmptyStrings();
    }

    static void testEraConstructorWithValidData() {
        String name = "TestEra";
        String abbr = "TE";
        long since = 1625097600000L; // Corresponds to 2021-07-01T00:00:00Z
        boolean localTime = false;
        Era era = new Era(name, abbr, since, localTime);
        assert era.getName().equals(name) : "Name mismatch";
        assert era.getAbbreviation().equals(abbr) : "Abbreviation mismatch";
        assert era.getDisplayName(Locale.getDefault()).equals(name) : "Display name mismatch";
    }

    static void testEraConstructorWithZeroSince() {
        String name = "ZeroSinceEra";
        String abbr = "ZSE";
        long since = 0L; // Epoch
        boolean localTime = true;
        Era era = new Era(name, abbr, since, localTime);
        assert era.getName().equals(name) : "Name mismatch with zero since";
        assert era.getAbbreviation().equals(abbr) : "Abbreviation mismatch with zero since";
    }

    static void testEraConstructorWithNegativeSince() {
        String name = "NegativeSinceEra";
        String abbr = "NSE";
        long since = -1000000000L; // Before epoch
        boolean localTime = false;
        Era era = new Era(name, abbr, since, localTime);
        assert era.getName().equals(name) : "Name mismatch with negative since";
        assert era.getAbbreviation().equals(abbr) : "Abbreviation mismatch with negative since";
    }

    static void testEraConstructorWithEmptyStrings() {
        String name = "";
        String abbr = "";
        long since = 1625097600000L;
        boolean localTime = false;
        Era era = new Era(name, abbr, since, localTime);
        assert era.getName().equals("") : "Empty name mismatch";
        assert era.getAbbreviation().equals("") : "Empty abbreviation mismatch";
    }
}
