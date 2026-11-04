/* @test    @summary Test for FinalReference.enqueue() method to verify exception throwing behavior.    @run main FinalReferenceWitupTest */

import java.lang.ref.FinalReference;

public class FinalReferenceWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsInternalError();
    }

    private static void testEnqueueThrowsInternalError() {
        FinalReference<Object> ref = new FinalReference<>(new Object(), null);
        try {
            ref.enqueue();
            System.err.println("Expected InternalError was not thrown.");
            System.exit(1);
        } catch (InternalError e) {
            if ("should never reach here".equals(e.getMessage())) {
                System.out.println("InternalError thrown as expected.");
            } else {
                System.err.println("InternalError message mismatch.");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
