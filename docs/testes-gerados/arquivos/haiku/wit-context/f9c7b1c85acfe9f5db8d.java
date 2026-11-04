/*
 * @test
 * @summary Verify IntPipeline.allMatch on empty, matching, non-matching, reused and null-predicate cases
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        if (!IntStream.empty().allMatch(x -> false)) {
            throw new AssertionError("allMatch on empty stream must return true");
        }
        if (!IntStream.of(1, 2, 3).allMatch(x -> x > 0)) {
            throw new AssertionError("allMatch should return true when all elements match");
        }
        if (IntStream.of(1, 2, 3).allMatch(x -> x < 2)) {
            throw new AssertionError("allMatch should return false when one element does not match");
        }
        if (IntStream.of(1, 2, 3).allMatch(x -> false)) {
            throw new AssertionError("allMatch should return false when no element matches");
        }

        IntStream reused = IntStream.of(1, 2, 3);
        reused.allMatch(x -> true);
        boolean reuseThrew = false;
        try {
            reused.allMatch(x -> true);
        } catch (IllegalStateException e) {
            reuseThrew = true;
        }
        if (!reuseThrew) {
            throw new AssertionError("reusing a consumed stream must throw IllegalStateException");
        }

        boolean npeThrown = false;
        try {
            IntStream.of(1).allMatch(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("null predicate must throw NullPointerException");
        }
    }
}
