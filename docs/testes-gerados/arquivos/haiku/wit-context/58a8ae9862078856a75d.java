/*
 * @test
 * @summary Verifies that AclEntry.Builder.build() rejects a builder without a type component
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;

public class AclEntryWitupTest {
    public static void main(String[] args) throws Exception {
        AclEntry.Builder builder = AclEntry.newBuilder();
        try {
            builder.build();
            throw new RuntimeException("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            if (!"Missing type component".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
