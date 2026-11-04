/*
 * @test
 * @summary Verify observable behavior of the public Permissions() constructor: empty collection, independent instances, and add/implies round trip
 * @run main PermissionsWitupTest
 */

import java.security.Permission;
import java.security.Permissions;
import java.util.Enumeration;

public class PermissionsWitupTest {

    public static void main(String[] args) throws Exception {
        testNewInstanceIsEmpty();
        testNewInstanceDoesNotImplyPermission();
        testNewInstanceIsNotReadOnly();
        testAddThenImplies();
        testInstancesAreIndependent();
        testElementsReflectAddedPermission();
    }

    static void testNewInstanceIsEmpty() {
        Permissions p = new Permissions();
        Enumeration<Permission> e = p.elements();
        if (e.hasMoreElements()) {
            throw new RuntimeException("new Permissions() should have no elements");
        }
    }

    static void testNewInstanceDoesNotImplyPermission() {
        Permissions p = new Permissions();
        if (p.implies(new RuntimePermission("exitVM"))) {
            throw new RuntimeException("new Permissions() should not imply any permission");
        }
    }

    static void testNewInstanceIsNotReadOnly() {
        Permissions p = new Permissions();
        if (p.isReadOnly()) {
            throw new RuntimeException("new Permissions() should not be read-only");
        }
    }

    static void testAddThenImplies() {
        Permissions p = new Permissions();
        p.add(new RuntimePermission("exitVM"));
        if (!p.implies(new RuntimePermission("exitVM"))) {
            throw new RuntimeException("added permission should be implied");
        }
    }

    static void testInstancesAreIndependent() {
        Permissions p = new Permissions();
        Permissions q = new Permissions();
        p.add(new RuntimePermission("exitVM"));
        if (q.implies(new RuntimePermission("exitVM"))) {
            throw new RuntimeException("permission added to one instance leaked into another");
        }
    }

    static void testElementsReflectAddedPermission() {
        Permissions p = new Permissions();
        p.add(new RuntimePermission("exitVM"));
        Enumeration<Permission> e = p.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("elements() should contain the added permission");
        }
        Permission first = e.nextElement();
        if (!new RuntimePermission("exitVM").equals(first)) {
            throw new RuntimeException("unexpected element: " + first);
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("expected exactly one element");
        }
    }
}
