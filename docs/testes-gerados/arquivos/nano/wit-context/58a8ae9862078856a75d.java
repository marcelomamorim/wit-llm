/* @test    @summary Test for AclEntry.build() method to verify exception paths and normal behavior    @run main AclEntryWitupTest */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testBuildWithNullType();
        testBuildWithNullWho();
        testBuildWithValidComponents();
    }

    private static void testBuildWithNullType() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder();
            // Simulate setting other components
            builder.who = new DummyUserPrincipal();
            builder.perms = new HashSet<>();
            builder.flags = new HashSet<>();
            // Set type to null explicitly
            builder.type = null;
            builder.build();
            System.out.println("FAIL: Expected IllegalStateException for null type");
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("Missing type component")) {
                System.out.println("PASS: Caught expected exception for null type");
            } else {
                System.out.println("FAIL: Unexpected message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testBuildWithNullWho() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder();
            // Set type to a dummy value
            builder.type = AclEntryType.ALLOW;
            // Simulate setting other components
            builder.perms = new HashSet<>();
            builder.flags = new HashSet<>();
            // Set who to null explicitly
            builder.who = null;
            builder.build();
            System.out.println("FAIL: Expected IllegalStateException for null who");
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("Missing who component")) {
                System.out.println("PASS: Caught expected exception for null who");
            } else {
                System.out.println("FAIL: Unexpected message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testBuildWithValidComponents() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder();
            builder.type = AclEntryType.ALLOW;
            builder.who = new DummyUserPrincipal();
            builder.perms = new HashSet<>();
            builder.flags = new HashSet<>();
            AclEntry entry = builder.build();
            if (entry != null && entry.type() == AclEntryType.ALLOW && entry.principal() != null) {
                System.out.println("PASS: Successfully built AclEntry with valid components");
            } else {
                System.out.println("FAIL: Built AclEntry is invalid");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    // Dummy implementation for UserPrincipal for testing
    private static class DummyUserPrincipal implements UserPrincipal {
        @Override
        public String getName() {
            return "dummy";
        }
    }
}
