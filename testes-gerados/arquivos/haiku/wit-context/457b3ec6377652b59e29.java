/*
 * @test
 * @summary Verifies observable behavior of Properties(Properties defaults), including default lookup and null defaults.
 * @run main PropertiesWitupTest
 */

public class PropertiesWitupTest {

    public static void main(String[] args) {
        Properties defaults = new Properties();
        defaults.setProperty("a", "1");

        Properties p = new Properties(defaults);

        check("1".equals(p.getProperty("a")), "default value must be visible through getProperty");
        check(p.getProperty("missing") == null, "missing key without default must return null");
        check("1".equals(p.getProperty("a", "x")), "defaults must take precedence over the supplied fallback");
        check(p.getProperty("missing", "x").equals("x"), "fallback must be returned when key is absent everywhere");
        check(p.size() == 0, "defaults must not be counted in the own entries");

        p.setProperty("a", "2");
        check("2".equals(p.getProperty("a")), "own value must shadow the default");
        check("1".equals(defaults.getProperty("a")), "defaults must not be modified by the child");

        Properties withNullDefaults = new Properties((Properties) null);
        check(withNullDefaults.getProperty("a") == null, "null defaults must behave as no defaults");
        withNullDefaults.setProperty("b", "3");
        check("3".equals(withNullDefaults.getProperty("b")), "properties must still be settable with null defaults");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
