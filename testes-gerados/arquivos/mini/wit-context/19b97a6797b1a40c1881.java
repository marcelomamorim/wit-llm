/*
 * @test
 * @summary Test javax.security.auth.Subject.getPublicCredentials(Class<T>) for null argument and normal usage
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPublicCredentialsNullClass();
        testGetPublicCredentialsWithClass();
    }

    private static void testGetPublicCredentialsNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPublicCredentials(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetPublicCredentialsWithClass() {
        Subject subject = new Subject();
        Set<Principal> creds = subject.getPublicCredentials(Principal.class);
        if (creds == null) {
            throw new RuntimeException("getPublicCredentials returned null");
        }
        // The returned Set should be empty initially
        if (!creds.isEmpty()) {
            throw new RuntimeException("Expected empty Set from getPublicCredentials");
        }
        // The Set should support add operation (modification)
        Principal dummyPrincipal = new Principal() {
            public String getName() { return "dummy"; }
            public boolean equals(Object o) { return this == o; }
            public int hashCode() { return System.identityHashCode(this); }
        };
        boolean added = creds.add(dummyPrincipal);
        if (!added) {
            throw new RuntimeException("Expected to be able to add to the returned Set");
        }
        if (!creds.contains(dummyPrincipal)) {
            throw new RuntimeException("Added Principal not found in the Set");
        }
    }
}
