/*
 * @test
 * @summary Test removal of an object from Subject's credential sets.
 * @run main SubjectRemoveTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class SubjectRemoveTest {

    public static void main(String[] args) {
        testRemoveExistingPrincipal();
        testRemoveExistingCredential();
        testRemoveNonExistingObject();
    }

    private static void testRemoveExistingPrincipal() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "TestPrincipal";
            }
        };
        Set<Principal> principals = new HashSet<>();
        principals.add(principal);
        Subject subject = new Subject();
        subject.getPrincipals().addAll(principals);
        boolean removed = subject.remove(principal);
        assert removed : "Principal should be removed successfully";
        assert !subject.getPrincipals().contains(principal) : "Principal should no longer be in the set";
    }

    private static void testRemoveExistingCredential() {
        String credential = "testCredential";
        Set<Object> credentials = new HashSet<>();
        credentials.add(credential);
        Subject subject = new Subject();
        subject.getPublicCredentials().addAll(credentials);
        boolean removed = subject.remove(credential);
        assert removed : "Credential should be removed successfully";
        assert !subject.getPublicCredentials().contains(credential) : "Credential should no longer be in the set";
    }

    private static void testRemoveNonExistingObject() {
        Object obj = new Object();
        Subject subject = new Subject();
        boolean result = subject.remove(obj);
        assert !result : "Removing non-existing object should return false";
    }
}
