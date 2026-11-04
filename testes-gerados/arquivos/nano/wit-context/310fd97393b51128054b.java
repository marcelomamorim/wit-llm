/*
 * @test
 * @summary Test for Permissions.nextElement() method.
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;
import java.util.NoSuchElementException;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testNextElementWithEmptyPermset();
        testNextElementWithPermset();
    }

    private static void testNextElementWithEmptyPermset() {
        Permissions permissions = new Permissions();
        // Simulate that hasMoreElements() returns false by not adding permissions
        // and forcibly setting permset to null to trigger exception
        // Note: permset is private; so we test the behavior by calling nextElement() directly
        try {
            permissions.nextElement();
            // If no exception, test fails
            System.err.println("Expected NoSuchElementException not thrown in empty permset case.");
        } catch (NoSuchElementException e) {
            // Expected exception
            System.out.println("Passed: NoSuchElementException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testNextElementWithPermset() {
        Permissions permissions = new Permissions();
        // Add a dummy Permission to ensure hasMoreElements() returns true
        permissions.add(new DummyPermission());
        try {
            Permission p = permissions.nextElement();
            if (p != null) {
                System.out.println("Passed: nextElement() returned a Permission.");
            } else {
                System.err.println("Failed: nextElement() returned null.");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static class DummyPermission extends Permission {
        public DummyPermission() {
            super("DummyPermission");
        }
        @Override
        public boolean implies(Permission p) {
            return false;
        }
    }
}
