/*
 * @test
 * @summary Test SortedOps.opWrapSink behavior with different StreamOpFlag states
 * @run main SortedOpsWitupTest
 */

import java.util.Objects;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;
import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal Sink<Long> implementation for testing
    static class TestSink implements Sink<Long> {
        boolean accepted = false;

        @Override
        public void begin(long size) { }

        @Override
        public void accept(Long t) {
            accepted = true;
        }

        @Override
        public void end() { }

        @Override
        public boolean cancellationRequested() {
            return false;
        }
    }

    public static void main(String[] args) {
        // Test 1: When StreamOpFlag.SORTED is known, opWrapSink returns the same sink
        TestSink sink1 = new TestSink();
        int flagsSorted = StreamOpFlag.SORTED.getMask();
        Sink<Long> result1 = new SortedOps.OfLong(null).opWrapSink(flagsSorted, sink1);
        if (result1 != sink1) {
            throw new AssertionError("Expected same sink instance when SORTED flag is known");
        }

        // Test 2: When StreamOpFlag.SIZED is known but SORTED is not, returns SizedLongSortingSink
        TestSink sink2 = new TestSink();
        int flagsSized = StreamOpFlag.SIZED.getMask();
        Sink<Long> result2 = new SortedOps.OfLong(null).opWrapSink(flagsSized, sink2);
        if (result2 == sink2) {
            throw new AssertionError("Expected new SizedLongSortingSink instance when SIZED flag is known and SORTED is not");
        }

        // Test 3: When neither SORTED nor SIZED is known, returns LongSortingSink
        TestSink sink3 = new TestSink();
        int flagsNone = 0;
        Sink<Long> result3 = new SortedOps.OfLong(null).opWrapSink(flagsNone, sink3);
        if (result3 == sink3) {
            throw new AssertionError("Expected new LongSortingSink instance when no flags are known");
        }

        // Test 4: Null sink argument throws NullPointerException
        try {
            new SortedOps.OfLong(null).opWrapSink(flagsNone, null);
            throw new AssertionError("Expected NullPointerException when sink is null");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
