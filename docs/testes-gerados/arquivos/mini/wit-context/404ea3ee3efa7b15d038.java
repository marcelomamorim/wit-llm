/*
 * @test
 * @summary Test javax.security.auth.Subject.getPrivateCredentials(Class<T>) for null argument and normal usage
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPrivateCredentialsNullClass();
        testGetPrivateCredentialsEmptySet();
    }

    private static void testGetPrivateCredentialsNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrivateCredentials(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetPrivateCredentialsEmptySet() {
        Subject subject = new Subject();
        Set<Object> privCreds = subject.getPrivateCredentials(Object.class);
        if (privCreds == null) {
            throw new RuntimeException("getPrivateCredentials returned null instead of empty Set");
        }
        if (!privCreds.isEmpty()) {
            throw new RuntimeException("Expected empty Set from getPrivateCredentials on new Subject");
        }
        // test that the returned Set supports add operation (not throwing UnsupportedOperationException)
        // but since the returned Set is a ClassSet backed by PRIV_CREDENTIAL_SET, which is internal,
        // we only test that add does not throw NullPointerException or ClassCastException for valid input
        try {
            privCreds.add(new Principal() {
                public String getName() { return "testPrincipal"; }
                public boolean equals(Object o) { return this == o; }
                public int hashCode() { return 1; }
                public String toString() { return "testPrincipal"; }
            });
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when adding to private credentials Set: " + e);
        }
    }
}
