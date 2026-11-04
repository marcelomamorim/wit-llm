/*
 * @test
 * @summary Verifies that AclEntry.Builder.setPrincipal rejects null and returns the same builder for a valid principal
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.UserPrincipal;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        AclEntry.Builder builder = AclEntry.newBuilder();

        boolean thrown = false;
        try {
            builder.setPrincipal(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("setPrincipal(null) should throw NullPointerException");
        }

        UserPrincipal user = () -> "alice";
        if (builder.setPrincipal(user) != builder) {
            throw new AssertionError("setPrincipal(valid) should return the same Builder instance");
        }

        UserPrincipal other = () -> "bob";
        if (builder.setPrincipal(other) != builder) {
            throw new AssertionError("setPrincipal(valid) should be chainable after a previous call");
        }
    }
}
