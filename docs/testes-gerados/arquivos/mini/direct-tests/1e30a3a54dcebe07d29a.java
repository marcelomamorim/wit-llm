/*
 * @test
 * @summary Test AclEntry.Builder.setFlags method behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.util.EnumSet;
import java.util.Set;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        testSetFlagsWithValidFlags();
        testSetFlagsWithNullElement();
    }

    private static void testSetFlagsWithValidFlags() {
        AclEntry.Builder builder = new AclEntry.Builder(null, null, EnumSet.noneOf(AclEntryPermission.class), EnumSet.noneOf(AclEntryFlag.class));
        builder.setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT);
        Set<AclEntryFlag> flags = builder.flags;
        if (!flags.contains(AclEntryFlag.FILE_INHERIT) || !flags.contains(AclEntryFlag.DIRECTORY_INHERIT)) {
            throw new AssertionError("Flags not set correctly");
        }
        if (flags.size() != 2) {
            throw new AssertionError("Flags set size incorrect");
        }
    }

    private static void testSetFlagsWithNullElement() {
        AclEntry.Builder builder = new AclEntry.Builder(null, null, EnumSet.noneOf(AclEntryPermission.class), EnumSet.noneOf(AclEntryFlag.class));
        try {
            builder.setFlags(AclEntryFlag.FILE_INHERIT, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
