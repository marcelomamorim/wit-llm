/*
 * @test
 * @summary Verify Subject.getPublicCredentials(Class) rejects null and returns a non-null empty set for a fresh Subject
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.Set;

public class SubjectWitupTest {
    public static void main(String[] args) {
        Subject subject = new Subject();

        Set<String> strings = subject.getPublicCredentials(String.class);
        if (strings == null) {
            throw new RuntimeException("getPublicCredentials(String.class) returned null");
        }
        if (!strings.isEmpty()) {
            throw new RuntimeException("Expected empty set for fresh Subject, got: " + strings);
        }

        Set<Integer> ints = subject.getPublicCredentials(Integer.class);
        if (ints == null || !ints.isEmpty()) {
            throw new RuntimeException("Expected non-null empty set for Integer.class");
        }

        try {
            subject.getPublicCredentials((Class<Object>) null);
            throw new RuntimeException("Expected NullPointerException for null Class argument");
        } catch (NullPointerException expected) {
            // expected behavior
        }
    }
}
