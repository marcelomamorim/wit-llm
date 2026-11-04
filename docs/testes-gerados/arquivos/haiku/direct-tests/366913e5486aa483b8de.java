/*
 * @test
 * @summary Verify that replaceAll on immutable maps throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        BiFunction<String, Integer, Integer> inc = (k, v) -> v + 1;

        Map<String, Integer> single = Map.of("a", 1);
        expectUoe(single, inc, "Map.of single entry");
        check(single.get("a") == 1, "map modified after failed replaceAll");

        Map<String, Integer> multi = Map.of("a", 1, "b", 2, "c", 3);
        expectUoe(multi, inc, "Map.of multiple entries");
        check(multi.get("b") == 2, "multi-entry map modified after failed replaceAll");

        Map<String, Integer> empty = Map.of();
        expectUoe(empty, inc, "Map.of empty");
        check(empty.isEmpty(), "empty map changed after failed replaceAll");

        Map<String, Integer> fromEntries = Map.ofEntries(Map.entry("x", 10));
        expectUoe(fromEntries, inc, "Map.ofEntries");
        check(fromEntries.get("x") == 10, "ofEntries map modified after failed replaceAll");

        HashMap<String, Integer> src = new HashMap<>();
        src.put("k", 5);
        Map<String, Integer> copy = Map.copyOf(src);
        expectUoe(copy, inc, "Map.copyOf");
        check(copy.get("k") == 5, "copyOf map modified after failed replaceAll");
    }

    static void expectUoe(Map<String, Integer> m, BiFunction<String, Integer, Integer> f, String name) {
        try {
            m.replaceAll(f);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException(name + ": expected UnsupportedOperationException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
