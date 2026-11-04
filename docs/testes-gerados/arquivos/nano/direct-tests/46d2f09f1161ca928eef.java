/* @test    @summary Test AbstractPipeline.onClose method behavior    @run main AbstractPipelineWitupTest */

import java.util.Objects;
import java.util.stream.AbstractPipeline;

public class AbstractPipelineWitupTest {

    public static void main(String[] args) {
        testOnCloseWithNullHandler();
        testOnCloseWithExistingHandler();
        testOnCloseWhenLinkedOrConsumed();
    }

    private static void testOnCloseWithNullHandler() {
        class DummyPipeline extends AbstractPipeline {
            DummyPipeline() {
                super();
            }
            @Override
            public java.util.stream.BaseStream close() { return this; }
        }
        DummyPipeline pipeline = new DummyPipeline();
        try {
            pipeline.onClose(null);
            System.out.println("testOnCloseWithNullHandler: PASSED");
        } catch (NullPointerException e) {
            System.out.println("testOnCloseWithNullHandler: FAILED - NullPointerException thrown");
        } catch (Exception e) {
            System.out.println("testOnCloseWithNullHandler: FAILED - " + e);
        }
    }

    private static void testOnCloseWithExistingHandler() {
        class DummyPipeline extends AbstractPipeline {
            Runnable existingHandler;
            DummyPipeline() {
                super();
                this.existingHandler = () -> {};
            }
            @Override
            public java.util.stream.BaseStream close() { return this; }
        }
        DummyPipeline pipeline = new DummyPipeline();
        Runnable newHandler = () -> {};
        try {
            Object result = pipeline.onClose(newHandler);
            if (result == pipeline) {
                System.out.println("testOnCloseWithExistingHandler: PASSED");
            } else {
                System.out.println("testOnCloseWithExistingHandler: FAILED - return not self");
            }
        } catch (Exception e) {
            System.out.println("testOnCloseWithExistingHandler: FAILED - " + e);
        }
    }

    private static void testOnCloseWhenLinkedOrConsumed() {
        class DummyPipeline extends AbstractPipeline {
            boolean linkedOrConsumed;
            DummyPipeline(boolean linkedOrConsumed) {
                super();
                this.linkedOrConsumed = linkedOrConsumed;
            }
            @Override
            public java.util.stream.BaseStream close() { return this; }
        }
        DummyPipeline linkedPipeline = new DummyPipeline(true);
        try {
            linkedPipeline.onClose(() -> {});
            System.out.println("testOnCloseWhenLinkedOrConsumed: FAILED - no exception");
        } catch (IllegalStateException e) {
            System.out.println("testOnCloseWhenLinkedOrConsumed: PASSED");
        } catch (Exception e) {
            System.out.println("testOnCloseWhenLinkedOrConsumed: FAILED - " + e);
        }
        DummyPipeline consumedPipeline = new DummyPipeline(false);
        consumedPipeline.linkedOrConsumed = true;
        try {
            consumedPipeline.onClose(() -> {});
            System.out.println("testOnCloseWhenLinkedOrConsumed (not linked): FAILED - no exception");
        } catch (IllegalStateException e) {
            System.out.println("testOnCloseWhenLinkedOrConsumed (not linked): PASSED");
        } catch (Exception e) {
            System.out.println("testOnCloseWhenLinkedOrConsumed (not linked): FAILED - " + e);
        }
    }
}
