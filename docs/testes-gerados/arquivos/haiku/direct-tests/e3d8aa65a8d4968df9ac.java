/*
 * @test
 * @summary Verifies Provider.getOrDefault returns stored values and the supplied default for absent keys
 * @run main ProviderWitupTest
 */

public class ProviderWitupTest {

    static class TestProvider extends java.security.Provider {
        TestProvider() {
            super("WitupTestProvider", 1.0, "witup info");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        p.put("k1", "v1");
        check("v1".equals(p.getOrDefault("k1", "def")),
              "present key should return stored value");

        check("def".equals(p.getOrDefault("absent", "def")),
              "absent key should return supplied default");

        check(p.getOrDefault("absent", null) == null,
              "absent key with null default should return null");

        Object dv = new Object();
        check(p.getOrDefault("missing", dv) == dv,
              "absent key should return the same default instance");

        p.put("k2", "v2");
        check("v2".equals(p.getOrDefault("k2", "x")),
              "second stored key should be retrievable");

        check("v1".equals(p.getOrDefault("k1", "x")),
              "earlier key should still be retrievable after another put");
    }
}
