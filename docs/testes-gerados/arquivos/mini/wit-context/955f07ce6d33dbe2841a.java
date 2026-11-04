/*
 * @test
 * @summary Test javax.security.auth.Subject.remove(Object) method
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.HashSet;
import java.util.Set;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testRemoveNullThrowsNPE();
        testRemoveNonExistingReturnsFalse();
        testRemoveExistingReturnsTrue();
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
        Set<Object> principals = new HashSet<>();
        principals.add("principal1");
        Subject subject = new Subject(false, principals, new HashSet<>(), new HashSet<>());

        boolean removed = subject.remove("nonExisting");
        if (removed) {
            throw new RuntimeException("remove returned true for non-existing element");
        }
    }

    private static void testRemoveExistingReturnsTrue() {
        Set<Object> principals = new HashSet<>();
        String principal = "principal1";
        principals.add(principal);
        Subject subject = new Subject(false, principals, new HashSet<>(), new HashSet<>());

        boolean removed = subject.remove(principal);
        if (!removed) {
            throw new RuntimeException("remove returned false for existing element");
        }

        // Confirm removal
        if (subject.getPrincipals().contains(principal)) {
            throw new RuntimeException("Element still present after remove");
        }
    }
}
