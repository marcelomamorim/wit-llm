/*
 * @test
 * @summary Test constructor Era(String, String, long, boolean) for basic correctness
 * @run main EraWitupTest
 */

public class EraWitupTest {
    public static void main(String[] args) {
        // Test normal construction with typical values
        String name = "TestEra";
        String abbr = "TE";
        long since = 0L; // epoch
        boolean localTime = false;

        sun.util.calendar.Era era = new sun.util.calendar.Era(name, abbr, since, localTime);

        if (!era.getName().equals(name)) {
            throw new RuntimeException("getName() returned unexpected value");
        }
        if (!era.getAbbreviation().equals(abbr)) {
            throw new RuntimeException("getAbbreviation() returned unexpected value");
        }

        // getDisplayName should return the name regardless of locale
        if (!era.getDisplayName(java.util.Locale.US).equals(name)) {
            throw new RuntimeException("getDisplayName() returned unexpected value");
        }

        // Test with localTime true
        sun.util.calendar.Era eraLocal = new sun.util.calendar.Era("LocalEra", "LE", since, true);
        if (!eraLocal.getName().equals("LocalEra")) {
            throw new RuntimeException("getName() with localTime true returned unexpected value");
        }

        // Test with a large positive since value
        long largeSince = 253402300799000L; // near year 9999
        sun.util.calendar.Era eraFuture = new sun.util.calendar.Era("FutureEra", "FE", largeSince, false);
        if (!eraFuture.getName().equals("FutureEra")) {
            throw new RuntimeException("getName() with large since returned unexpected value");
        }

        // Test with a large negative since value
        long largeNegativeSince = -62135769600000L; // year 0001-01-01 UTC approx
        sun.util.calendar.Era eraPast = new sun.util.calendar.Era("PastEra", "PE", largeNegativeSince, false);
        if (!eraPast.getName().equals("PastEra")) {
            throw new RuntimeException("getName() with large negative since returned unexpected value");
        }

        // No exceptions should be thrown for these valid inputs
        System.out.println("All Era constructor tests passed.");
    }
}
