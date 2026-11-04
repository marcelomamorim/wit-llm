/*
 * @test
 * @summary Test default Destroyable.destroy() throws DestroyFailedException
 * @run main DestroyableWitupTest
 */

import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

public class DestroyableWitupTest {
    public static void main(String[] args) throws Exception {
        Destroyable d = new Destroyable() { };
        try {
            d.destroy();
            throw new RuntimeException("Expected DestroyFailedException not thrown");
        } catch (DestroyFailedException e) {
            // expected
        }
        if (d.isDestroyed()) {
            throw new RuntimeException("Default isDestroyed() should return false");
        }
    }
}
