/*
 * @test
 * @summary forEach on ReferencePipeline via public Stream API: ordering, reuse after consumption and null action
 * @run main ReferencePipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachVisitsElementsInOrder();
        testReuseAfterForEachThrowsIllegalStateException();
        testNullActionThrowsNullPointerException();
    }

    static void testForEachVisitsElementsInOrder() {
        List<String> seen = new ArrayList<>();
        Stream.of("a", "b", "c").forEach(seen::add);
        check(seen.size() == 3, "expected 3 elements but got " + seen.size());
        check(seen.get(0).equals("a"), "element 0 mismatch: " + seen.get(0));
        check(seen.get(1).equals("b"), "element 1 mismatch: " + seen.get(1));
        check(seen.get(2).equals("c"), "element 2 mismatch: " + seen.get(2));
    }

    static void testReuseAfterForEachThrowsIllegalStateException() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        s.forEach(x -> { });
        boolean thrown = false;
        try {
            s.forEach(x -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException on reuse of consumed stream");
    }

    static void testNullActionThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Stream.of("x").forEach((Consumer<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
