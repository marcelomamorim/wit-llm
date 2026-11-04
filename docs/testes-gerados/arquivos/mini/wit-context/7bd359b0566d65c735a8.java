/*
 * @test
 * @summary Test javax.security.auth.Subject.getPrincipals(Class<T>) for null argument and normal usage
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPrincipalsNullArgument();
        testGetPrincipalsWithValidClass();
    }

    private static void testGetPrincipalsNullArgument() {
        Subject subject = new Subject();
        try {
            subject.getPrincipals(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetPrincipalsWithValidClass() {
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPrincipals(Principal.class);
        if (principals == null) {
            throw new RuntimeException("getPrincipals returned null instead of empty Set");
        }
        // The returned set should be empty initially
        if (!principals.isEmpty()) {
            throw new RuntimeException("Expected empty Set of principals");
        }
        // Adding a Principal to the returned set should be allowed
        Principal p = new Principal() {
            public String getName() { return "TestPrincipal"; }
            public String toString() { return getName(); }
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Principal)) return false;
                return getName().equals(((Principal)o).getName());
            }
            public int hashCode() { return getName().hashCode(); }
        };
        principals.add(p);
        if (!principals.contains(p)) {
            throw new RuntimeException("Added principal not found in the set");
        }
    }
}
