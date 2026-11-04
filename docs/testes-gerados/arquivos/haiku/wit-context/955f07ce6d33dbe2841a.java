/*
 * @test
 * @summary Verifies SecureSet.remove(Object) through the public Subject sets: null input throws NullPointerException, present elements are removed, absent elements return false.
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final class Named implements Principal {
        private final String name;

        Named(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Named)) {
                return false;
            }
            return name.equals(((Named) o).name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPrincipalsRemoveNullThrowsNPE() {
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPrincipals();
        boolean thrown = false;
        try {
            principals.remove(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "remove(null) on principals must throw NullPointerException");
    }

    static void testPublicCredentialsRemoveNullThrowsNPE() {
        Subject subject = new Subject();
        Set<Object> creds = subject.getPublicCredentials();
        boolean thrown = false;
        try {
            creds.remove(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "remove(null) on public credentials must throw NullPointerException");
    }

    static void testRemovePresentPrincipal() {
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPrincipals();
        Named alice = new Named("alice");
        principals.add(alice);
        check(principals.contains(alice), "principal should be present before removal");
        check(principals.remove(alice), "remove of present principal should return true");
        check(!principals.contains(alice), "principal should be absent after removal");
    }

    static void testRemoveAbsentPrincipalReturnsFalse() {
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPrincipals();
        principals.add(new Named("bob"));
        check(!principals.remove(new Named("carol")),
              "remove of absent principal should return false");
        check(principals.size() == 1, "set size should be unchanged after failed removal");
    }

    public static void main(String[] args) {
        testPrincipalsRemoveNullThrowsNPE();
        testPublicCredentialsRemoveNullThrowsNPE();
        testRemovePresentPrincipal();
        testRemoveAbsentPrincipalReturnsFalse();
    }
}
