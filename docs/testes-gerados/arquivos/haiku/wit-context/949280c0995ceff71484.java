/*
 * @test
 * @summary Witup-derived tests for ConcurrentHashMap entrySet forEach null action and traversal
 * @run main ConcurrentHashMapWitupTest
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsAllEntries();
        testEmptyMapDoesNotInvokeAction();
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        Consumer<Map.Entry<Integer,Integer>> action = null;
        try {
            map.entrySet().forEach(action);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachVisitsAllEntries() {
        ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, i * 10);
        }
        final int[] count = {0};
        final long[] sum = {0L};
        map.entrySet().forEach(e -> {
            count[0]++;
            if (e.getValue() != e.getKey() * 10) {
                throw new RuntimeException("entry mismatch: " + e.getKey() + "->" + e.getValue());
            }
            sum[0] += e.getValue();
        });
        if (count[0] != 5) {
            throw new RuntimeException("expected 5 visits but was " + count[0]);
        }
        if (sum[0] != 150L) {
            throw new RuntimeException("expected sum 150 but was " + sum[0]);
        }
    }

    static void testEmptyMapDoesNotInvokeAction() {
        ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();
        final int[] count = {0};
        map.entrySet().forEach(e -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("expected no visits but was " + count[0]);
        }
    }
}
