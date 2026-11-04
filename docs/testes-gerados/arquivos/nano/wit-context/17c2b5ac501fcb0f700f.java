/*
 * @test
 * @summary Test for AclEntry.setPrincipal(UserPrincipal who)
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.UserPrincipal;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetPrincipalWithNull();
        testSetPrincipalWithValidUser();
    }

    private static void testSetPrincipalWithNull() {
        AclEntry.Builder builder = createBuilder();
        try {
            builder.setPrincipal(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testSetPrincipalWithValidUser() {
        UserPrincipal user = new UserPrincipal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };
        AclEntry.Builder builder = createBuilder();
        try {
            AclEntry.Builder result = builder.setPrincipal(user);
            if (result != builder) {
                System.err.println("Failed: setPrincipal did not return the builder itself");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static AclEntry.Builder createBuilder() {
        // Create a dummy builder with minimal required state
        // Since constructor is private, simulate via reflection or assume a static method exists
        // For this test, we assume a static method or a minimal instantiation
        // As per context, we can simulate with a minimal subclass or dummy class
        return new DummyBuilder();
    }

    private static class DummyBuilder extends AclEntry.Builder {
        DummyBuilder() {
            super(AclEntry.AclEntryType.ALLOW, null, new HashSet<>(), new HashSet<>());
        }
    }
}
