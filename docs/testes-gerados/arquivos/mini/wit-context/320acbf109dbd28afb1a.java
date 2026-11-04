/*
 * @test
 * @summary Test javax.security.auth.Subject SecureSet addAll method for null argument and normal behavior
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testAddAllNullCollection();
        testAddAllEmptyCollection();
        testAddAllNonEmptyCollection();
    }

    private static void testAddAllNullCollection() {
        Subject subject = new Subject();
        Set<Subject.Principal> principals = subject.getPrincipals();
        try {
            principals.addAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testAddAllEmptyCollection() {
        Subject subject = new Subject();
        Set<Subject.Principal> principals = subject.getPrincipals();
        boolean result = principals.addAll(Collections.<Subject.Principal>emptySet());
        if (result) {
            throw new RuntimeException("addAll with empty collection should return false");
        }
    }

    private static void testAddAllNonEmptyCollection() {
        Subject subject = new Subject();
        Set<Subject.Principal> principals = subject.getPrincipals();

        // Create a dummy Principal implementation
        PrincipalImpl p1 = new PrincipalImpl("p1");
        PrincipalImpl p2 = new PrincipalImpl("p2");

        // Add one principal first
        principals.add(p1);

        // Prepare collection with one new and one existing principal
        List<Subject.Principal> newPrincipals = new ArrayList<>();
        newPrincipals.add(p1); // already present
        newPrincipals.add(p2); // new

        boolean result = principals.addAll(newPrincipals);

        if (!result) {
            throw new RuntimeException("addAll should return true when at least one element is added");
        }

        if (!principals.contains(p1) || !principals.contains(p2)) {
            throw new RuntimeException("Principals set does not contain expected elements after addAll");
        }
    }

    // Simple Principal implementation for testing
    private static class PrincipalImpl implements java.security.Principal {
        private final String name;

        PrincipalImpl(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PrincipalImpl)) return false;
            PrincipalImpl that = (PrincipalImpl) o;
            return name.equals(that.name);
        }

        public int hashCode() {
            return name.hashCode();
        }

        public String toString() {
            return "PrincipalImpl[" + name + "]";
        }
    }
}
