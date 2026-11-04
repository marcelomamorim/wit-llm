/*
 * @test
 * @summary Verifies AclEntry.Builder.build() validation and returned component values
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.util.EnumSet;
import java.util.Set;

public class AclEntryWitupTest {

    static UserPrincipal user(final String name) {
        return new UserPrincipal() {
            public String getName() {
                return name;
            }
        };
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMissingTypeThrows() {
        try {
            AclEntry.newBuilder()
                    .setPrincipal(user("alice"))
                    .build();
            throw new RuntimeException("expected IllegalStateException for missing type");
        } catch (IllegalStateException e) {
            check("Missing type component".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testMissingWhoThrows() {
        try {
            AclEntry.newBuilder()
                    .setType(AclEntryType.ALLOW)
                    .build();
            throw new RuntimeException("expected IllegalStateException for missing who");
        } catch (IllegalStateException e) {
            check("Missing who component".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testMissingBothReportsTypeFirst() {
        try {
            AclEntry.newBuilder().build();
            throw new RuntimeException("expected IllegalStateException when both missing");
        } catch (IllegalStateException e) {
            check("Missing type component".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testBuildReturnsComponents() {
        UserPrincipal bob = user("bob");
        Set<AclEntryPermission> perms = EnumSet.of(AclEntryPermission.READ_DATA);
        Set<AclEntryFlag> flags = EnumSet.of(AclEntryFlag.FILE_INHERIT);

        AclEntry entry = AclEntry.newBuilder()
                .setType(AclEntryType.DENY)
                .setPrincipal(bob)
                .setPermissions(perms)
                .setFlags(flags)
                .build();

        check(entry.type() == AclEntryType.DENY, "type mismatch");
        check(entry.principal() == bob, "principal mismatch");
        check(entry.permissions().equals(perms), "permissions mismatch");
        check(entry.flags().equals(flags), "flags mismatch");
    }

    public static void main(String[] args) {
        testMissingTypeThrows();
        testMissingWhoThrows();
        testMissingBothReportsTypeFirst();
        testBuildReturnsComponents();
    }
}
