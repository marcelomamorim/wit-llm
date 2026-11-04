/*
 * @test
 * @summary Checks public observable behavior of Provider.replace(Object, Object) on an initialized provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Provider p = new Provider("TestProv", "1.0", "info") { };

        p.put("Test.Key", "old");

        Object prev = p.replace("Test.Key", "new");
        check("old".equals(prev),
              "replace should return the previous value, got: " + prev);
        check("new".equals(p.get("Test.Key")),
              "get should observe the replaced value");

        Object prev2 = p.replace("Test.Key", "newer");
        check("new".equals(prev2),
              "second replace should return the previous replaced value, got: " + prev2);
        check("newer".equals(p.get("Test.Key")),
              "get should observe the second replaced value");
    }
}
