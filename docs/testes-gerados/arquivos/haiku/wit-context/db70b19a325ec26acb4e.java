/*
 * @test
 * @summary Witup-derived checks for ReferencePipeline.findAny through the public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        Optional<Integer> present = Stream.of(1, 2, 3).findAny();
        if (!present.isPresent()) {
            throw new RuntimeException("findAny on non-empty stream returned empty");
        }
        int v = present.get();
        if (v != 1 && v != 2 && v != 3) {
            throw new RuntimeException("findAny returned element not in source");
        }

        if (Stream.<Integer>empty().findAny().isPresent()) {
            throw new RuntimeException("findAny on empty stream should be empty");
        }

        Stream<Integer> consumed = Stream.of(1, 2);
        consumed.findAny();
        boolean threw = false;
        try {
            consumed.findAny();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("reusing consumed stream did not throw IllegalStateException");
        }

        threw = false;
        try {
            Stream.of((String) null).findAny();
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("findAny with null element did not throw NullPointerException");
        }
    }
}
