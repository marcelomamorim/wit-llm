/*
 * @test
 * @summary Verifies observable behavior of ReferencePipeline.forEach through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEachPreservesEncounterOrder();
        testForEachOnEmptyStreamInvokesNothing();
        testParallelForEachVisitsEveryElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialForEachPreservesEncounterOrder() {
        List<String> out = new ArrayList<>();
        Stream.of("a", "b", "c").forEach(out::add);
        check(out.equals(Arrays.asList("a", "b", "c")),
              "sequential forEach must visit elements in encounter order, got " + out);
    }

    static void testForEachOnEmptyStreamInvokesNothing() {
        int[] calls = {0};
        Stream.<String>empty().forEach(s -> calls[0]++);
        check(calls[0] == 0, "forEach on empty stream must not invoke the action");
    }

    static void testParallelForEachVisitsEveryElement() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbers.add(i);
        }
        AtomicInteger sum = new AtomicInteger();
        numbers.parallelStream().forEach(x -> sum.addAndGet(x));
        check(sum.get() == 5050,
              "parallel forEach must visit every element exactly once, sum was " + sum.get());
    }
}
