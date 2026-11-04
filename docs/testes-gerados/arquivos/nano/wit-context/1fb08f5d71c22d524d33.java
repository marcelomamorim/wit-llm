/* @test    @summary Test for Policy.add(Permission) method to observe behavior and exception handling.    @run main PolicyAddTest */

import java.security.Permission;
import java.security.Policy;
import java.security.PermissionCollection;

public class PolicyAddTest {

    public static void main(String[] args) {
        testAddPermission();
    }

    private static void testAddPermission() {
        Policy policy = Policy.getInstance("JavaPolicy", null);
        Permission permission = new Permission("test.permission") {
            @Override
            public String getActions() {
                return "";
            }
        };
        try {
            policy.add(permission);
            System.out.println("add() executed without exception.");
        } catch (SecurityException se) {
            System.out.println("SecurityException caught as expected.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
