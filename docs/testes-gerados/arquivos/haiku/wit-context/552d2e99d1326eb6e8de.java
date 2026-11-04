/*
 * @test
 * @summary Basic observable behavior of Provider.elements() on an initialized provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Enumeration<Object> empty = p.elements();
        if (empty == null) {
            throw new RuntimeException("elements() returned null");
        }
        if (empty.hasMoreElements()) {
            throw new RuntimeException("expected no elements in a new provider");
        }

        p.put("test.key", "test.value");

        Enumeration<Object> e = p.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected one element after put");
        }
        Object v = e.nextElement();
        if (!"test.value".equals(v)) {
            throw new RuntimeException("unexpected element: " + v);
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("expected exactly one element");
        }
    }
}
