/*
 * @test
 * @summary Checks TreeMap.headMap(K, boolean) observable results and range checks on sub-map views
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    interface Action {
        void run();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(Action action, String message) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> tm = new TreeMap<>();
        for (int i = 1; i <= 9; i++) {
            tm.put(i, "v" + i);
        }

        NavigableMap<Integer, String> exclusive = tm.headMap(5, false);
        check(exclusive.size() == 4, "headMap(5,false) size expected 4 but was " + exclusive.size());
        check(exclusive.lastKey() == 4, "headMap(5,false) last key expected 4");
        check(!exclusive.containsKey(5), "headMap(5,false) must not contain 5");

        NavigableMap<Integer, String> inclusive = tm.headMap(5, true);
        check(inclusive.size() == 5, "headMap(5,true) size expected 5 but was " + inclusive.size());
        check(inclusive.lastKey() == 5, "headMap(5,true) last key expected 5");

        NavigableMap<Integer, String> beyond = tm.headMap(100, true);
        check(beyond.size() == 9, "headMap(100,true) on root map expected size 9 but was " + beyond.size());

        NavigableMap<Integer, String> sub = tm.subMap(3, true, 7, true);
        NavigableMap<Integer, String> subHeadInclusive = sub.headMap(6, true);
        check(subHeadInclusive.size() == 4, "sub.headMap(6,true) size expected 4 but was " + subHeadInclusive.size());

        NavigableMap<Integer, String> subHeadAtBound = sub.headMap(7, true);
        check(subHeadAtBound.size() == 5, "sub.headMap(7,true) size expected 5 but was " + subHeadAtBound.size());

        NavigableMap<Integer, String> subHeadExclusive = sub.headMap(7, false);
        check(subHeadExclusive.size() == 4, "sub.headMap(7,false) size expected 4 but was " + subHeadExclusive.size());

        expectIllegalArgument(() -> sub.headMap(8, true), "sub.headMap(8,true) must reject key above range");
        expectIllegalArgument(() -> sub.headMap(2, true), "sub.headMap(2,true) must reject key below range");
    }
}
