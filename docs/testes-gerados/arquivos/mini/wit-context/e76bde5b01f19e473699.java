/*
 * @test
 * @summary Test AclEntry.Builder.setType method for null argument and normal usage
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetTypeWithNull();
        testSetTypeWithValidType();
    }

    private static void testSetTypeWithNull() {
        // Prepare dummy parameters for Builder constructor
        AclEntryType initialType = AclEntryType.ALLOW;
        UserPrincipal dummyPrincipal = new UserPrincipal() {
            public String getName() { return "dummy"; }
        };
        Set<AclEntry.AclEntryPermission> perms = Collections.emptySet();
        Set<AclEntry.AclEntryFlag> flags = Collections.emptySet();

        AclEntry.Builder builder = new AclEntry.Builder(initialType, dummyPrincipal, perms, flags);

        try {
            builder.setType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testSetTypeWithValidType() {
        AclEntryType initialType = AclEntryType.ALLOW;
        UserPrincipal dummyPrincipal = new UserPrincipal() {
            public String getName() { return "dummy"; }
        };
        Set<AclEntry.AclEntryPermission> perms = Collections.emptySet();
        Set<AclEntry.AclEntryFlag> flags = Collections.emptySet();

        AclEntry.Builder builder = new AclEntry.Builder(initialType, dummyPrincipal, perms, flags);

        AclEntryType newType = AclEntryType.DENY;
        AclEntry.Builder returned = builder.setType(newType);

        if (returned != builder) {
            throw new RuntimeException("setType should return the same Builder instance");
        }
    }
}