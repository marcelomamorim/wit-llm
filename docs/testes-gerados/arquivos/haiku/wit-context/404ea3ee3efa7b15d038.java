/*
 * @test
 * @summary Verify Subject.getPrivateCredentials(Class) null handling and class filtering
 * @modules java.base
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testNullClassThrowsNPE();
        testFilterByClass();
        testNoMatchingCredentialsReturnsEmptySet();
    }

    private static void testNullClassThrowsNPE() {
        Subject subject = new Subject(false,
                new HashSet<Principal>(),
                new HashSet<Object>(),
                new HashSet<Object>());
        try {
            subject.getPrivateCredentials(null);
            throw new RuntimeException("Expected NullPointerException for null class");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFilterByClass() {
        Set<Object> priv = new HashSet<Object>();
        priv.add("secret");
        priv.add(Integer.valueOf(1));
        Subject subject = new Subject(false,
                new HashSet<Principal>(),
                new HashSet<Object>(),
                priv);
        Set<String> strings = subject.getPrivateCredentials(String.class);
        if (strings == null) {
            throw new RuntimeException("getPrivateCredentials returned null");
        }
        if (strings.size() != 1 || !strings.contains("secret")) {
            throw new RuntimeException("Unexpected String credentials: " + strings);
        }
    }

    private static void testNoMatchingCredentialsReturnsEmptySet() {
        Set<Object> priv = new HashSet<Object>();
        priv.add("secret");
        Subject subject = new Subject(false,
                new HashSet<Principal>(),
                new HashSet<Object>(),
                priv);
        Set<Double> doubles = subject.getPrivateCredentials(Double.class);
        if (doubles == null) {
            throw new RuntimeException("Expected empty set, got null");
        }
        if (!doubles.isEmpty()) {
            throw new RuntimeException("Expected no Double credentials: " + doubles);
        }
    }
}
