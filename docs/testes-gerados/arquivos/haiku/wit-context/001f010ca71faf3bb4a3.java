/*
 * @test
 * @summary Verifies the observable state of a Subject created by the no-argument constructor
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    public static void main(String[] args) {
        Subject subject = new Subject();

        check(subject.getPrincipals() != null, "principals set must not be null");
        check(subject.getPublicCredentials() != null, "public credentials set must not be null");
        check(subject.getPrivateCredentials() != null, "private credentials set must not be null");

        check(subject.getPrincipals().isEmpty(), "new Subject must have no principals");
        check(subject.getPublicCredentials().isEmpty(), "new Subject must have no public credentials");
        check(subject.getPrivateCredentials().isEmpty(), "new Subject must have no private credentials");

        Principal alice = new Principal() {
            public String getName() {
                return "alice";
            }
        };
        check(subject.getPrincipals().add(alice), "adding a principal to a default Subject must succeed");
        check(subject.getPrincipals().size() == 1, "principal set must contain one element");
        check(subject.getPrincipals().contains(alice), "principal set must contain the added principal");

        check(subject.getPublicCredentials().add("public-cred"), "adding a public credential must succeed");
        check(subject.getPublicCredentials().size() == 1, "public credential set must contain one element");

        check(subject.getPrivateCredentials().add("private-cred"), "adding a private credential must succeed");
        check(subject.getPrivateCredentials().size() == 1, "private credential set must contain one element");

        Subject other = new Subject();
        check(other.getPrincipals().isEmpty(), "principals must not be shared between Subject instances");
        check(other.getPublicCredentials().isEmpty(), "public credentials must not be shared between Subject instances");
        check(other.getPrivateCredentials().isEmpty(), "private credentials must not be shared between Subject instances");

        Set<?> principals = subject.getPrincipals();
        check(principals.size() == 1, "returned principal set must reflect the stored state");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
