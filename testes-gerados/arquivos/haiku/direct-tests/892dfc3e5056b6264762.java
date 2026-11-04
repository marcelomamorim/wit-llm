/*
 * @test
 * @summary Verifies Provider.forEach visits the entries stored in an initialized provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {

    static final class TestProvider extends Provider {
        TestProvider() {
            super("TestProv", "1.0", "info");
        }
    }

    static final class TestProviderDouble extends Provider {
        TestProviderDouble() {
            super("TestProvD", 2.0, "info");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testForEachVisitsAllEntries() {
        TestProvider p = new TestProvider();
        p.put("k1", "v1");
        p.put("k2", "v2");

        Map<Object, Object> seen = new HashMap<>();
        int[] calls = {0};
        p.forEach((k, v) -> {
            calls[0]++;
            seen.put(k, v);
        });

        check(calls[0] == 2, "expected 2 callbacks but got " + calls[0]);
        check("v1".equals(seen.get("k1")), "k1 not visited with v1");
        check("v2".equals(seen.get("k2")), "k2 not visited with v2");
    }

    static void testForEachReflectsUpdatedValue() {
        TestProvider p = new TestProvider();
        p.put("key", "old");
        p.put("key", "new");

        Map<Object, Object> seen = new HashMap<>();
        p.forEach((k, v) -> seen.put(k, v));

        check(seen.size() == 1, "expected a single entry but got " + seen.size());
        check("new".equals(seen.get("key")), "forEach did not observe updated value");
    }

    static void testForEachWithDoubleVersionConstructor() {
        TestProviderDouble p = new TestProviderDouble();
        p.put("a", "b");

        Map<Object, Object> seen = new HashMap<>();
        p.forEach((k, v) -> seen.put(k, v));

        check("b".equals(seen.get("a")), "entry not visited for double-version provider");
    }

    public static void main(String[] args) {
        testForEachVisitsAllEntries();
        testForEachReflectsUpdatedValue();
        testForEachWithDoubleVersionConstructor();
    }
}
