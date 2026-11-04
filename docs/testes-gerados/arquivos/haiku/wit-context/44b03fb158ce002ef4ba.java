/*
 * @test
 * @summary Verifies that Sink.ChainedInt rejects a null downstream sink and accepts a non-null one
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;

public class SinkWitupTest {

    public static void main(String[] args) {
        testNullDownstreamThrowsNpe();
        testNonNullDownstreamIsAccepted();
    }

    static void testNullDownstreamThrowsNpe() {
        boolean thrown = false;
        try {
            new Sink.ChainedInt<String>(null) {
                @Override
                public void accept(int value) {
                }
            };
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null downstream");
        }
    }

    static void testNonNullDownstreamIsAccepted() {
        Sink<Integer> downstream = new Sink.OfInt() {
            @Override
            public void accept(int value) {
            }
        };
        Sink.ChainedInt<Integer> chained = new Sink.ChainedInt<Integer>(downstream) {
            @Override
            public void accept(int value) {
            }
        };
        if (chained == null) {
            throw new RuntimeException("Constructed ChainedInt must not be null");
        }
        if (chained.cancellationRequested()) {
            throw new RuntimeException("cancellationRequested should be false for a fresh chain with a non-cancelling downstream");
        }
    }
}
