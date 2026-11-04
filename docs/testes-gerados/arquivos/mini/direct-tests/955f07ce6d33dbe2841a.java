/*
 * @test
 * @summary Test javax.security.auth.Subject.remove(Object) method behavior
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testRemoveNullThrowsNPE();
        testRemoveNonExistingReturnsFalse();
        testRemoveExistingPrincipalReturnsTrue();
        testRemoveExistingPublicCredentialReturnsTrue();
        testRemoveExistingPrivateCredentialReturnsTrue();
    }

    private static void testRemoveNullThrowsNPE() {
        Subject subject = new Subject();
        try {
            subject.remove(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testRemoveNonExistingReturnsFalse() {
        Subject subject = new Subject();
        Principal p = new Principal() {
            public String getName() { return "nonexistent"; }
            public boolean equals(Object o) { return false; }
            public int hashCode() { return 1; }
        };
        boolean removed = subject.remove(p);
        if (removed) {
            throw new RuntimeException("remove returned true for non-existing element");
        }
    }

    private static void testRemoveExistingPrincipalReturnsTrue() {
        Subject subject = new Subject();
        Principal p = new Principal() {
            public String getName() { return "principal1"; }
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Principal)) return false;
                return ((Principal)o).getName().equals("principal1");
            }
            public int hashCode() { return "principal1".hashCode(); }
        };
        subject.getPrincipals().add(p);
        boolean removed = subject.remove(p);
        if (!removed) {
            throw new RuntimeException("remove returned false for existing principal");
        }
        if (subject.getPrincipals().contains(p)) {
            throw new RuntimeException("Principal still present after remove");
        }
    }

    private static void testRemoveExistingPublicCredentialReturnsTrue() {
        Subject subject = new Subject();
        String credential = "publicCredential";
        subject.getPublicCredentials().add(credential);
        boolean removed = subject.remove(credential);
        if (!removed) {
            throw new RuntimeException("remove returned false for existing public credential");
        }
        if (subject.getPublicCredentials().contains(credential)) {
            throw new RuntimeException("Public credential still present after remove");
        }
    }

    private static void testRemoveExistingPrivateCredentialReturnsTrue() {
        Subject subject = new Subject();
        String credential = "privateCredential";
        subject.getPrivateCredentials().add(credential);
        boolean removed = subject.remove(credential);
        if (!removed) {
            throw new RuntimeException("remove returned false for existing private credential");
        }
        if (subject.getPrivateCredentials().contains(credential)) {
            throw new RuntimeException("Private credential still present after remove");
        }
    }
}
