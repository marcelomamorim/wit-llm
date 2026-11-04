/*
 * @test
 * @summary Test for ReferencePipeline.accept method to observe exception behavior.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testAcceptWithNull();
        testAcceptWithValidInput();
    }

    private static void testAcceptWithNull() {
        try {
            ReferencePipeline<?> pipeline = new ReferencePipeline<Object>(null, 0, false) {
                @Override
                public void accept(Object u) {
                    // No implementation needed for test
                }
            };
            pipeline.accept(null);
            System.out.println("testAcceptWithNull: No exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testAcceptWithNull: Caught expected NullPointerException");
        } catch (Exception e) {
            System.out.println("testAcceptWithNull: Caught unexpected exception: " + e);
        }
    }

    private static void testAcceptWithValidInput() {
        try {
            final boolean[] called = {false};
            ReferencePipeline<Object> pipeline = new ReferencePipeline<Object>(null, 0, false) {
                @Override
                public void accept(Object u) {
                    called[0] = true;
                }
            };
            pipeline.accept(new Object());
            if (called[0]) {
                System.out.println("testAcceptWithValidInput: accept called successfully");
            } else {
                System.out.println("testAcceptWithValidInput: accept not called");
            }
        } catch (Exception e) {
            System.out.println("testAcceptWithValidInput: Caught exception: " + e);
        }
    }
}
