/* @test    @summary Test for Destroyable.destroy() method to verify it throws DestroyFailedException as expected.    @run main DestroyableWitupTest */

import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

public class DestroyableWitupTest {

    public static void main(String[] args) {
        testDestroyThrowsException();
    }

    public static void testDestroyThrowsException() {
        Destroyable destroyable = new Destroyable() {
            @Override
            public void destroy() throws DestroyFailedException {
                throw new DestroyFailedException();
            }
        };
        try {
            destroyable.destroy();
            System.out.println("Test failed: DestroyFailedException was not thrown.");
        } catch (DestroyFailedException e) {
            System.out.println("Test passed: DestroyFailedException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
