/*
 * @test
 * @summary Test IntPipeline.accept method for correct downstream invocation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class DummyDownstream implements IntConsumer {
        int receivedValue = -1;
        boolean accepted = false;

        @Override
        public void accept(int value) {
            this.receivedValue = value;
            this.accepted = true;
        }
    }

    public static void main(String[] args) {
        testAcceptMethod();
    }

    public static void testAcceptMethod() {
        DummyDownstream downstream = new DummyDownstream();
        IntPipeline pipeline = new IntPipeline() {
            @Override
            public void forEach(IntConsumer action) {
                // Not used in this test
            }

            @Override
            public boolean isParallel() {
                return false;
            }

            @Override
            public void accept(int t) {
                // Call the method under test
                this.accept(t);
            }
        };
        // Use reflection to invoke the accept method of IntPipeline
        try {
            java.lang.reflect.Method acceptMethod = IntPipeline.class.getDeclaredMethod("accept", int.class);
            acceptMethod.setAccessible(true);
            acceptMethod.invoke(pipeline, 42);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Manually invoke the accept method to simulate the behavior
        // Since the method is protected, we call it via reflection
        try {
            java.lang.reflect.Method acceptMethod = IntPipeline.class.getDeclaredMethod("accept", int.class);
            acceptMethod.setAccessible(true);
            acceptMethod.invoke(pipeline, 42);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // For the purpose of this test, directly call the method
        // as the above reflection does not automatically connect to downstream
        // Instead, simulate the accept call
        pipeline.accept(42);
        // Verify that downstream received the value
        if (downstream.receivedValue != 42 || !downstream.accepted) {
            throw new AssertionError("accept method did not invoke downstream correctly");
        }
        System.out.println("Test passed: accept method correctly invoked downstream.");
    }
}
