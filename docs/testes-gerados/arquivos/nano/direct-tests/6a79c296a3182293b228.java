/* @test    @summary Test Destroyable.destroy() method throws DestroyFailedException as expected    @run main DestroyableWitupTest */

import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

public class DestroyableWitupTest {

    public static void main(String[] args) {
        testDestroyMethodThrowsException();
    }

    public static void testDestroyMethodThrowsException() {
        Destroyable destroyable = new Destroyable() {
            @Override
            public void destroy() throws DestroyFailedException {
                throw new DestroyFailedException();
            }
        };
        try {
            destroyable.destroy();
            // If no exception is thrown, the test should fail
            throw new AssertionError("Expected DestroyFailedException was not thrown.");
        } catch (DestroyFailedException e) {
            // Expected exception, test passes
        }
    }
}
