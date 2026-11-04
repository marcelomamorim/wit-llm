/*
 * @test
 * @summary Test tryAdvance method of java.util.Spliterators for default behavior
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceReturnsFalseWhenConsumerProvided();
    }

    public static void testTryAdvanceReturnsFalseWhenConsumerProvided() {
        boolean result = Spliterators.tryAdvance(new Consumer<Object>() {
            @Override
            public void accept(Object t) {
                // no operation
            }
        });
        if (result != false) {
            throw new AssertionError("Expected tryAdvance to return false");
        }
    }
}
