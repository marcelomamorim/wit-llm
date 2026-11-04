/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.tryAdvance method behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithSingleElement();
    }

    private static void testTryAdvanceWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.tryAdvance(v -> {
            System.out.println("This should not be called");
        });
        if (!result) {
            System.out.println("PASS: tryAdvance returns false on empty map");
        } else {
            System.out.println("FAIL: tryAdvance should return false on empty map");
        }
    }

    private static void testTryAdvanceWithSingleElement() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        final boolean[] called = {false};
        boolean result = map.tryAdvance(v -> {
            if ("one".equals(v)) {
                called[0] = true;
            } else {
                System.out.println("FAIL: Unexpected value: " + v);
            }
        });
        if (result && called[0]) {
            System.out.println("PASS: tryAdvance successfully processed single element");
        } else {
            System.out.println("FAIL: tryAdvance did not process single element as expected");
        }
    }
}
