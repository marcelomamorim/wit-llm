/*
 * @test
 * @summary Basic tests for javax.security.auth.Subject constructor
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithNullPrincipals();
        testConstructorWithNullPrincipalsAndNullPubCredentials();
        testConstructorWithNullPrincipalsAndNullPrivCredentials();
    }

    public static void testDefaultConstructor() {
        Subject subject = new Subject();
        assert subject.getPrincipals() != null : "Principals set should not be null";
        assert subject.getPublicCredentials() != null : "Public credentials set should not be null";
        assert subject.getPrivateCredentials() != null : "Private credentials set should not be null";
    }

    public static void testConstructorWithNullPrincipals() {
        try {
            new Subject(true, null, new HashSet<>(), new HashSet<>());
            assert false : "Expected NullPointerException for null principals";
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void testConstructorWithNullPrincipalsAndNullPubCredentials() {
        try {
            new Subject(true, null, null, new HashSet<>());
            assert false : "Expected NullPointerException for null principals";
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void testConstructorWithNullPrincipalsAndNullPrivCredentials() {
        try {
            new Subject(true, null, new HashSet<>(), null);
            assert false : "Expected NullPointerException for null principals";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
