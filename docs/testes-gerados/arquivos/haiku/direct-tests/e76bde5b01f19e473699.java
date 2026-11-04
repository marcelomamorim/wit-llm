/*
 * @test
 * @summary Verifies AclEntry.Builder.setType returns the same builder and rejects null with NullPointerException
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        AclEntry.Builder builder = AclEntry.newBuilder();

        AclEntry.Builder returned = builder.setType(AclEntryType.ALLOW);
        if (returned != builder) {
            throw new RuntimeException("setType(ALLOW) must return the same builder");
        }

        boolean npeThrown = false;
        try {
            builder.setType(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("setType(null) must throw NullPointerException");
        }

        AclEntry.Builder afterNull = builder.setType(AclEntryType.DENY);
        if (afterNull != builder) {
            throw new RuntimeException("setType(DENY) after failed null call must return the same builder");
        }
    }
}
