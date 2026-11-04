/*
 * @test
 * @summary Verifies AclEntry.Builder.setPermissions copies, validates and replaces permissions
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

public class AclEntryWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static UserPrincipal principal() {
        return new UserPrincipal() {
            public String getName() { return "witup-user"; }
        };
    }

    static AclEntry.Builder builder() {
        return AclEntry.newBuilder()
            .setType(AclEntryType.values()[0])
            .setPrincipal(principal());
    }

    static void testNullElementThrowsNPE() {
        AclEntryPermission first = AclEntryPermission.values()[0];
        boolean thrown = false;
        try {
            AclEntry.newBuilder().setPermissions(first, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null element must throw NullPointerException");
    }

    static void testEmptyVarargsClearsPermissions() {
        AclEntry.Builder b = builder();
        b.setPermissions(AclEntryPermission.values());
        b.setPermissions();
        AclEntry entry = b.build();
        check(entry.permissions().isEmpty(), "empty varargs must clear permissions");
    }

    static void testDuplicatesCollapse() {
        AclEntryPermission p = AclEntryPermission.values()[0];
        AclEntry entry = builder().setPermissions(p, p, p).build();
        Set<AclEntryPermission> perms = entry.permissions();
        check(perms.size() == 1, "duplicates must collapse, size=" + perms.size());
        check(perms.contains(p), "permission must be present");
    }

    static void testReplacesPreviousPermissions() {
        AclEntryPermission[] all = AclEntryPermission.values();
        AclEntryPermission first = all[0];
        AclEntry entry = builder()
            .setPermissions(all)
            .setPermissions(first)
            .build();
        Set<AclEntryPermission> perms = entry.permissions();
        check(perms.size() == 1 && perms.contains(first),
              "second call must replace previous permissions");
    }

    static void testReturnsSameBuilder() {
        AclEntry.Builder b = builder();
        AclEntry.Builder r = b.setPermissions(AclEntryPermission.values()[0]);
        check(r == b, "setPermissions must return this builder");
    }

    public static void main(String[] args) {
        testNullElementThrowsNPE();
        testEmptyVarargsClearsPermissions();
        testDuplicatesCollapse();
        testReplacesPreviousPermissions();
        testReturnsSameBuilder();
    }
}
