/*
 * @test
 * @summary Test for Subject.getPublicCredentials(Class)
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetPublicCredentialsWithNull();
        testGetPublicCredentialsWithValidClass();
    }

    public static void testGetPublicCredentialsWithNull() {
        Subject subject = new Subject();
        try {
            subject.getPublicCredentials(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testGetPublicCredentialsWithValidClass() {
        Subject subject = new Subject();
        Class<Principal> clazz = Principal.class;
        Set<Principal> creds = subject.getPublicCredentials(clazz);
        if (creds == null) {
            throw new AssertionError("getPublicCredentials returned null");
        }
        // Verify that the returned set is empty initially
        if (!creds.isEmpty()) {
            throw new AssertionError("Expected empty set initially");
        }
        // Add a Principal and verify
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "TestPrincipal";
            }
        };
        creds.add(principal);
        if (!creds.contains(principal)) {
            throw new AssertionError("Set does not contain added Principal");
        }
    }
}
