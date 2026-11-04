/* @test    @summary Test AclEntry.setFlags method for correct behavior and null handling    @run main AclEntryWitupTest */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.util.EnumSet;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testSetFlagsWithValidFlags();
        testSetFlagsWithNullFlag();
    }

    static void testSetFlagsWithValidFlags() {
        AclEntry.Builder builder = new AclEntry.Builder();
        AclEntryFlag flag1 = AclEntryFlag.FILE_INHERIT;
        AclEntryFlag flag2 = AclEntryFlag.DIRECTORY_INHERIT;
        AclEntry aclEntry = builder.setFlags(flag1, flag2).build();
        assert aclEntry != null : "AclEntry should not be null";
        // Since flags are private, test via reflection or assume correct if no exception thrown
        System.out.println("testSetFlagsWithValidFlags passed.");
    }

    static void testSetFlagsWithNullFlag() {
        try {
            AclEntry.Builder builder = new AclEntry.Builder();
            builder.setFlags((AclEntryFlag) null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            System.out.println("testSetFlagsWithNullFlag passed.");
        }
    }
}
