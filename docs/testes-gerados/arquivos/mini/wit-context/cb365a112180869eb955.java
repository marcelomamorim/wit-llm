/*
 * @test
 * @summary Test FinalReference.enqueue() throws InternalError as expected
 * @run main FinalReferenceWitupTest
 */

import java.lang.ref.FinalReference;
import java.lang.ref.ReferenceQueue;

public class FinalReferenceWitupTest {
    public static void main(String[] args) {
        ReferenceQueue<Object> rq = new ReferenceQueue<>();
        FinalReference<Object> fr = new FinalReference<>(new Object(), rq);
        try {
            fr.enqueue();
            throw new RuntimeException("Expected InternalError was not thrown");
        } catch (InternalError e) {
            // Expected exception, test passes
        }
    }
}
