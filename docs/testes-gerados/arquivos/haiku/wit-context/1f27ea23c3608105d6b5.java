/*
 * @test
 * @summary Verify observable behavior of ReferencePipeline.anyMatch, including reuse of a consumed stream
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        if (!Stream.of(1, 2, 3).anyMatch(x -> x == 2)) {
            throw new AssertionError("anyMatch should return true when an element matches");
        }
        if (Stream.of(1, 2, 3).anyMatch(x -> x > 5)) {
            throw new AssertionError("anyMatch should return false when no element matches");
        }
        if (Stream.empty().anyMatch(x -> true)) {
            throw new AssertionError("anyMatch on an empty stream should return false");
        }

        Stream<Integer> consumed = Stream.of(1, 2);
        consumed.anyMatch(x -> true);
        boolean thrown = false;
        try {
            consumed.anyMatch(x -> true);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("anyMatch on an already consumed stream should throw IllegalStateException");
        }

        thrown = false;
        try {
            Stream.of(1).anyMatch(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("anyMatch with a null predicate should throw NullPointerException");
        }
    }
}
