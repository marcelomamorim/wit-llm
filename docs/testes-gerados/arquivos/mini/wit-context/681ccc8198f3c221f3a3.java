/*
 * @test
 * @summary Test java.util.stream.SortedOps.OfLong.opWrapSink method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.Objects;
import java.util.stream.SortedOps;
import java.util.stream.SortedOps.OfLong;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;

public class SortedOpsWitupTest {

    // Minimal Sink<Long> implementation for testing
    static class TestSink implements Sink<Long> {
        @Override
        public void accept(Long t) { }
        @Override
        public void begin(long size) { }
        @Override
        public void end() { }
        @Override
        public boolean cancellationRequested() { return false; }
    }

    public static void main(String[] args) {
        testNullSinkThrowsNPE();
        testSortedFlagReturnsSameSink();
        testSizedFlagReturnsSizedLongSortingSink();
        testOtherFlagsReturnLongSortingSink();
    }

    private static void testNullSinkThrowsNPE() {
        OfLong ofLong = new OfLong(null);
        try {
            ofLong.opWrapSink(0, null);
            throw new AssertionError("Expected NullPointerException for null sink");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSortedFlagReturnsSameSink() {
        OfLong ofLong = new OfLong(null);
        Sink<Long> sink = new TestSink();
        int flags = StreamOpFlag.SORTED.getMask();
        Sink<Long> result = ofLong.opWrapSink(flags, sink);
        if (result != sink) {
            throw new AssertionError("Expected same sink instance when SORTED flag is known");
        }
    }

    private static void testSizedFlagReturnsSizedLongSortingSink() {
        OfLong ofLong = new OfLong(null);
        Sink<Long> sink = new TestSink();
        int flags = StreamOpFlag.SIZED.getMask();
        Sink<Long> result = ofLong.opWrapSink(flags, sink);
        if (result == sink) {
            throw new AssertionError("Expected different sink instance when SIZED flag is known");
        }
        // We cannot assert exact class due to access restrictions, but result should not be sink
    }

    private static void testOtherFlagsReturnLongSortingSink() {
        OfLong ofLong = new OfLong(null);
        Sink<Long> sink = new TestSink();
        int flags = 0; // no flags known
        Sink<Long> result = ofLong.opWrapSink(flags, sink);
        if (result == sink) {
            throw new AssertionError("Expected different sink instance when no SORTED or SIZED flag");
        }
    }
}
