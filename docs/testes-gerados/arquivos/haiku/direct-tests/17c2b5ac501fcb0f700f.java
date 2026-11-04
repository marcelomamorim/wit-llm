/*
 * @test
 * @summary Verify AclEntry.Builder.setPrincipal null rejection and observable principal behavior
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.util.EnumSet;
import java.util.Set;

public class AclEntryWitupTest {

    static final class NamedPrincipal implements UserPrincipal {
        private final String name;

        NamedPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    static AclEntry.Builder baseBuilder() {
        Set<AclEntryPermission> perms = EnumSet.of(AclEntryPermission.READ_DATA);
        Set<AclEntryFlag> flags = EnumSet.noneOf(AclEntryFlag.class);
        return AclEntry.newBuilder()
                .setType(AclEntryType.ALLOW)
                .setPermissions(perms)
                .setFlags(flags);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testReturnsSameBuilder() {
        AclEntry.Builder b = baseBuilder();
        AclEntry.Builder r = b.setPrincipal(new NamedPrincipal("alice"));
        check(r == b, "setPrincipal must return the same builder instance");
    }

    static void testPrincipalIsUsed() {
        NamedPrincipal p = new NamedPrincipal("alice");
        AclEntry entry = baseBuilder().setPrincipal(p).build();
        check(entry.principal() == p, "built entry must expose the principal set on the builder");
    }

    static void testLastPrincipalWins() {
        NamedPrincipal p1 = new NamedPrincipal("alice");
        NamedPrincipal p2 = new NamedPrincipal("bob");
        AclEntry.Builder b = baseBuilder();
        b.setPrincipal(p1);
        b.setPrincipal(p2);
        AclEntry entry = b.build();
        check(entry.principal() == p2, "the most recently set principal must be used");
    }

    static void testNullThrowsNPE() {
        AclEntry.Builder b = baseBuilder();
        try {
            b.setPrincipal(null);
            throw new RuntimeException("expected NullPointerException for null principal");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullDoesNotReplacePreviousPrincipal() {
        NamedPrincipal p1 = new NamedPrincipal("alice");
        AclEntry.Builder b = baseBuilder();
        b.setPrincipal(p1);
        try {
            b.setPrincipal(null);
            throw new RuntimeException("expected NullPointerException for null principal");
        } catch (NullPointerException expected) {
            // expected
        }
        AclEntry entry = b.build();
        check(entry.principal() == p1, "failed null assignment must not replace the previous principal");
    }

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testPrincipalIsUsed();
        testLastPrincipalWins();
        testNullThrowsNPE();
        testNullDoesNotReplacePreviousPrincipal();
    }
}
