/*
 * @test
 * @summary Basic observable behavior of ConcurrentSkipListMap.forEach
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapInvokesNothing();
        testNaturalOrderVisitsSortedEntries();
        testComparatorOrderIsRespected();
        testValuesMatchPutMappings();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        boolean thrown = false;
        try {
            m.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testEmptyMapInvokesNothing() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        final int[] calls = {0};
        m.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testNaturalOrderVisitsSortedEntries() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        List<String> seen = new ArrayList<>();
        m.forEach((k, v) -> seen.add(k + "=" + v));
        String got = seen.toString();
        check(got.equals("[1=a, 2=b, 3=c]"), "unexpected order: " + got);
    }

    static void testComparatorOrderIsRespected() {
        ConcurrentSkipListMap<Integer, String> m =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        m.put(1, "a");
        m.put(3, "c");
        m.put(2, "b");
        List<String> seen = new ArrayList<>();
        m.forEach((k, v) -> seen.add(k + "=" + v));
        String got = seen.toString();
        check(got.equals("[3=c, 2=b, 1=a]"), "unexpected comparator order: " + got);
    }

    static void testValuesMatchPutMappings() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("x", 10);
        m.put("y", 20);
        final long[] sum = {0L};
        final int[] count = {0};
        m.forEach((k, v) -> {
            sum[0] += v;
            count[0]++;
        });
        check(count[0] == 2, "expected 2 visits but got " + count[0]);
        check(sum[0] == 30L, "expected value sum 30 but got " + sum[0]);
    }
}
