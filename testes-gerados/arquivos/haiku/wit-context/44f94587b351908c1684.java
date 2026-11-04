/*
 * @test
 * @summary Witup generated test for javax.security.auth.Subject.toString()
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final String NAME = "witup-principal-name";

    static class NamedPrincipal implements Principal {
        public String getName() {
            return NAME;
        }

        public String toString() {
            return "NamedPrincipal[" + NAME + "]";
        }
    }

    public static void main(String[] args) throws Exception {
        Subject empty = new Subject();
        String s1 = empty.toString();
        check(s1 != null, "toString returned null for empty Subject");
        check(!s1.isEmpty(), "toString returned empty string for empty Subject");
        check(s1.equals(empty.toString()), "toString is not deterministic for empty Subject");

        Set<Principal> principals = new HashSet<>();
        principals.add(new NamedPrincipal());
        Subject withPrincipal = new Subject(false, principals, new HashSet<Object>(), new HashSet<Object>());
        String out = withPrincipal.toString();
        check(out != null, "toString returned null for Subject with principal");
        check(out.contains(NAME), "toString does not reflect principal name");

        Subject readOnly = new Subject(true, principals, new HashSet<Object>(), new HashSet<Object>());
        String ro = readOnly.toString();
        check(ro != null && !ro.isEmpty(), "toString returned invalid value for read-only Subject");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
