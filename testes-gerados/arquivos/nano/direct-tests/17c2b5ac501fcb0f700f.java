/* @test    @summary Test setting principal in AclEntry.Builder    @run main AclEntryWitupTest */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetPrincipalWithValidUserPrincipal();
        testSetPrincipalWithNull();
    }

    static void testSetPrincipalWithValidUserPrincipal() {
        UserPrincipal user = new UserPrincipal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };
        AclEntry.Builder builder = new AclEntry.Builder(AclEntryType.ALLOW, user, new HashSet<>(), new HashSet<>());
        AclEntry.Builder resultBuilder = builder.setPrincipal(user);
        if (resultBuilder != builder) {
            throw new AssertionError("setPrincipal should return the same builder instance")
        }
        System.out.println("testSetPrincipalWithValidUserPrincipal passed");
    }

    static void testSetPrincipalWithNull() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder(AclEntryType.ALLOW, null, new HashSet<>(), new HashSet<>());
            builder.setPrincipal(null);
            throw new AssertionError("Expected NullPointerException for null UserPrincipal");
        } catch (NullPointerException e) {
            System.out.println("testSetPrincipalWithNull passed");
        }
    }
}
