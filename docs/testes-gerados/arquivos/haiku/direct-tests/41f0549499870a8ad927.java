/*
 * @test
 * @summary Verifies System.getProperty(String, String) returns the default only when the key is absent
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        String absentKey = "witup.test.absent.key";
        System.clearProperty(absentKey);
        check("absent key returns default", "dflt".equals(System.getProperty(absentKey, "dflt")));
        check("absent key with null default returns null", System.getProperty(absentKey, null) == null);

        String presentKey = "witup.test.present.key";
        System.setProperty(presentKey, "value");
        check("present key returns value", "value".equals(System.getProperty(presentKey, "dflt")));
        check("present key ignores null default", "value".equals(System.getProperty(presentKey, null)));

        String emptyKey = "witup.test.empty.value.key";
        System.setProperty(emptyKey, "");
        check("empty value is returned, not default", "".equals(System.getProperty(emptyKey, "dflt")));

        System.clearProperty(presentKey);
        check("cleared key returns default", "dflt".equals(System.getProperty(presentKey, "dflt")));

        System.clearProperty(emptyKey);
    }

    private static void check(String msg, boolean cond) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
