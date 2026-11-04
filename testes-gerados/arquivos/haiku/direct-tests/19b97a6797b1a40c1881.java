/*
 * @test
 * @summary Verify Subject.getPublicCredentials(Class) filtering, empty results and null handling
 * @run main SubjectWitupTest
 */

import java.util.HashSet;
import java.util.Set;
import java.security.Principal;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    public static void main(String[] args) {
        Set<Object> pub = new HashSet<>();
        pub.add("a");
        pub.add(Integer.valueOf(1));
        Subject s = new Subject(false, new HashSet<Principal>(), pub, new HashSet<Object>());

        Set<String> strings = s.getPublicCredentials(String.class);
        check(strings != null, "result must not be null");
        check(strings.size() == 1, "expected one String credential");
        check(strings.contains("a"), "expected credential \"a\"");

        Set<Integer> ints = s.getPublicCredentials(Integer.class);
        check(ints != null, "Integer result must not be null");
        check(ints.size() == 1, "expected one Integer credential");
        check(ints.contains(Integer.valueOf(1)), "expected credential 1");

        Set<Object> all = s.getPublicCredentials(Object.class);
        check(all.size() == 2, "Object.class should match all public credentials");

        Set<Double> none = s.getPublicCredentials(Double.class);
        check(none != null, "unmatched class must return non-null set");
        check(none.isEmpty(), "unmatched class must return empty set");

        Class<String> nul = null;
        boolean npe = false;
        try {
            s.getPublicCredentials(nul);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null Class must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
