/*
 * @test
 * @summary Test BasicPermission.BasicPermissionCollection constructor behavior
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;
import java.util.concurrent.ConcurrentHashMap;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        // Use a concrete subclass of BasicPermission since BasicPermission is abstract
        class TestPermission extends BasicPermission {
            private static final long serialVersionUID = 1L;
            public TestPermission(String name) {
                super(name);
            }
        }

        // Test constructor with a valid class
        BasicPermission.BasicPermissionCollection collection = new BasicPermission.BasicPermissionCollection(TestPermission.class);

        // Check that the internal ConcurrentHashMap is initialized (indirectly)
        // Since perms is private, we test observable behavior: add a permission and check implies
        TestPermission p1 = new TestPermission("test.permission");
        collection.add(p1);
        if (!collection.implies(p1)) {
            throw new RuntimeException("BasicPermissionCollection should imply added permission");
        }

        // Check that all_allowed is false initially
        // No direct access, but implies should be false for unrelated permission
        TestPermission p2 = new TestPermission("other.permission");
        if (collection.implies(p2)) {
            throw new RuntimeException("BasicPermissionCollection should not imply unrelated permission");
        }

        // Check that permClass is set correctly by adding a permission of different class
        // Adding a permission of different class should throw ClassCastException
        try {
            BasicPermission.BasicPermissionCollection otherCollection = new BasicPermission.BasicPermissionCollection(BasicPermission.class);
            // Add a permission of a different subclass
            otherCollection.add(p1); // p1 is TestPermission, otherCollection expects BasicPermission
            throw new RuntimeException("Expected ClassCastException when adding permission of wrong class");
        } catch (ClassCastException expected) {
            // expected
        }

        System.out.println("BasicPermissionCollection constructor and basic behavior tests passed.");
    }
}
