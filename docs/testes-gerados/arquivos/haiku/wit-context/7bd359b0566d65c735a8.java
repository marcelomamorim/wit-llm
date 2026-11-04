/*
 * @test
 * @summary Verify Subject.getPrincipals(Class) null handling and class filtering behavior
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static class AlicePrincipal implements Principal {
        private final String name;
        AlicePrincipal(String name) { this.name = name; }
        public String getName() { return name; }
        public String toString() { return "AlicePrincipal[" + name + "]"; }
    }

    static class BobPrincipal implements Principal {
        private final String name;
        BobPrincipal(String name) { this.name = name; }
        public String getName() { return name; }
        public String toString() { return "BobPrincipal[" + name + "]"; }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullClassThrowsNPE() {
        Subject subject = new Subject();
        boolean thrown = false;
        try {
            subject.getPrincipals(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "getPrincipals(null) must throw NullPointerException");
    }

    static void testEmptySubjectReturnsEmptySetNotNull() {
        Subject subject = new Subject();
        Set<AlicePrincipal> result = subject.getPrincipals(AlicePrincipal.class);
        check(result != null, "getPrincipals(Class) must never return null");
        check(result.isEmpty(), "empty subject must yield empty set for requested class");
    }

    static void testFilteringByClass() {
        Set<Principal> principals = new HashSet<>();
        principals.add(new AlicePrincipal("alice"));
        principals.add(new BobPrincipal("bob"));
        principals.add(new AlicePrincipal("alice2"));
        Subject subject = new Subject(false, principals, new HashSet<Object>(), new HashSet<Object>());

        Set<AlicePrincipal> alices = subject.getPrincipals(AlicePrincipal.class);
        check(alices.size() == 2, "expected 2 AlicePrincipal instances but got " + alices.size());
        for (AlicePrincipal p : alices) {
            check(p instanceof AlicePrincipal, "unexpected principal type in result");
        }

        Set<BobPrincipal> bobs = subject.getPrincipals(BobPrincipal.class);
        check(bobs.size() == 1, "expected 1 BobPrincipal instance but got " + bobs.size());

        Set<Principal> all = subject.getPrincipals(Principal.class);
        check(all.size() == 3, "expected 3 principals for Principal.class but got " + all.size());
    }

    static void testNoMatchReturnsEmptySet() {
        Set<Principal> principals = new HashSet<>();
        principals.add(new BobPrincipal("bob"));
        Subject subject = new Subject(false, principals, new HashSet<Object>(), new HashSet<Object>());
        Set<AlicePrincipal> result = subject.getPrincipals(AlicePrincipal.class);
        check(result != null && result.isEmpty(), "no matching principal must yield empty non-null set");
    }

    public static void main(String[] args) {
        testNullClassThrowsNPE();
        testEmptySubjectReturnsEmptySetNotNull();
        testFilteringByClass();
        testNoMatchReturnsEmptySet();
    }
}
