/*
 * @test
 * @summary Test AclEntry.Builder.setPrincipal method behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.UserPrincipal;

public class AclEntryWitupTest {
    public static void main(String[] args) throws Exception {
        testSetPrincipalWithValidUserPrincipal();
        testSetPrincipalWithNullThrowsNPE();
    }

    private static void testSetPrincipalWithValidUserPrincipal() {
        // Create a dummy UserPrincipal implementation for testing
        UserPrincipal user = new UserPrincipal() {
            public String getName() {
                return "testUser";
            }
        };

        // Create a Builder instance via newBuilder (using a minimal valid builder)
        AclEntry.Builder builder = AclEntry.newBuilder();

        // Set principal and verify the returned builder is the same instance
        AclEntry.Builder returned = builder.setPrincipal(user);
        if (returned != builder) {
            throw new AssertionError("setPrincipal should return the same Builder instance");
        }
    }

    private static void testSetPrincipalWithNullThrowsNPE() {
        AclEntry.Builder builder = AclEntry.newBuilder();
        try {
            builder.setPrincipal(null);
            throw new AssertionError("Expected NullPointerException when setting null principal");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
