/*
 * @test
 * @summary Verify forEachOrdered order and reuse rejection of a LongStream pipeline
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedPreservesEncounterOrder();
        testReuseAfterTerminalOperationThrowsIllegalStateException();
    }

    private static void testForEachOrderedPreservesEncounterOrder() {
        StringBuilder sb = new StringBuilder();
        LongStream.of(3L, 1L, 2L).forEachOrdered(v -> sb.append(v).append(','));
        String expected = "3,1,2,";
        if (!expected.equals(sb.toString())) {
            throw new RuntimeException("Unexpected order: " + sb);
        }
    }

    private static void testReuseAfterTerminalOperationThrowsIllegalStateException() {
        LongStream s = LongStream.of(1L, 2L);
        s.forEachOrdered(v -> { });
        try {
            s.forEachOrdered(v -> { });
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException on reuse of consumed stream");
    }
}
