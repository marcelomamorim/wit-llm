/*
 * @test
 * @summary Test AclEntry.Builder build() method for correct construction and exceptions
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AclEntryWitupTest {

    static class DummyUserPrincipal implements UserPrincipal {
        private final String name;
        DummyUserPrincipal(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public static void main(String[] args) {
        testBuildSuccess();
        testBuildMissingType();
        testBuildMissingWho();
    }

    private static void testBuildSuccess() {
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setType(AclEntryType.ALLOW);
        UserPrincipal user = new DummyUserPrincipal("user1");
        builder.setPrincipal(user);
        Set<AclEntryPermission> perms = new HashSet<>();
        perms.add(AclEntryPermission.READ_DATA);
        builder.setPermissions(perms);
        Set<AclEntryFlag> flags = new HashSet<>();
        flags.add(AclEntryFlag.FILE_INHERIT);
        builder.setFlags(flags);

        AclEntry entry = builder.build();
        if (entry == null) {
            throw new RuntimeException("build() returned null");
        }
        if (entry.type() != AclEntryType.ALLOW) {
            throw new RuntimeException("Incorrect type in built AclEntry");
        }
        if (!entry.principal().equals(user)) {
            throw new RuntimeException("Incorrect principal in built AclEntry");
        }
        if (!entry.permissions().contains(AclEntryPermission.READ_DATA)) {
            throw new RuntimeException("Permissions missing in built AclEntry");
        }
        if (!entry.flags().contains(AclEntryFlag.FILE_INHERIT)) {
            throw new RuntimeException("Flags missing in built AclEntry");
        }
    }

    private static void testBuildMissingType() {
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setPrincipal(new DummyUserPrincipal("user2"));
        builder.setPermissions(Collections.emptySet());
        builder.setFlags(Collections.emptySet());
        try {
            builder.build();
            throw new RuntimeException("Expected IllegalStateException for missing type");
        } catch (IllegalStateException e) {
            if (!"Missing type component".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBuildMissingWho() {
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setType(AclEntryType.DENY);
        builder.setPermissions(Collections.emptySet());
        builder.setFlags(Collections.emptySet());
        try {
            builder.build();
            throw new RuntimeException("Expected IllegalStateException for missing who");
        } catch (IllegalStateException e) {
            if (!"Missing who component".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
