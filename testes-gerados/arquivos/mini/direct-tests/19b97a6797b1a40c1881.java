/*
 * @test
 * @summary Test javax.security.auth.Subject.getPublicCredentials(Class<T>) method
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPublicCredentialsWithValidClass();
        testGetPublicCredentialsWithNullClass();
    }

    private static void testGetPublicCredentialsWithValidClass() {
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPublicCredentials(Principal.class);
        if (principals == null) {
            throw new RuntimeException("getPublicCredentials returned null instead of empty Set");
        }
        // The returned set should be empty initially
        if (!principals.isEmpty()) {
            throw new RuntimeException("Expected empty Set from getPublicCredentials");
        }
        // The returned set should support add operation
        try {
            Principal dummy = new Principal() {
                public String getName() { return "dummy"; }
                public boolean equals(Object o) { return this == o; }
                public int hashCode() { return 1; }
                public String toString() { return "dummy"; }
            };
            principals.add(dummy);
            if (!principals.contains(dummy)) {
                throw new RuntimeException("Added element not found in returned Set");
            }
        } catch (UnsupportedOperationException uoe) {
            throw new RuntimeException("Returned Set does not support add operation", uoe);
        }
    }

    private static void testGetPublicCredentialsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPublicCredentials(null);
            throw new RuntimeException("Expected NullPointerException when passing null class");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
