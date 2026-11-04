/*
 * @test
 * @summary Test javax.security.auth.Subject.getPrincipals(Class<T>) method
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;
import java.util.Iterator;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testGetPrincipalsWithValidClass();
        testGetPrincipalsWithNullClass();
    }

    private static void testGetPrincipalsWithValidClass() {
        Subject subject = new Subject();

        // The returned set should be empty initially
        Set<Principal> principals = subject.getPrincipals(Principal.class);
        if (principals == null) {
            throw new RuntimeException("getPrincipals returned null instead of empty set");
        }
        if (!principals.isEmpty()) {
            throw new RuntimeException("getPrincipals returned non-empty set on new Subject");
        }

        // Add a Principal implementation and verify it appears in the set
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

        subject.getPrincipals().add(p);

        Set<Principal> principalsFiltered = subject.getPrincipals(Principal.class);
        if (!principalsFiltered.contains(p)) {
            throw new RuntimeException("getPrincipals(Class) did not return added principal");
        }

        // Test filtering by a subclass of Principal (anonymous subclass)
        class SubPrincipal implements Principal {
            public String getName() { return "SubPrincipal"; }
        }
        SubPrincipal sp = new SubPrincipal();
        subject.getPrincipals().add(sp);

        Set<SubPrincipal> subPrincipals = subject.getPrincipals(SubPrincipal.class);
        if (!subPrincipals.contains(sp)) {
            throw new RuntimeException("getPrincipals(Class) did not return added subclass principal");
        }

        // The filtered set should not contain principals of other types
        if (subPrincipals.contains(p)) {
            throw new RuntimeException("getPrincipals(Class) returned principal of wrong type");
        }
    }

    private static void testGetPrincipalsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrincipals(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
