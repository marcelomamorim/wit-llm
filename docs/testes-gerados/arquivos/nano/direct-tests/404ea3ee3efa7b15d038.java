/*
 * @test
 * @summary Test getPrivateCredentials method of javax.security.auth.Subject
 * @run main SubjectGetPrivateCredentialsTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;
import java.util.HashSet;

public class SubjectGetPrivateCredentialsTest {

    public static void main(String[] args) {
        testGetPrivateCredentialsReturnsEmptySetForNullClass();
        testGetPrivateCredentialsReturnsSetForMatchingClass();
    }

    private static void testGetPrivateCredentialsReturnsEmptySetForNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrivateCredentials(null);
            System.out.println("FAIL: Expected NullPointerException for null class");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testGetPrivateCredentialsReturnsSetForMatchingClass() {
        Subject subject = new Subject();
        // Add a Principal to the subject
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "TestPrincipal";
            }
        };
        subject.getPrincipals().add(principal);
        // Add a String credential (not a Principal)
        String credential = "CredentialString";
        subject.getPrivateCredentials().add(credential);
        // Retrieve credentials of type String
        Set<String> creds = subject.getPrivateCredentials(String.class);
        if (creds.contains(credential)) {
            System.out.println("PASS: getPrivateCredentials returned set containing credential");
        } else {
            System.out.println("FAIL: getPrivateCredentials did not return expected credential");
        }
        // Retrieve credentials of a class not added
        Set<Integer> intCreds = subject.getPrivateCredentials(Integer.class);
        if (intCreds.isEmpty()) {
            System.out.println("PASS: getPrivateCredentials returned empty set for unmatched class");
        } else {
            System.out.println("FAIL: getPrivateCredentials returned non-empty set for unmatched class");
        }
    }
}
