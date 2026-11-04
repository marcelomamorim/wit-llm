/*
 * @test
 * @summary Test for Subject.getPrivateCredentials(Class)
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetPrivateCredentialsWithNull();
        testGetPrivateCredentialsWithValidClass();
    }

    private static void testGetPrivateCredentialsWithNull() {
        Subject subject = new Subject();
        try {
            subject.getPrivateCredentials(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetPrivateCredentialsWithValidClass() {
        Subject subject = new Subject();
        try {
            // Using a dummy class for testing
            class DummyCredential {}
            Set<DummyCredential> creds = subject.getPrivateCredentials(DummyCredential.class);
            if (creds != null && creds.isEmpty()) {
                System.out.println("PASS: Returned empty set for new Subject");
            } else {
                System.out.println("FAIL: Unexpected set contents or null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
