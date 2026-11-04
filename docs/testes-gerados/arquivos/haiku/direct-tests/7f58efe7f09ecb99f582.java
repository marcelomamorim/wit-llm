/*
 * @test
 * @summary Tests Stream.peek behavior through the public API backed by ReferencePipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testPeekSeesAllElementsInOrder();
        testPeekIsLazy();
        testPeekPassesElementsThrough();
        testPeekBeforeFilterSeesAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.of(1, 2, 3).peek(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "peek(null) must throw NullPointerException");
    }

    static void testPeekSeesAllElementsInOrder() {
        List<Integer> seen = new ArrayList<>();
        Stream.of(1, 2, 3).peek(seen::add).forEach(x -> { });
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "peek must observe all elements in encounter order");
    }

    static void testPeekIsLazy() {
        List<Integer> log = new ArrayList<>();
        Stream<Integer> s = Stream.of(1, 2).peek(log::add);
        check(log.isEmpty(), "peek must not run before a terminal operation");
        s.forEach(x -> { });
        check(log.size() == 2, "peek must run once per element on terminal operation");
    }

    static void testPeekPassesElementsThrough() {
        List<Integer> out = Stream.of(4, 5)
                .peek(x -> { })
                .collect(Collectors.toList());
        check(out.equals(Arrays.asList(4, 5)),
              "peek must not alter the elements passed downstream");
    }

    static void testPeekBeforeFilterSeesAll() {
        List<Integer> seen = new ArrayList<>();
        Stream.of(1, 2, 3)
                .peek(seen::add)
                .filter(x -> x > 1)
                .forEach(x -> { });
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "peek placed before filter must observe all upstream elements");
    }
}
