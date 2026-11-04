/*
 * @test    @summary Test IntPipeline.accept method for correct downstream invocation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class DummyConsumer implements IntConsumer {
        int receivedValue = 0;
        boolean wasCalled = false;

        @Override
        public void accept(int t) {
            this.receivedValue = t;
            this.wasCalled = true;
        }
    }

    public static void main(String[] args) {
        DummyConsumer dummy = new DummyConsumer();
        // Create a minimal subclass of IntPipeline to test accept
        IntPipeline<?> pipeline = new IntPipeline<Object>(null, 0, false) {
            @Override
            public void forEach(IntConsumer action) {
                // Not needed for this test
            }
        };
        // Use reflection to invoke the accept method
        try {
            java.lang.reflect.Method acceptMethod = IntPipeline.class.getDeclaredMethod("accept", int.class);
            acceptMethod.setAccessible(true);
            acceptMethod.invoke(pipeline, 42);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Call accept directly to simulate the method behavior
        pipeline.accept(42);
        // Verify that downstream.accept was called with the correct value
        if (dummy.wasCalled && dummy.receivedValue == 42) {
            System.out.println("Test passed: accept method correctly forwards value.");
        } else {
            System.out.println("Test failed: accept method did not forward value correctly.");
            System.exit(1);
        }
    }
}
