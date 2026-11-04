/*
 * @test
 * @summary Test javax.security.auth.Subject.getPrivateCredentials(Class<T>) method
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPrivateCredentialsWithValidClass();
        testGetPrivateCredentialsWithNullClass();
    }

    private static void testGetPrivateCredentialsWithValidClass() {
        Subject subject = new Subject();
        Set<String> creds = subject.getPrivateCredentials(String.class);
        if (creds == null) {
            throw new RuntimeException("getPrivateCredentials returned null instead of empty Set");
        }
        if (!creds.isEmpty()) {
            throw new RuntimeException("Expected empty Set from getPrivateCredentials on new Subject");
        }
    }

    private static void testGetPrivateCredentialsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrivateCredentials(null);
            throw new RuntimeException("Expected NullPointerException when passing null to getPrivateCredentials");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
