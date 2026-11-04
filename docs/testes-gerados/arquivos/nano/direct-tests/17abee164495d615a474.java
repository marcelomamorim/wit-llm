/* @test    @summary Test for SortedOps.opWrapSink method behavior in different flag scenarios    @run main SortedOpsWitupTest */

import java.util.Objects;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testOpWrapSinkWithKnownSortFlag();
        testOpWrapSinkWithKnownSizedFlag();
        testOpWrapSinkWithUnknownFlags();
    }

    static void testOpWrapSinkWithKnownSortFlag() {
        Sink<Double> mockSink = new MockSink<>();
        int flags = StreamOpFlag.SORTED.toFlags();
        Sink<Double> result = java.util.stream.SortedOps.opWrapSink(flags, mockSink);
        assert result == mockSink : "Expected sink to be returned when SORTED flag is known";
    }

    static void testOpWrapSinkWithKnownSizedFlag() {
        Sink<Double> mockSink = new MockSink<>();
        int flags = StreamOpFlag.SIZED.toFlags();
        // Clear SORTED flag to simulate only SIZED being known
        flags &= ~StreamOpFlag.SORTED.toFlags();
        Sink<Double> result = java.util.stream.SortedOps.opWrapSink(flags, mockSink);
        assert result instanceof SizedDoubleSortingSink : "Expected SizedDoubleSortingSink when SIZED is known";
    }

    static void testOpWrapSinkWithUnknownFlags() {
        Sink<Double> mockSink = new MockSink<>();
        int flags = 0; // No known flags
        Sink<Double> result = java.util.stream.SortedOps.opWrapSink(flags, mockSink);
        assert result instanceof DoubleSortingSink : "Expected DoubleSortingSink when no flags are known";
    }

    static class MockSink<T> implements Sink<T> {
        @Override
        public void accept(T t) {}
        @Override
        public boolean requiresMore() { return false; }
        @Override
        public void begin(long size) {}
        @Override
        public void end() {}
        @Override
        public void cancellationRequested() {}
        @Override
        public void accept(T t) {}
        @Override
        public void close() {}
    }
}
