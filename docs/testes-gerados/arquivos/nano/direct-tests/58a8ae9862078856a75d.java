/* @test    @summary Test the build() method of AclEntry for valid and invalid states    @run main AclEntryWitupTest */

import java.nio.file.attribute.*;
import java.util.*;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testBuildWithValidState();
        testBuildWithNullType();
        testBuildWithNullWho();
    }

    static void testBuildWithValidState() {
        UserPrincipal user = new UserPrincipal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };
        Set<AclEntryPermission> perms = new HashSet<>();
        perms.add(AclEntryPermission.READ_DATA);
        Set<AclEntryFlag> flags = new HashSet<>();
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.type(AclEntryType.ALLOW);
        builder.who(user);
        builder.perms(perms);
        builder.flags(flags);
        AclEntry entry = builder.build();
        assert entry != null : "Entry should not be null";
        assert entry.type() == AclEntryType.ALLOW : "Type mismatch";
        assert entry.principal().getName().equals("testUser") : "Principal name mismatch";
        assert entry.permissions().contains(AclEntryPermission.READ_DATA) : "Permissions missing";
        assert entry.flags().contains(AclEntryFlag.FILE_INHERIT) == false : "Flags mismatch";
    }

    static void testBuildWithNullType() {
        UserPrincipal user = new UserPrincipal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };
        Set<AclEntryPermission> perms = new HashSet<>();
        perms.add(AclEntryPermission.READ_DATA);
        Set<AclEntryFlag> flags = new HashSet<>();
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.type(null);
        builder.who(user);
        builder.perms(perms);
        builder.flags(flags);
        try {
            builder.build();
            assert false : "Expected IllegalStateException for null type";
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("Missing type component") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testBuildWithNullWho() {
        Set<AclEntryPermission> perms = new HashSet<>();
        perms.add(AclEntryPermission.READ_DATA);
        Set<AclEntryFlag> flags = new HashSet<>();
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.type(AclEntryType.ALLOW);
        builder.who(null);
        builder.perms(perms);
        builder.flags(flags);
        try {
            builder.build();
            assert false : "Expected IllegalStateException for null who";
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("Missing who component") : "Unexpected message: " + e.getMessage();
        }
    }
}
