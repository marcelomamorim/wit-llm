/*
 * @test
 * @summary Verify that the no-arg Subject constructor creates empty, writable principal and credential sets
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final class TestPrincipal implements Principal {
        private final String name;

        TestPrincipal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Subject subject = new Subject();

        check(!subject.isReadOnly(), "new Subject must not be read-only");
        check(subject.getPrincipals().isEmpty(), "new Subject must have no principals");
        check(subject.getPublicCredentials().isEmpty(), "new Subject must have no public credentials");
        check(subject.getPrivateCredentials().isEmpty(), "new Subject must have no private credentials");

        Principal alice = new TestPrincipal("alice");
        check(subject.getPrincipals().add(alice), "adding a principal must succeed");
        check(subject.getPrincipals().size() == 1, "principal set size must be 1");
        check(subject.getPrincipals().contains(alice), "principal set must contain added principal");
        check(subject.getPublicCredentials().isEmpty(), "principal add must not affect public credentials");
        check(subject.getPrivateCredentials().isEmpty(), "principal add must not affect private credentials");

        check(subject.getPublicCredentials().add("pub-cred"), "adding a public credential must succeed");
        check(subject.getPublicCredentials().size() == 1, "public credential set size must be 1");
        check(subject.getPublicCredentials().contains("pub-cred"), "public credential set must contain added credential");
        check(subject.getPrivateCredentials().isEmpty(), "public credential add must not affect private credentials");

        check(subject.getPrivateCredentials().add("priv-cred"), "adding a private credential must succeed");
        check(subject.getPrivateCredentials().size() == 1, "private credential set size must be 1");
        check(subject.getPrivateCredentials().contains("priv-cred"), "private credential set must contain added credential");
        check(subject.getPublicCredentials().size() == 1, "private credential add must not affect public credentials");

        Subject other = new Subject();
        check(other.getPrincipals().isEmpty(), "separate Subject must not share principals");
        check(other.getPublicCredentials().isEmpty(), "separate Subject must not share public credentials");
        check(other.getPrivateCredentials().isEmpty(), "separate Subject must not share private credentials");
    }
}
