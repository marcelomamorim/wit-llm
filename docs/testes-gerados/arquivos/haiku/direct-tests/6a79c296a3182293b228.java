/*
 * @test
 * @summary Verifies the default behavior of Destroyable.destroy() and Destroyable.isDestroyed()
 * @run main DestroyableWitupTest
 */

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

public class DestroyableWitupTest {

    static class DefaultDestroyable implements Destroyable {
    }

    public static void main(String[] args) throws Exception {
        Destroyable d = new DefaultDestroyable();

        boolean thrown = false;
        try {
            d.destroy();
        } catch (DestroyFailedException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Default destroy() did not throw DestroyFailedException");
        }

        if (d.isDestroyed()) {
            throw new RuntimeException("Default isDestroyed() returned true");
        }
    }
}
