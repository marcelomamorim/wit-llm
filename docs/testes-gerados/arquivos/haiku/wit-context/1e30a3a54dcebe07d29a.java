/*
 * @test
 * @summary Checks AclEntry.Builder.setFlags null handling and fluent return behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testNullVarargsArrayThrowsNPE();
        testNullElementThrowsNPE();
        testEmptyFlagsReturnsSameBuilder();
        testValidFlagsReturnsSameBuilder();
    }

    static void testNullVarargsArrayThrowsNPE() {
        AclEntry.Builder b = AclEntry.newBuilder();
        AclEntryFlag[] flags = null;
        try {
            b.setFlags(flags);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null varargs array");
    }

    static void testNullElementThrowsNPE() {
        AclEntry.Builder b = AclEntry.newBuilder();
        try {
            b.setFlags(new AclEntryFlag[] { null });
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null element");
    }

    static void testEmptyFlagsReturnsSameBuilder() {
        AclEntry.Builder b = AclEntry.newBuilder();
        AclEntry.Builder r = b.setFlags();
        if (r != b) {
            throw new RuntimeException("setFlags() with no flags must return the same builder");
        }
    }

    static void testValidFlagsReturnsSameBuilder() {
        AclEntryFlag[] all = AclEntryFlag.values();
        AclEntry.Builder b = AclEntry.newBuilder();
        AclEntry.Builder r = b.setFlags(all);
        if (r != b) {
            throw new RuntimeException("setFlags(valid) must return the same builder");
        }
    }
}
