/* @test    @summary Test UnresolvedPermissionCollection constructor and add method    @run main UnresolvedPermissionCollectionWitupTest */

import java.security.UnresolvedPermissionCollection;
import java.security.Permission;
import java.security.UnresolvedPermission;

public class UnresolvedPermissionCollectionWitupTest {

    public static void main(String[] args) {
        testConstructorCreatesInstance();
        testAddUnresolvedPermission();
        testGetUnresolvedPermissionsReturnsCorrectList();
    }

    static void testConstructorCreatesInstance() {
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        if (upc == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    static void testAddUnresolvedPermission() {
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        UnresolvedPermission up = new UnresolvedPermission("type", "name", "actions", null);
        upc.add(up);
        java.util.List<UnresolvedPermission> list = upc.getUnresolvedPermissions(up);
        if (list == null || list.isEmpty()) {
            throw new AssertionError("add() did not store permission correctly");
        }
        if (!list.contains(up)) {
            throw new AssertionError("Stored list does not contain added permission");
        }
    }

    static void testGetUnresolvedPermissionsReturnsCorrectList() {
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        UnresolvedPermission up1 = new UnresolvedPermission("typeA", "nameA", "actionsA", null);
        UnresolvedPermission up2 = new UnresolvedPermission("typeA", "nameB", "actionsB", null);
        UnresolvedPermission up3 = new UnresolvedPermission("typeB", "nameC", "actionsC", null);
        upc.add(up1);
        upc.add(up2);
        upc.add(up3);
        java.util.List<UnresolvedPermission> listA = upc.getUnresolvedPermissions(up1);
        java.util.List<UnresolvedPermission> listB = upc.getUnresolvedPermissions(up3);
        if (listA == null || listA.size() != 2) {
            throw new AssertionError("Incorrect list for typeA permissions");
        }
        if (!listA.contains(up1) || !listA.contains(up2)) {
            throw new AssertionError("List for typeA does not contain correct permissions");
        }
        if (listB == null || listB.size() != 1) {
            throw new AssertionError("Incorrect list for typeB permissions");
        }
        if (!listB.contains(up3)) {
            throw new AssertionError("List for typeB does not contain correct permission");
        }
    }
}
