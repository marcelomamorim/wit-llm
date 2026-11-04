/*
 * @test
 * @summary Test for javax.security.auth.Subject.getPrincipals(Class)
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetPrincipalsWithNullClass();
        testGetPrincipalsWithValidClass();
    }

    private static void testGetPrincipalsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPrincipals(null);
            System.out.println("FAIL: Expected NullPointerException for null class");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testGetPrincipalsWithValidClass() {
        Subject subject = new Subject();
        class DummyPrincipal implements Principal {
            @Override
            public String getName() { return "dummy"; }
        }
        Principal p = new DummyPrincipal();
        subject.getPrincipals().add(p);
        Set<Principal> principals = subject.getPrincipals(Principal.class);
        if (principals.contains(p)) {
            System.out.println("PASS: Principal found in set");
        } else {
            System.out.println("FAIL: Principal not found in set");
        }
    }
}
