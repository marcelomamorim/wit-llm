/*
 * @test
 * @summary Verifies that a sized stream whose spliterator delivers fewer elements than it reports fails when the fixed-size node builder is ended
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class NodesWitupTest {

    public static void main(String[] args) {
        List<String> items = Arrays.asList("a", "b", "c");

        Object[] ok = StreamSupport.stream(
                Spliterators.spliterator(items.iterator(), 3,
                        Spliterator.SIZED | Spliterator.ORDERED), false).toArray();
        if (ok.length != 3) {
            throw new AssertionError("Expected 3 elements but got " + ok.length);
        }

        boolean thrown = false;
        String message = null;
        try {
            StreamSupport.stream(
                    Spliterators.spliterator(items.iterator(), 5,
                            Spliterator.SIZED | Spliterator.ORDERED), false).toArray();
        } catch (IllegalStateException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when size is under-delivered");
        }
        if (message == null || !message.contains("End size 3 is less than fixed size 5")) {
            throw new AssertionError("Unexpected message: " + message);
        }
    }
}
