/*
 * @test
 * @summary Basic tests for javax.security.auth.Subject constructor
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;
import java.util.HashSet;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) throws Exception {
        testDefaultConstructorCreatesNonNullSets();
    }

    private static void testDefaultConstructorCreatesNonNullSets() {
        Subject subject = new Subject();
        // The sets returned should be non-null
        Set<? extends Principal> principals = subject.getPrincipals();
        if (principals == null) {
            throw new AssertionError("getPrincipals() returned null");
        }
        Set<?> pubCreds = subject.getPublicCredentials();
        if (pubCreds == null) {
            throw new AssertionError("getPublicCredentials() returned null");
        }
        Set<?> privCreds = subject.getPrivateCredentials();
        if (privCreds == null) {
            throw new AssertionError("getPrivateCredentials() returned null");
        }

        // The sets should be initially empty
        if (!principals.isEmpty()) {
            throw new AssertionError("Principals set should be empty initially");
        }
        if (!pubCreds.isEmpty()) {
            throw new AssertionError("Public credentials set should be empty initially");
        }
        if (!privCreds.isEmpty()) {
            throw new AssertionError("Private credentials set should be empty initially");
        }

        // The sets should be synchronized sets (observable by checking class name contains "Synchronized")
        // This is a weak check but no public API to verify synchronization
        if (!principals.getClass().getName().contains("Synchronized")) {
            throw new AssertionError("Principals set is not synchronized");
        }
        if (!pubCreds.getClass().getName().contains("Synchronized")) {
            throw new AssertionError("Public credentials set is not synchronized");
        }
        if (!privCreds.getClass().getName().contains("Synchronized")) {
            throw new AssertionError("Private credentials set is not synchronized");
        }
    }
}
