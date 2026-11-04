/*
 * @test
 * @summary Test default Destroyable.destroy() throws DestroyFailedException
 * @run main javax.security.auth.DestroyableWitupTest
 */

import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

public class DestroyableWitupTest {
    public static void main(String[] args) {
        Destroyable d = new Destroyable() { /* no override, uses default method */ };
        try {
            d.destroy();
            throw new RuntimeException("Expected DestroyFailedException not thrown");
        } catch (DestroyFailedException e) {
            // expected
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
        // isDestroyed() default returns false
        if (d.isDestroyed() != false) {
            throw new RuntimeException("Expected isDestroyed() to return false");
        }
    }
}
