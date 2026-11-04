/*
 * @test
 * @summary Test that FinalReference.enqueue() always throws InternalError
 * @run main FinalReferenceWitupTest
 */

import java.lang.ref.FinalReference;
import java.lang.ref.ReferenceQueue;

public class FinalReferenceWitupTest {
    public static void main(String[] args) {
        Object referent = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        FinalReference<Object> fr = new FinalReference<>(referent, queue);
        try {
            fr.enqueue();
            throw new RuntimeException("Expected InternalError was not thrown");
        } catch (InternalError e) {
            if (!"should never reach here".equals(e.getMessage())) {
                throw new RuntimeException("InternalError message mismatch", e);
            }
        }
    }
}
