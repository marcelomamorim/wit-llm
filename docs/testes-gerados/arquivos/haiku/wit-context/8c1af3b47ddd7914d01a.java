/*
 * @test
 * @summary Verifies SecurityManager.checkAccess(ThreadGroup) rejects null and accepts a non-root thread group
 * @run main SecurityManagerWitupTest
 */

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        boolean thrown = false;
        try {
            sm.checkAccess((ThreadGroup) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null thread group");
        }
        ThreadGroup g = new ThreadGroup("witup-group");
        sm.checkAccess(g);
    }
}
