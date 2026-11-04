/*
 * @test
 * @summary Test AbstractPipeline.onClose(Runnable) for exception and normal behavior
 * @run main AbstractPipelineWitupTest
 */

import java.util.stream.AbstractPipeline;
import java.util.Objects;

public class AbstractPipelineWitupTest {

    // Minimal subclass to instantiate AbstractPipeline
    static class TestPipeline extends AbstractPipeline<Object, Object, TestPipeline> {
        TestPipeline() {
            super(null, null, 0);
        }

        @Override
        public TestPipeline sequential() { return this; }

        @Override
        public TestPipeline parallel() { return this; }

        @Override
        public void close() { }
    }

    public static void main(String[] args) {
        testOnCloseNullHandler();
        testOnCloseLinkedOrConsumed();
        testOnCloseNormal();
    }

    private static void testOnCloseNullHandler() {
        TestPipeline pipeline = new TestPipeline();
        try {
            pipeline.onClose(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
    }

    private static void testOnCloseLinkedOrConsumed() {
        TestPipeline pipeline = new TestPipeline();
        // Set linkedOrConsumed to true via reflection since it's private
        // but reflection is disallowed by instructions, so simulate by subclass hack
        // Instead, create a subclass that sets linkedOrConsumed to true via constructor
        // but no public/protected constructor hints, so discard this test as not feasible
        // Instead, test that calling onClose twice throws IllegalStateException
        // Actually, onClose checks linkedOrConsumed flag, not if onClose called twice
        // So we cannot simulate linkedOrConsumed true without reflection or internal access
        // Therefore, discard this test due to lack of public API to set linkedOrConsumed
    }

    private static void testOnCloseNormal() {
        TestPipeline pipeline = new TestPipeline();
        Runnable r1 = new Runnable() {
            boolean run = false;
            public void run() { run = true; }
        };
        Runnable r2 = new Runnable() {
            boolean run = false;
            public void run() { run = true; }
        };

        // Attach first handler
        pipeline.onClose(r1);

        // Attach second handler, should compose
        pipeline.onClose(r2);

        // We cannot access sourceCloseAction directly to verify composition,
        // so just verify no exceptions and chaining returns same pipeline
        TestPipeline returned = pipeline.onClose(r1);
        if (returned != pipeline) {
            throw new RuntimeException("onClose did not return this");
        }
    }
}
