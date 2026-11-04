/*
 * @test
 * @summary Tests Stream.toArray() result and IllegalStateException on a consumed pipeline
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        Object[] arr = Stream.of("a", "b").toArray();
        if (arr.length != 2 || !"a".equals(arr[0]) || !"b".equals(arr[1])) {
            throw new RuntimeException("unexpected toArray result");
        }

        Object[] emptyArr = Stream.empty().toArray();
        if (emptyArr.length != 0) {
            throw new RuntimeException("expected empty array");
        }

        Stream<String> consumed = Stream.of("x");
        consumed.toArray();
        boolean threw = false;
        try {
            consumed.toArray();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IllegalStateException on reused stream");
        }
    }
}
