/*
 * @test
 * @summary Verify the default Destroyable.destroy() throws DestroyFailedException and isDestroyed() returns false
 * @run main DestroyableWitupTest
 */

import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

public class DestroyableWitupTest {
    public static void main(String[] args) {
        Destroyable d = new Destroyable() {};
        boolean thrown = false;
        try {
            d.destroy();
        } catch (DestroyFailedException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected DestroyFailedException from default destroy()");
        }
        if (d.isDestroyed()) {
            throw new RuntimeException("Default isDestroyed() should return false");
        }
    }
}
