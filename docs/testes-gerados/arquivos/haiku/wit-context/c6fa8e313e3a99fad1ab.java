/*
 * @test
 * @summary Verify public IntStream.forEachOrdered behavior: encounter order and IllegalStateException when the stream is reused
 * @run main IntPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedPreservesEncounterOrder();
        testForEachOrderedOnConsumedStreamThrows();
    }

    static void testForEachOrderedPreservesEncounterOrder() {
        List<Integer> out = new ArrayList<>();
        IntStream.range(0, 5).forEachOrdered(v -> out.add(v));
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);
        if (!expected.equals(out)) {
            throw new RuntimeException("Unexpected order: " + out);
        }
    }

    static void testForEachOrderedOnConsumedStreamThrows() {
        IntStream s = IntStream.of(1, 2, 3);
        s.forEachOrdered(v -> { });
        boolean thrown = false;
        try {
            s.forEachOrdered(v -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on reused stream");
        }
    }
}
