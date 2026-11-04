/*
 * @test
 * @summary Verifies that Sink.ChainedLong's constructor rejects a null downstream and accepts a non-null one
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;

public class SinkWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Sink<String> downstream = new Sink<String>() {
            @Override
            public void accept(String s) {
            }
        };

        Sink.ChainedLong<String> chained =
            new Sink.ChainedLong<String>(downstream) {
                @Override
                public void accept(long value) {
                }
            };
        check(chained != null, "ChainedLong with non-null downstream should be created");

        boolean npeThrown = false;
        try {
            new Sink.ChainedLong<String>(null) {
                @Override
                public void accept(long value) {
                }
            };
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "ChainedLong with null downstream should throw NullPointerException");
    }
}
