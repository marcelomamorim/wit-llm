/*
 * @test
 * @summary Test AclEntry.Builder.build() for expected IllegalStateException on null fields and successful build
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryFlag;
import java.util.*;

public class AclEntryWitupTest {

    // Minimal UserPrincipal implementation for testing
    static class TestUserPrincipal implements UserPrincipal {
        private final String name;
        TestUserPrincipal(String name) { this.name = name; }
        public String getName() { return name; }
    }

    // Minimal AclEntryType enum stub for testing
    enum TestAclEntryType implements AclEntryType {
        ALLOW, DENY;
        public String name() { return super.toString(); }
    }

    public static void main(String[] args) {
        testBuildThrowsWhenTypeNull();
        testBuildThrowsWhenWhoNull();
        testBuildSucceeds();
        System.out.println("All tests passed.");
    }

    private static void testBuildThrowsWhenTypeNull() {
        AclEntry.Builder builder = new AclEntry.Builder(null, new TestUserPrincipal("user"),
                EnumSet.noneOf(AclEntryPermission.class), EnumSet.noneOf(AclEntryFlag.class));
        try {
            builder.build();
            throw new RuntimeException("Expected IllegalStateException for null type");
        } catch (IllegalStateException e) {
            if (!"Missing type component".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBuildThrowsWhenWhoNull() {
        AclEntry.Builder builder = new AclEntry.Builder(TestAclEntryType.ALLOW, null,
                EnumSet.noneOf(AclEntryPermission.class), EnumSet.noneOf(AclEntryFlag.class));
        try {
            builder.build();
            throw new RuntimeException("Expected IllegalStateException for null who");
        } catch (IllegalStateException e) {
            if (!"Missing who component".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBuildSucceeds() {
        AclEntry.Builder builder = new AclEntry.Builder(TestAclEntryType.ALLOW, new TestUserPrincipal("user"),
                EnumSet.noneOf(AclEntryPermission.class), EnumSet.noneOf(AclEntryFlag.class));
        AclEntry entry = builder.build();
        if (entry == null) {
            throw new RuntimeException("build() returned null");
        }
        if (!TestAclEntryType.ALLOW.equals(entry.type())) {
            throw new RuntimeException("Unexpected type in built AclEntry");
        }
        if (!"user".equals(entry.principal().getName())) {
            throw new RuntimeException("Unexpected principal in built AclEntry");
        }
        if (!entry.permissions().isEmpty()) {
            throw new RuntimeException("Expected empty permissions set");
        }
        if (!entry.flags().isEmpty()) {
            throw new RuntimeException("Expected empty flags set");
        }
    }
}