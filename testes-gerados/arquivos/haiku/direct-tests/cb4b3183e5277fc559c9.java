/*
 * @test
 * @summary Provider.putIfAbsent stores the first value and returns the existing value afterwards
 * @run main ProviderWitupTest
 */
import java.security.Provider;

public class ProviderWitupTest extends Provider {

    ProviderWitupTest() {
        super("WitupTestProvider", "1.0", "witup test provider");
    }

    public static void main(String[] args) throws Exception {
        ProviderWitupTest p = new ProviderWitupTest();

        Object r1 = p.putIfAbsent("k", "v1");
        if (r1 != null) {
            throw new RuntimeException("Expected null for absent key, got: " + r1);
        }
        if (!"v1".equals(p.getProperty("k"))) {
            throw new RuntimeException("Expected stored value v1, got: " + p.getProperty("k"));
        }

        Object r2 = p.putIfAbsent("k", "v2");
        if (!"v1".equals(r2)) {
            throw new RuntimeException("Expected existing value v1 returned, got: " + r2);
        }
        if (!"v1".equals(p.get("k"))) {
            throw new RuntimeException("Value must not be replaced, got: " + p.get("k"));
        }

        Object r3 = p.putIfAbsent("other", "x");
        if (r3 != null) {
            throw new RuntimeException("Expected null for new key, got: " + r3);
        }
        if (!"x".equals(p.getProperty("other"))) {
            throw new RuntimeException("Expected stored value x, got: " + p.getProperty("other"));
        }
    }
}
