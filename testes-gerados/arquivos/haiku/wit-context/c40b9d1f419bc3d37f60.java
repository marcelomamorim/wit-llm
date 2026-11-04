/*
 * @test
 * @summary Witup-derived checks for the forEachRemaining path of the lines spliterator reached through String.lines()
 * @run main StringUTF16WitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        Spliterator<String> sp = "a\nb\nc".lines().spliterator();
        List<String> out = new ArrayList<>();
        sp.forEachRemaining(out::add);
        if (!out.equals(List.of("a", "b", "c"))) {
            throw new AssertionError("unexpected lines: " + out);
        }

        List<String> empty = new ArrayList<>();
        "".lines().spliterator().forEachRemaining(empty::add);
        if (!empty.isEmpty()) {
            throw new AssertionError("expected no lines but got: " + empty);
        }

        try {
            "a\nb".lines().spliterator().forEachRemaining(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }
}
