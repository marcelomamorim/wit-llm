/*
 * @test
 * @summary Verify that Sink.ChainedLong rejects a null downstream sink with NullPointerException
 * @run main SinkWitupTest
 */
import java.util.stream.Sink;

public class SinkWitupTest {
    public static void main(String[] args) {
        Sink<Long> downstream = new Sink<Long>() {
            @Override
            public void accept(Long value) { }
        };

        Sink.ChainedLong<Long> chained = new Sink.ChainedLong<Long>(downstream) {
            @Override
            public void accept(long value) { }
        };
        if (chained == null) {
            throw new AssertionError("ChainedLong with non-null downstream must be created");
        }

        try {
            new Sink.ChainedLong<Long>(null) {
                @Override
                public void accept(long value) { }
            };
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException expected) {
            // expected behavior: Objects.requireNonNull(downstream)
        }
    }
}
