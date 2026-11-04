/*
 * @test
 * @summary Verifies observable behavior of Provider.get(Object) for present, absent and non-String keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest extends Provider {

    private static final long serialVersionUID = 1L;

    ProviderWitupTest() {
        super("WitupTestProvider", "1.0", "witup test provider");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ProviderWitupTest p = new ProviderWitupTest();

        check(p.get("missing") == null, "get on absent key must return null");

        p.put("k1", "v1");
        check("v1".equals(p.get("k1")), "get must return the value previously put");

        p.put("k2", "v2");
        check("v2".equals(p.get("k2")), "get must return the value for the second key");
        check("v1".equals(p.get("k1")), "earlier key must remain retrievable");

        check(p.get(Integer.valueOf(1)) == null, "get with a non-matching key type must return null");

        p.put("k1", "v3");
        check("v3".equals(p.get("k1")), "get must reflect the latest put for the same key");
    }
}
