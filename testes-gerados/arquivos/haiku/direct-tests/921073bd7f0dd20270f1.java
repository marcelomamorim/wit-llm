/*
 * @test
 * @summary Observable behavior of System.getProperty(String) for absent, set, updated and cleared keys
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        String key = "witup.getProperty.test.key";
        System.clearProperty(key);

        if (System.getProperty(key) != null) {
            throw new RuntimeException("expected null for absent key");
        }

        System.setProperty(key, "v1");
        if (!"v1".equals(System.getProperty(key))) {
            throw new RuntimeException("expected v1 after setProperty");
        }

        System.setProperty(key, "v2");
        if (!"v2".equals(System.getProperty(key))) {
            throw new RuntimeException("expected v2 after overwrite");
        }

        String previous = System.clearProperty(key);
        if (!"v2".equals(previous)) {
            throw new RuntimeException("clearProperty should return previous value v2");
        }
        if (System.getProperty(key) != null) {
            throw new RuntimeException("expected null after clearProperty");
        }
    }
}
