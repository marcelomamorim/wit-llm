/*
 * @test
 * @summary Test for SecurityManager.checkAccept with null host
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // No-op to avoid security exceptions during test
            }
        };
        System.setSecurityManager(sm);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            try {
                security.checkAccept(null, 80);
                System.out.println("Test failed: NullPointerException was not thrown");
            } catch (NullPointerException e) {
                System.out.println("Test passed: NullPointerException was thrown as expected");
            } catch (Exception e) {
                System.out.println("Test failed: Unexpected exception: " + e);
            }
        } else {
            System.out.println("SecurityManager is null");
        }
    }
}
