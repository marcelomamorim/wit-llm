/*
 * @test
 * @summary Verify null action handling of forEachRemaining on the spliterator returned by Stream.iterate(seed, hasNext, next)
 * @run main StreamWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamWitupTest {
    public static void main(String[] args) {
        Spliterator<Integer> fresh = Stream.iterate(1, x -> x < 5, x -> x + 1).spliterator();
        expectNpe(fresh);

        List<Integer> out = new ArrayList<>();
        Spliterator<Integer> s = Stream.iterate(1, x -> x < 5, x -> x + 1).spliterator();
        s.forEachRemaining(out::add);
        if (!out.equals(Arrays.asList(1, 2, 3, 4))) {
            throw new RuntimeException("unexpected elements: " + out);
        }
        expectNpe(s);
    }

    static void expectNpe(Spliterator<Integer> sp) {
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }
}
