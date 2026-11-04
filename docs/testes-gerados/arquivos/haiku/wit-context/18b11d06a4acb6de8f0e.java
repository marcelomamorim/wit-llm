/*
 * @test
 * @summary Tests public DoubleStream.anyMatch behavior, including null predicate and reused stream cases
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMatchFound();
        testNoMatch();
        testEmptyStream();
        testNullPredicate();
        testReusedStreamThrows();
    }

    static void testMatchFound() {
        if (!DoubleStream.of(1.0, 2.0, 3.0).anyMatch(d -> d > 2.5)) {
            fail("expected true when an element matches");
        }
    }

    static void testNoMatch() {
        if (DoubleStream.of(1.0, 2.0, 3.0).anyMatch(d -> d > 10.0)) {
            fail("expected false when no element matches");
        }
    }

    static void testEmptyStream() {
        if (DoubleStream.empty().anyMatch(d -> true)) {
            fail("expected false for empty stream");
        }
    }

    static void testNullPredicate() {
        try {
            DoubleStream.of(1.0).anyMatch(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("expected NullPointerException for null predicate");
    }

    static void testReusedStreamThrows() {
        DoubleStream s = DoubleStream.of(1.0, 2.0);
        s.anyMatch(d -> d > 0.0);
        try {
            s.anyMatch(d -> d > 0.0);
        } catch (IllegalStateException e) {
            return;
        }
        fail("expected IllegalStateException when stream is reused");
    }

    static void fail(String msg) {
        throw new AssertionError(msg);
    }
}
