/*
 * @test
 * @summary Test for SortedOps.opWrapSink handling null sink
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Sink;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testOpWrapSinkNullSink();
    }

    public static void testOpWrapSinkNullSink() {
        int flags = 0; // valor arbitrário para teste
        Sink<Long> sink = null;
        boolean threwNullPointer = false;
        try {
            SortedOps.opWrapSink(flags, sink);
        } catch (NullPointerException e) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException when sink is null");
        }
    }
}
