/*
 * @test
 * @summary Test AclEntry.Builder setType method behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        testSetTypeWithValidType();
        testSetTypeWithNullThrowsNPE();
    }

    private static void testSetTypeWithValidType() {
        AclEntry.Builder builder = AclEntry.newBuilder();
        AclEntryType type = AclEntryType.ALLOW;
        AclEntry.Builder returned = builder.setType(type);
        if (returned != builder) {
            throw new AssertionError("setType should return the same Builder instance");
        }
    }

    private static void testSetTypeWithNullThrowsNPE() {
        AclEntry.Builder builder = AclEntry.newBuilder();
        try {
            builder.setType(null);
            throw new AssertionError("setType(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
