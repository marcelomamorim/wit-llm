/*
 * @test
 * @summary Verifies that getComparator on a concatenated spliterator throws IllegalStateException before any split
 * @run main StreamsWitupTest
 */

import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        Spliterator<String> sp = Stream.concat(Stream.of("a"), Stream.of("b")).spliterator();
        boolean thrown = false;
        try {
            sp.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException before split");
        }
    }
}
