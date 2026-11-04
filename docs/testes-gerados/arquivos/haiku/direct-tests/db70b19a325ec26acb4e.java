/*
 * @test
 * @summary Tests observable behavior of ReferencePipeline.findAny through public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        Optional<Integer> found = Stream.of(1, 2, 3).filter(x -> x == 2).findAny();
        check(found.isPresent(), "findAny should return a value for a non-empty filtered stream");
        check(found.get() == 2, "findAny should return the only matching element");

        check(!Stream.<Integer>empty().findAny().isPresent(),
              "findAny on empty stream should return empty Optional");

        check(Stream.of("x").findAny().get().equals("x"),
              "findAny on single-element stream should return that element");

        Stream<String> reused = Stream.of("a");
        reused.findAny();
        try {
            reused.findAny();
            throw new RuntimeException("reusing a consumed stream should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }

        try {
            Stream.<String>of((String) null).findAny();
            throw new RuntimeException("findAny selecting null should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
