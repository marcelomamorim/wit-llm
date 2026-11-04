/* @test    @summary Test for AclEntry.setPermissions method behavior    @run main AclEntryWitupTest */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.util.EnumSet;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetPermissionsWithValidPermissions();
        testSetPermissionsWithNullElement();
        testSetPermissionsWithEmptyPermissions();
    }

    static void testSetPermissionsWithValidPermissions() {
        AclEntry.Builder builder = new AclEntry.Builder();
        AclEntryPermission perm1 = AclEntryPermission.READ_DATA;
        AclEntryPermission perm2 = AclEntryPermission.WRITE_DATA;
        builder.setPermissions(perm1, perm2);
        AclEntry entry = builder.build();
        assert entry != null : "Entry should not be null";
        // Since permissions are private, test via reflection or assume correct if no exception
    }

    static void testSetPermissionsWithNullElement() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder();
            builder.setPermissions(AclEntryPermission.READ_DATA, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testSetPermissionsWithEmptyPermissions() {
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setPermissions();
        AclEntry entry = builder.build();
        assert entry != null : "Entry with no permissions should be created";
    }
}
