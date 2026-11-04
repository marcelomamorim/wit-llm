/*
 * @test
 * @summary Verifies Subject.getPrivateCredentials(Class) null handling, class filtering, and view behavior
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {
    public static void main(String[] args) throws Exception {
        testNullClassThrowsNPE();
        testEmptySubjectReturnsEmptySet();
        testFilterByClass();
        testPublicCredentialsExcluded();
        testReturnedSetIsView();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullClassThrowsNPE() {
        Subject subject = new Subject();
        try {
            subject.getPrivateCredentials(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptySubjectReturnsEmptySet() {
        Subject subject = new Subject();
        Set<String> result = subject.getPrivateCredentials(String.class);
        check(result != null, "result must not be null");
        check(result.isEmpty(), "result must be empty for new Subject");
    }

    static void testFilterByClass() {
        Set<Object> priv = new HashSet<Object>(Arrays.asList("a", Integer.valueOf(1)));
        Subject subject = new Subject(false, new HashSet<Principal>(), new HashSet<Object>(), priv);

        Set<String> strings = subject.getPrivateCredentials(String.class);
        check(strings.size() == 1, "expected one String credential");
        check(strings.contains("a"), "String credential 'a' missing");

        Set<Integer> ints = subject.getPrivateCredentials(Integer.class);
        check(ints.size() == 1, "expected one Integer credential");
        check(ints.contains(Integer.valueOf(1)), "Integer credential 1 missing");
    }

    static void testPublicCredentialsExcluded() {
        Set<Object> pub = new HashSet<Object>(Arrays.asList("pub"));
        Set<Object> priv = new HashSet<Object>(Arrays.asList("secret"));
        Subject subject = new Subject(false, new HashSet<Principal>(), pub, priv);

        Set<String> result = subject.getPrivateCredentials(String.class);
        check(result.size() == 1, "expected only the private String credential");
        check(result.contains("secret"), "private credential 'secret' missing");
        check(!result.contains("pub"), "public credential must not appear in private view");
    }

    static void testReturnedSetIsView() {
        Subject subject = new Subject();
        Set<String> view = subject.getPrivateCredentials(String.class);
        check(view.isEmpty(), "view must start empty");
        view.add("x");
        check(view.contains("x"), "added credential not visible in returned set");
        Set<String> fresh = subject.getPrivateCredentials(String.class);
        check(fresh.contains("x"), "added credential not visible in new view");
        check(fresh.size() == 1, "expected exactly one credential after add");
    }
}
