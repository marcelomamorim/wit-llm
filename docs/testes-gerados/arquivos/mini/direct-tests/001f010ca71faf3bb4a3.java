/*
 * @test
 * @summary Test default constructor of javax.security.auth.Subject
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) {
        Subject subject = new Subject();

        // Check that principals set is not null and initially empty
        Set<Principal> principals = subject.getPrincipals();
        if (principals == null) {
            throw new RuntimeException("Principals set is null");
        }
        if (!principals.isEmpty()) {
            throw new RuntimeException("Principals set should be empty initially");
        }

        // Check that public credentials set is not null and initially empty
        Set<?> pubCreds = subject.getPublicCredentials();
        if (pubCreds == null) {
            throw new RuntimeException("Public credentials set is null");
        }
        if (!pubCreds.isEmpty()) {
            throw new RuntimeException("Public credentials set should be empty initially");
        }

        // Check that private credentials set is not null and initially empty
        Set<?> privCreds = subject.getPrivateCredentials();
        if (privCreds == null) {
            throw new RuntimeException("Private credentials set is null");
        }
        if (!privCreds.isEmpty()) {
            throw new RuntimeException("Private credentials set should be empty initially");
        }

        // Check that the sets are synchronized sets by attempting concurrent modification
        // This is a basic check: add and remove elements in multiple threads
        // Here we just do a simple add/remove to check no exceptions
        Principal dummyPrincipal = new Principal() {
            public String getName() { return "dummy"; }
            public boolean equals(Object o) { return this == o; }
            public int hashCode() { return System.identityHashCode(this); }
        };

        principals.add(dummyPrincipal);
        if (!principals.contains(dummyPrincipal)) {
            throw new RuntimeException("Failed to add principal to principals set");
        }
        principals.remove(dummyPrincipal);
        if (principals.contains(dummyPrincipal)) {
            throw new RuntimeException("Failed to remove principal from principals set");
        }

        // Add and remove from public credentials
        Object pubCred = new Object();
        pubCreds.add(pubCred);
        if (!pubCreds.contains(pubCred)) {
            throw new RuntimeException("Failed to add to public credentials set");
        }
        pubCreds.remove(pubCred);
        if (pubCreds.contains(pubCred)) {
            throw new RuntimeException("Failed to remove from public credentials set");
        }

        // Add and remove from private credentials
        Object privCred = new Object();
        privCreds.add(privCred);
        if (!privCreds.contains(privCred)) {
            throw new RuntimeException("Failed to add to private credentials set");
        }
        privCreds.remove(privCred);
        if (privCreds.contains(privCred)) {
            throw new RuntimeException("Failed to remove from private credentials set");
        }
    }
}
