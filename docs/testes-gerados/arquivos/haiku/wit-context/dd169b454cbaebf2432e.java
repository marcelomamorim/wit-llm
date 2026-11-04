/*
 * @test
 * @summary WITUP-derived checks for ConcurrentHashMap values().forEach null action and traversal
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> empty = new ConcurrentHashMap<>();
        expectNpe(empty);

        final int[] calls = {0};
        empty.values().forEach(v -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");

        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        expectNpe(m);

        List<Integer> seen = new ArrayList<>();
        m.values().forEach(seen::add);
        check(seen.size() == 3, "expected 3 values but got " + seen.size());
        check(seen.contains(1) && seen.contains(2) && seen.contains(3), "missing values: " + seen);
        int sum = 0;
        for (int x : seen) {
            sum += x;
        }
        check(sum == 6, "unexpected sum " + sum);
    }

    private static void expectNpe(ConcurrentHashMap<String, Integer> map) {
        try {
            map.values().forEach(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null action");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
