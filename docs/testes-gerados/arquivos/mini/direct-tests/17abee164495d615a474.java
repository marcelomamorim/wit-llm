/*
 * @test
 * @summary Test SortedOps.opWrapSink behavior with different StreamOpFlag flags
 * @run main SortedOpsWitupTest
 */

import java.util.Objects;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;
import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal Sink<Double> implementation for testing
    static class TestSink implements Sink<Double> {
        boolean accepted = false;

        @Override
        public void accept(Double t) {
            accepted = true;
        }

        @Override
        public void begin(long size) {}

        @Override
        public void end() {}

        @Override
        public boolean cancellationRequested() { return false; }
    }

    public static void main(String[] args) {
        SortedOps.OfDouble op = new SortedOps.OfDouble(null);

        TestSink sink = new TestSink();

        // Test 1: flags with SORTED bit set - should return the same sink instance
        int flagsSorted = StreamOpFlag.SORTED.getMask();
        Sink<Double> result1 = op.opWrapSink(flagsSorted, sink);
        if (result1 != sink) {
            throw new AssertionError("Expected same sink instance when SORTED flag is set");
        }

        // Test 2: flags with SIZED bit set but not SORTED - should return SizedDoubleSortingSink
        int flagsSized = StreamOpFlag.SIZED.getMask();
        Sink<Double> result2 = op.opWrapSink(flagsSized, sink);
        if (result2 == sink) {
            throw new AssertionError("Expected new SizedDoubleSortingSink instance when SIZED flag is set and SORTED not set");
        }

        // Test 3: flags with neither SORTED nor SIZED - should return DoubleSortingSink
        int flagsNone = 0;
        Sink<Double> result3 = op.opWrapSink(flagsNone, sink);
        if (result3 == sink) {
            throw new AssertionError("Expected new DoubleSortingSink instance when neither SORTED nor SIZED flags are set");
        }

        // Test 4: null sink argument should throw NullPointerException
        boolean npeThrown = false;
        try {
            op.opWrapSink(flagsNone, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when sink is null");
        }
    }
}
