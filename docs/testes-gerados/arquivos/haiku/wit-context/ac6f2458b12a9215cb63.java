/*
 * @test
 * @summary Tests IntStream forEachOrdered for sequential and parallel pipelines and reuse detection
 * @run main IntPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testSequentialOrder();
        testParallelOrder();
        testReuseThrowsIllegalStateException();
    }

    static void testSequentialOrder() {
        List<Integer> out = new ArrayList<>();
        IntStream.of(3, 1, 2).map(x -> x * 10).forEachOrdered(out::add);
        check(out.size() == 3 && out.get(0) == 30 && out.get(1) == 10 && out.get(2) == 20,
              "sequential order: " + out);
    }

    static void testParallelOrder() {
        List<Integer> out = new ArrayList<>();
        IntStream.range(0, 5000).parallel().map(x -> x + 1).forEachOrdered(out::add);
        check(out.size() == 5000, "parallel size: " + out.size());
        for (int i = 0; i < out.size(); i++) {
            check(out.get(i) == i + 1, "parallel order at " + i + ": " + out.get(i));
        }
    }

    static void testReuseThrowsIllegalStateException() {
        IntStream s = IntStream.of(1, 2, 3);
        s.forEachOrdered(x -> { });
        boolean thrown = false;
        try {
            s.forEachOrdered(x -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reused stream did not throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
