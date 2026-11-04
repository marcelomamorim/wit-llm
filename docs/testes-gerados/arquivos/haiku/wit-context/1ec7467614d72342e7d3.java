/*
 * @test
 * @summary Witup-generated checks for ConcurrentHashMap.replace(K, V, V)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullArgumentsThrowNPE();
        testConditionalReplace();
    }

    static void testNullArgumentsThrowNPE() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "v");

        expectNPE(() -> map.replace(null, "v", "x"));
        expectNPE(() -> map.replace("k", null, "x"));
        expectNPE(() -> map.replace("k", "v", null));
        expectNPE(() -> map.replace(null, null, null));

        check(map.size() == 1);
        check("v".equals(map.get("k")));
    }

    static void testConditionalReplace() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "v");

        check(map.replace("k", "v", "x"));
        check("x".equals(map.get("k")));

        check(!map.replace("k", "v", "y"));
        check("x".equals(map.get("k")));

        check(!map.replace("absent", "v", "y"));
        check(!map.containsKey("absent"));
        check(map.size() == 1);
    }

    static void expectNPE(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("check failed");
        }
    }
}
