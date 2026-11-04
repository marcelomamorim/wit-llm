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
        Sink<Long> mockSink = new MockSink<>();
        int flags = StreamOpFlag.SORTED.toFlag();
        Sink<Long> result = SortedOps.opWrapSink(flags, mockSink);
        assert result == mockSink : "Expected sink to be returned when SORTED flag is known";
    }

    static void testOpWrapSinkWithKnownSizedFlag() {
        Sink<Long> mockSink = new MockSink<>();
        int flags = StreamOpFlag.SIZED.toFlag();
        Sink<Long> result = SortedOps.opWrapSink(flags, mockSink);
        assert result != null : "Result should not be null";
        assert result != mockSink : "Expected a new SizedLongSortingSink when SIZED flag is set";
        assert result.getClass().getSimpleName().contains("SizedLongSortingSink") : "Expected a SizedLongSortingSink";
    }

    static void testOpWrapSinkWithUnknownFlags() {
        Sink<Long> mockSink = new MockSink<>();
        int flags = 0; // no known flags
        Sink<Long> result = SortedOps.opWrapSink(flags, mockSink);
        assert result != null : "Result should not be null";
        assert result.getClass().getSimpleName().contains("LongSortingSink") : "Expected a LongSortingSink";
    }

    static class MockSink<T> implements Sink<T> {
        @Override
        public void accept(T t) {}
        @Override
        public void begin(long size) {}
        @Override
        public boolean cancellationSupported() { return false; }
        @Override
        public boolean isParallelCapable() { return false; }
        @Override
        public void end() {}
        @Override
        public void flush() {}
        @Override
        public String toString() { return "MockSink"; }
    }
}
