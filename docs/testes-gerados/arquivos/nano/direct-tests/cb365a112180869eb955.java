/* @test    @summary Test enqueue method of FinalReference throws InternalError as expected    @run main FinalReferenceWitupTest */

import java.lang.ref.FinalReference;

public class FinalReferenceWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsInternalError();
    }

    public static void testEnqueueThrowsInternalError() {
        FinalReference<Object> ref = new FinalReference<>(new Object(), null);
        try {
            ref.enqueue();
            // If no exception, test fails
            System.err.println("Test failed: enqueue() did not throw InternalError");
            System.exit(1);
        } catch (InternalError e) {
            // Expected exception
            System.out.println("enqueue() threw InternalError as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: enqueue() threw unexpected exception: " + t);
            System.exit(1);
        }
    }
}
