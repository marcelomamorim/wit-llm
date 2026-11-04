/*
 * @test
 * @summary WITUP-derived checks for ConcurrentSkipListMap.replaceAll(BiFunction)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNullPointerException();
        testNullFunctionOnEmptyMapThrowsNullPointerException();
        testFunctionReturningNullThrowsNullPointerException();
        testReplaceAllUpdatesValuesWithKeyAndValue();
        testReplaceAllOnEmptyMapInvokesNoFunction();
    }

    static void testNullFunctionThrowsNullPointerException() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        expectNpe(() -> map.replaceAll(null));
        check(map.get("a") == 1, "map must be unchanged after null function");
    }

    static void testNullFunctionOnEmptyMapThrowsNullPointerException() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        expectNpe(() -> map.replaceAll(null));
    }

    static void testFunctionReturningNullThrowsNullPointerException() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        BiFunction<String, Integer, Integer> f = (k, v) -> null;
        expectNpe(() -> map.replaceAll(f));
    }

    static void testReplaceAllUpdatesValuesWithKeyAndValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.replaceAll((k, v) -> k.equals("b") ? v * 10 : v + 100);
        check(map.get("a") == 101, "a expected 101 but was " + map.get("a"));
        check(map.get("b") == 20, "b expected 20 but was " + map.get("b"));
        check(map.get("c") == 103, "c expected 103 but was " + map.get("c"));
        check(map.size() == 3, "size must remain 3");
    }

    static void testReplaceAllOnEmptyMapInvokesNoFunction() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        final List<String> calls = new ArrayList<>();
        map.replaceAll((k, v) -> {
            calls.add(k);
            return v;
        });
        check(calls.isEmpty(), "function must not be invoked on empty map");
        check(map.isEmpty(), "map must remain empty");
    }

    interface Action {
        void run();
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
