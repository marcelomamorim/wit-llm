/* @test    @summary Test the add(Permission permission) method of java.security.Policy    @run main PolicyAddMethodTest */

import java.security.Permission;
import java.security.Policy;
import java.security.BasicPermission;

public class PolicyAddMethodTest {

    public static class DummyPermission extends Permission {
        public DummyPermission() {
            super("DummyPermission");
        }
        @Override
        public boolean implies(Permission permission) {
            return false;
        }
    }

    public static void main(String[] args) {
        Policy policy = Policy.getPolicy();
        Permission perm = new DummyPermission();
        try {
            policy.add(perm);
            System.out.println("Permission added successfully.");
        } catch (UnsupportedOperationException e) {
            System.out.println("add() method is unsupported in this Policy implementation.");
        } catch (SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        }
    }
}
