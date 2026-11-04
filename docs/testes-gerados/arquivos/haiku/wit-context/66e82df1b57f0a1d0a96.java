/*
 * @test
 * @summary Witup generated tests for IntPipeline.forEach through the public IntStream API
 * @run main IntPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachVisitsElementsInEncounterOrder();
        testForEachOnEmptyStreamDoesNotInvokeAction();
        testReusedStreamThrowsIllegalStateException();
    }

    static void testForEachVisitsElementsInEncounterOrder() {
        List<Integer> out = new ArrayList<>();
        IntStream.of(3, 1, 2).forEach(out::add);
        List<Integer> expected = Arrays.asList(3, 1, 2);
        if (!expected.equals(out)) {
            throw new RuntimeException("Expected " + expected + " but was " + out);
        }
    }

    static void testForEachOnEmptyStreamDoesNotInvokeAction() {
        final int[] count = {0};
        IntStream.empty().forEach(i -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("Action invoked " + count[0] + " times for empty stream");
        }
    }

    static void testReusedStreamThrowsIllegalStateException() {
        IntStream s = IntStream.of(1, 2);
        s.forEach(i -> { });
        boolean thrown = false;
        try {
            s.forEach(i -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on reused stream");
        }
    }
}
