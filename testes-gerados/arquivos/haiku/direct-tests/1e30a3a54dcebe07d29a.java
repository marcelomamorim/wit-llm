/*
 * @test
 * @summary Verifies AclEntry.Builder.setFlags returns the same builder and rejects null flags
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        AclEntry.Builder b = AclEntry.newBuilder();

        AclEntry.Builder r = b.setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT);
        if (r != b) {
            throw new AssertionError("setFlags should return the same builder");
        }

        if (b.setFlags() != b) {
            throw new AssertionError("setFlags with no arguments should return the same builder");
        }

        if (b.setFlags(new AclEntryFlag[0]) != b) {
            throw new AssertionError("setFlags with empty array should return the same builder");
        }

        try {
            b.setFlags(AclEntryFlag.FILE_INHERIT, null);
            throw new AssertionError("NullPointerException expected for null element");
        } catch (NullPointerException expected) {
        }

        try {
            b.setFlags((AclEntryFlag[]) null);
            throw new AssertionError("NullPointerException expected for null array");
        } catch (NullPointerException expected) {
        }
    }
}
