/*
 * @test
 * @summary Test javax.security.auth.Subject.toString() method
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testEmptySubjectToString();
        testSubjectWithPrincipalsToString();
    }

    private static void testEmptySubjectToString() {
        Subject subject = new Subject();
        String str = subject.toString();
        if (str == null || str.isEmpty()) {
            throw new RuntimeException("toString() returned null or empty string for empty Subject");
        }
    }

    private static void testSubjectWithPrincipalsToString() {
        Set<Principal> principals = new HashSet<>();
        principals.add(new Principal() {
            public String getName() {
                return "TestPrincipal";
            }
            public String toString() {
                return "TestPrincipal";
            }
        });
        Subject subject = new Subject(false, principals, new HashSet<>(), new HashSet<>());
        String str = subject.toString();
        if (str == null || !str.contains("TestPrincipal")) {
            throw new RuntimeException("toString() does not include principal name");
        }
    }
}
