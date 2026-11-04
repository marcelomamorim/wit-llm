/*
 * @test
 * @summary Test getPrincipals method of Subject class for various Principal types
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetPrincipalsWithExistingPrincipals();
        testGetPrincipalsWithEmptyPrincipals();
        testGetPrincipalsWithNullClass();
    }

    static void testGetPrincipalsWithExistingPrincipals() {
        // Create a Principal implementation
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "TestPrincipal";
            }
        };
        // Create a Subject with the Principal
        Set<Principal> principals = new HashSet<>();
        principals.add(principal);
        Subject subject = new Subject();
        subject.getPrincipals().addAll(principals);
        // Retrieve principals of the specific class
        Set<Principal> result = subject.getPrincipals(principal.getClass());
        // Verify that the result contains the principal
        if (!result.contains(principal)) {
            throw new AssertionError("Principal not found in getPrincipals result") ;
        }
        // Verify that the result is a Set
        if (!(result instanceof Set)) {
            throw new AssertionError("Result is not a Set") ;
        }
        System.out.println("testGetPrincipalsWithExistingPrincipals passed.");
    }

    static void testGetPrincipalsWithEmptyPrincipals() {
        // Create a Subject with no principals
        Subject subject = new Subject();
        // Retrieve principals of a class with no principals
        Set<Principal> result = subject.getPrincipals(Principal.class);
        // Verify that the result is empty
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty set, but got: " + result);
        }
        System.out.println("testGetPrincipalsWithEmptyPrincipals passed.");
    }

    static void testGetPrincipalsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrincipals(null);
            throw new AssertionError("Expected NullPointerException for null class") ;
        } catch (NullPointerException e) {
            // Expected exception
        }
        System.out.println("testGetPrincipalsWithNullClass passed.");
    }
}
