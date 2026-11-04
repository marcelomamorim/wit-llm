/*
 * @test
 * @summary Verify IntStream.findAny returns expected results and rejects reuse of a consumed pipeline
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        OptionalInt r = IntStream.of(5).findAny();
        check(r.isPresent() && r.getAsInt() == 5, "single element");
        check(!IntStream.empty().findAny().isPresent(), "empty stream");
        int v = IntStream.of(1, 2, 3).filter(x -> x > 1).findAny().getAsInt();
        check(v == 2 || v == 3, "filtered element");
        IntStream s = IntStream.of(1, 2);
        s.findAny();
        boolean threw = false;
        try {
            s.findAny();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "reuse of consumed stream");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
