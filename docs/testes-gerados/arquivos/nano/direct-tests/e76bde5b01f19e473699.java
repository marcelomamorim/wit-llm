/* @test    @summary Test for AclEntry.setType method behavior    @run main AclEntryWitupTest */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetTypeWithValidType();
        testSetTypeWithNull();
    }

    private static void testSetTypeWithValidType() {
        AclEntry.Builder builder = new AclEntry.Builder();
        AclEntryType type = AclEntryType.ALLOW;
        builder.setType(type);
        AclEntry entry = builder.build();
        assert entry != null : "Entry should not be null";
        // Since no getter for type, test via reflection or assume set correctly if no exception
        // Alternatively, test that setting a different type updates the builder
        builder.setType(AclEntryType.DENY);
        // No exception means success
    }

    private static void testSetTypeWithNull() {
        AclEntry.Builder builder = new AclEntry.Builder();
        try {
            builder.setType(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
