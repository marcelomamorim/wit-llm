/*
 * @test
 * @summary Test AbstractPipeline.onClose(Runnable) method behavior
 * @run main AbstractPipelineWitupTest
 */

import java.util.stream.AbstractPipeline;
import java.util.Objects;

public class AbstractPipelineWitupTest {

    static class TestPipeline extends AbstractPipeline<Integer, Integer, TestPipeline> {

        boolean linkedOrConsumedFlag = false;

        TestPipeline() {
            super(null, 0);
            this.linkedOrConsumed = linkedOrConsumedFlag;
        }

        @Override
        public TestPipeline onClose(Runnable closeHandler) {
            return super.onClose(closeHandler);
        }

        @Override
        public void close() {
            // no-op
        }

        @Override
        public java.util.Spliterator<Integer> spliterator() {
            return null;
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public TestPipeline sequential() {
            return this;
        }

        @Override
        public TestPipeline parallel() {
            return this;
        }

        @Override
        public TestPipeline unordered() {
            return this;
        }

        @Override
        public TestPipeline onClose(java.lang.Runnable closeHandler) {
            return super.onClose(closeHandler);
        }
    }

    public static void main(String[] args) {
        testOnCloseNullHandler();
        testOnCloseLinkedOrConsumedThrows();
        testOnCloseComposesHandlers();
    }

    private static void testOnCloseNullHandler() {
        TestPipeline p = new TestPipeline();
        try {
            p.onClose(null);
            throw new RuntimeException("Expected NullPointerException for null closeHandler");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testOnCloseLinkedOrConsumedThrows() {
        TestPipeline p = new TestPipeline();
        p.linkedOrConsumed = true;
        try {
            p.onClose(() -> {});
            throw new RuntimeException("Expected IllegalStateException when linkedOrConsumed is true");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testOnCloseComposesHandlers() {
        TestPipeline p = new TestPipeline();

        final boolean[] firstRun = {false};
        final boolean[] secondRun = {false};

        Runnable first = new Runnable() {
            public void run() {
                firstRun[0] = true;
            }
        };

        Runnable second = new Runnable() {
            public void run() {
                secondRun[0] = true;
            }
        };

        // Initially no handler
        p.sourceStage.sourceCloseAction = null;

        // Add first handler
        p.onClose(first);
        // Add second handler, should compose
        p.onClose(second);

        // Run composed handler
        p.sourceStage.sourceCloseAction.run();

        if (!firstRun[0]) {
            throw new RuntimeException("First handler was not run");
        }
        if (!secondRun[0]) {
            throw new RuntimeException("Second handler was not run");
        }
    }
}
