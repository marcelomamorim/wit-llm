/*
 * @test
 * @summary Test SortedOps.OfDouble.opWrapSink behavior including null sink check and flag handling
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;
import java.util.Objects;

public class SortedOpsWitupTest {

    // Minimal stub for Sink<Double> to test opWrapSink
    static class DummySink implements Sink<Double> {
        public void begin(long size) { }
        public void accept(Double t) { }
        public void end() { }
        public boolean cancellationRequested() { return false; }
    }

    public static void main(String[] args) {
        testNullSinkThrows();
        testSortedFlagReturnsSameSink();
        testSizedFlagReturnsSizedDoubleSortingSink();
        testOtherFlagsReturnDoubleSortingSink();
    }

    private static void testNullSinkThrows() {
        try {
            SortedOps.OfDouble ofDouble = new SortedOps.OfDouble(null) {};
            ofDouble.opWrapSink(0, null);
            throw new AssertionError("Expected NullPointerException for null sink");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSortedFlagReturnsSameSink() {
        DummySink sink = new DummySink();
        SortedOps.OfDouble ofDouble = new SortedOps.OfDouble(null) {};
        Sink<Double> result = ofDouble.opWrapSink(StreamOpFlag.SORTED.getMask(), sink);
        if (result != sink) {
            throw new AssertionError("Expected same sink instance when SORTED flag is set");
        }
    }

    private static void testSizedFlagReturnsSizedDoubleSortingSink() {
        DummySink sink = new DummySink();
        SortedOps.OfDouble ofDouble = new SortedOps.OfDouble(null) {};
        Sink<Double> result = ofDouble.opWrapSink(StreamOpFlag.SIZED.getMask(), sink);
        if (result == sink) {
            throw new AssertionError("Expected different sink instance when SIZED flag is set");
        }
        // We cannot assert exact class due to lack of visibility, but result must not be null
        if (result == null) {
            throw new AssertionError("Returned sink must not be null");
        }
    }

    private static void testOtherFlagsReturnDoubleSortingSink() {
        DummySink sink = new DummySink();
        SortedOps.OfDouble ofDouble = new SortedOps.OfDouble(null) {};
        int flags = 0; // no SORTED or SIZED
        Sink<Double> result = ofDouble.opWrapSink(flags, sink);
        if (result == sink) {
            throw new AssertionError("Expected different sink instance when no SORTED or SIZED flag");
        }
        if (result == null) {
            throw new AssertionError("Returned sink must not be null");
        }
    }
}
