/*
 * @test
 * @summary Test addAll method of javax.security.auth.Subject
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testAddAllWithEmptyCollection();
        testAddAllWithSinglePrincipal();
        testAddAllWithMultiplePrincipals();
        testAddAllWithNullCollection();
    }

    static void testAddAllWithEmptyCollection() {
        Subject subject = new Subject();
        boolean changed = subject.addAll(Collections.emptySet());
        assert !changed : "Adding empty collection should not change Subject";
        assert subject.getPrincipals().isEmpty() : "Principals should be empty after adding empty collection";
    }

    static void testAddAllWithSinglePrincipal() {
        Subject subject = new Subject();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Principal1";
            }
        };
        Set<Principal> principals = new HashSet<>();
        principals.add(principal);
        boolean changed = subject.addAll(principals);
        assert changed : "Adding new principal should return true";
        assert subject.getPrincipals().contains(principal) : "Principals should contain the added principal";
    }

    static void testAddAllWithMultiplePrincipals() {
        Subject subject = new Subject();
        Principal p1 = new Principal() {
            @Override
            public String getName() {
                return "Principal1";
            }
        };
        Principal p2 = new Principal() {
            @Override
            public String getName() {
                return "Principal2";
            }
        };
        Set<Principal> principals = new HashSet<>();
        principals.add(p1);
        principals.add(p2);
        boolean changed = subject.addAll(principals);
        assert changed : "Adding multiple principals should return true";
        assert subject.getPrincipals().contains(p1) : "Principals should contain Principal1";
        assert subject.getPrincipals().contains(p2) : "Principals should contain Principal2";
    }

    static void testAddAllWithNullCollection() {
        Subject subject = new Subject();
        try {
            subject.addAll(null);
            assert false : "addAll with null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
