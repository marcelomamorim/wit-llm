/*
 * @test
 * @summary Verifies that AclEntry.Builder.setType rejects null with NullPointerException and returns the same builder for a valid type
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryType;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        AclEntry.Builder builder = AclEntry.newBuilder();
        try {
            builder.setType(null);
            throw new RuntimeException("NullPointerException expected for null type");
        } catch (NullPointerException expected) {
        }
        AclEntryType[] types = AclEntryType.values();
        if (types.length == 0) {
            throw new RuntimeException("AclEntryType has no constants");
        }
        AclEntry.Builder returned = builder.setType(types[0]);
        if (returned != builder) {
            throw new RuntimeException("setType must return the same Builder instance");
        }
    }
}
