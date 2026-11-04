/*
 * @test
 * @summary IntStream.peek applies its action to each element before downstream and rejects a null action
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testPeekVisitsAllElements();
        testPeekPreservesValues();
        testPeekIsLazyWithoutTerminalOperation();
        testPeekOrderingPerElement();
        testNullActionThrowsNPE();
    }

    static void testPeekVisitsAllElements() {
        List<Integer> seen = new ArrayList<>();
        long count = IntStream.of(4, 5, 6).peek(x -> seen.add(x)).count();
        check(count == 3, "count should be 3 but was " + count);
        check(seen.size() == 3, "peek should visit 3 elements but visited " + seen.size());
        check(seen.get(0) == 4 && seen.get(1) == 5 && seen.get(2) == 6,
              "peek visited wrong elements: " + seen);
    }

    static void testPeekPreservesValues() {
        int[] result = IntStream.of(1, 2, 3).peek(x -> { }).map(x -> x * 2).toArray();
        check(result.length == 3, "length should be 3");
        check(result[0] == 2 && result[1] == 4 && result[2] == 6,
              "values changed by peek: " + result[0] + "," + result[1] + "," + result[2]);
    }

    static void testPeekIsLazyWithoutTerminalOperation() {
        List<Integer> seen = new ArrayList<>();
        IntStream stream = IntStream.of(1, 2, 3).peek(x -> seen.add(x));
        check(seen.isEmpty(), "peek action must not run before a terminal operation");
        stream.sum();
        check(seen.size() == 3, "peek action should run once terminal operation executes");
    }

    static void testPeekOrderingPerElement() {
        List<Integer> events = new ArrayList<>();
        IntStream.of(1, 2, 3)
                 .peek(x -> events.add(x * 10))
                 .forEach(x -> events.add(x * 10 + 1));
        Integer[] expected = {10, 11, 20, 21, 30, 31};
        check(events.size() == expected.length, "unexpected event count: " + events);
        for (int i = 0; i < expected.length; i++) {
            check(events.get(i).equals(expected[i]), "event " + i + " was " + events.get(i));
        }
    }

    static void testNullActionThrowsNPE() {
        boolean thrown = false;
        try {
            IntStream.of(1).peek(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "peek(null) should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
