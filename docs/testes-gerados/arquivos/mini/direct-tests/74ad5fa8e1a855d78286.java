/*
 * @test
 * @summary Test AclEntry.Builder.setPermissions method behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.util.EnumSet;
import java.util.Set;

public class AclEntryWitupTest {
    public static void main(String[] args) throws Exception {
        testSetPermissionsWithValidPermissions();
        testSetPermissionsWithNullElement();
    }

    private static void testSetPermissionsWithValidPermissions() {
        AclEntry.Builder builder = AclEntry.newBuilder();
        builder.setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA);
        Set<AclEntryPermission> perms = builder.permissions();
        if (!perms.contains(AclEntryPermission.READ_DATA)) {
            throw new RuntimeException("Expected READ_DATA permission");
        }
        if (!perms.contains(AclEntryPermission.WRITE_DATA)) {
            throw new RuntimeException("Expected WRITE_DATA permission");
        }
        if (perms.size() != 2) {
            throw new RuntimeException("Expected exactly 2 permissions");
        }
    }

    private static void testSetPermissionsWithNullElement() {
        AclEntry.Builder builder = AclEntry.newBuilder();
        try {
            builder.setPermissions(AclEntryPermission.READ_DATA, null);
            throw new RuntimeException("Expected NullPointerException for null permission");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
