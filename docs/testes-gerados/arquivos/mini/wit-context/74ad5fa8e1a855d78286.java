/*
 * @test
 * @summary Test AclEntry.Builder.setPermissions method for null elements and valid sets
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.util.Set;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        testSetPermissionsWithValidPermissions();
        testSetPermissionsWithNullElement();
    }

    private static void testSetPermissionsWithValidPermissions() {
        AclEntry.Builder builder = new AclEntry.Builder(null, null, java.util.EnumSet.noneOf(AclEntryPermission.class), java.util.EnumSet.noneOf(java.nio.file.attribute.AclEntryFlag.class));
        AclEntryPermission[] perms = new AclEntryPermission[] { AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA };
        AclEntry.Builder result = builder.setPermissions(perms);
        if (result == null) {
            throw new AssertionError("setPermissions returned null");
        }
    }

    private static void testSetPermissionsWithNullElement() {
        AclEntry.Builder builder = new AclEntry.Builder(null, null, java.util.EnumSet.noneOf(AclEntryPermission.class), java.util.EnumSet.noneOf(java.nio.file.attribute.AclEntryFlag.class));
        AclEntryPermission[] perms = new AclEntryPermission[] { AclEntryPermission.READ_DATA, null };
        try {
            builder.setPermissions(perms);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
