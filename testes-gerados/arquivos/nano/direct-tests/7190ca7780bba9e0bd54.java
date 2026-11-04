/*
 * @test
 * @summary Test the forEach method of LongPipeline to ensure it correctly applies the action to each element.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachAppliesAction();
        testForEachWithEmptyStream();
        testForEachNullAction();
    }

    static void testForEachAppliesAction() {
        long[] data = {1L, 2L, 3L};
        StringBuilder sb = new StringBuilder();
        LongStream stream = LongStream.of(data);
        stream.forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("1,2,3,")) {
            throw new AssertionError("Expected '1,2,3,', but got: " + result);
        }
    }

    static void testForEachWithEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        final boolean[] called = {false};
        emptyStream.forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new AssertionError("forEach on empty stream should not call accept");
        }
    }

    static void testForEachNullAction() {
        try {
            LongStream stream = LongStream.of(1L, 2L);
            stream.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
