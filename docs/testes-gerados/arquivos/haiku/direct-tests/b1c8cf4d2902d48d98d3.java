/*
 * @test
 * @summary Verifies that LongPipeline's peek sink (accept(long)) invokes the action before forwarding each element downstream
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testActionThenDownstreamOrder();
        testDownstreamReceivesAllElements();
        testEmptySourceNoActionCalls();
    }

    static void testActionThenDownstreamOrder() {
        List<String> events = new ArrayList<>();
        LongStream.of(1L, 2L, 3L)
                .peek(v -> events.add("peek:" + v))
                .forEach(v -> events.add("down:" + v));
        List<String> expected = new ArrayList<>();
        expected.add("peek:1");
        expected.add("down:1");
        expected.add("peek:2");
        expected.add("down:2");
        expected.add("peek:3");
        expected.add("down:3");
        if (!expected.equals(events)) {
            throw new AssertionError("Unexpected order: " + events);
        }
    }

    static void testDownstreamReceivesAllElements() {
        long[] count = new long[1];
        long sum = LongStream.of(10L, 20L, 30L)
                .peek(v -> count[0]++)
                .sum();
        if (count[0] != 3L) {
            throw new AssertionError("Action called " + count[0] + " times, expected 3");
        }
        if (sum != 60L) {
            throw new AssertionError("Sum was " + sum + ", expected 60");
        }
    }

    static void testEmptySourceNoActionCalls() {
        long[] count = new long[1];
        long result = LongStream.empty()
                .peek(v -> count[0]++)
                .count();
        if (count[0] != 0L) {
            throw new AssertionError("Action called on empty source");
        }
        if (result != 0L) {
            throw new AssertionError("Count was " + result + ", expected 0");
        }
    }
}
