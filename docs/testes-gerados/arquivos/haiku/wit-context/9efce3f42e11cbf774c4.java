/*
 * @test
 * @summary Witup-generated checks for ReferencePipeline.max observable through the public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        Optional<Integer> r = Stream.of(3, 1, 2).max(Comparator.naturalOrder());
        check(r.isPresent() && r.get() == 3, "max of 3,1,2 should be 3");

        Optional<String> byLength = Stream.of("aa", "b", "ccc")
                .max(Comparator.comparingInt(String::length));
        check(byLength.isPresent() && byLength.get().equals("ccc"),
                "max by length should be ccc");

        Optional<Integer> empty = Stream.<Integer>empty().max(Comparator.naturalOrder());
        check(!empty.isPresent(), "max of empty stream should be empty Optional");

        Stream<Integer> consumed = Stream.of(1, 2);
        consumed.max(Comparator.naturalOrder());
        boolean thrown = false;
        try {
            consumed.max(Comparator.naturalOrder());
        } catch (IllegalStateException ex) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream in max should throw IllegalStateException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
