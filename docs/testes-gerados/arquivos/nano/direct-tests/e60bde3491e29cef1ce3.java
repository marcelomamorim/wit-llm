/*
 * @test
 * @summary Test for Subject.getSubject(AccessControlContext)
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.*;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetSubjectWithValidContext();
        testGetSubjectWithNullContext();
        testGetSubjectWithDifferentDomainCombiner();
    }

    static void testGetSubjectWithValidContext() {
        // Create a Subject with a Principal
        Principal principal = new Principal() {
            public String getName() { return "TestPrincipal"; }
        };
        Subject subject = new Subject();
        subject.getPrincipals().add(principal);

        // Create a DomainCombiner that returns the Subject
        DomainCombiner dc = new DomainCombiner() {
            public DomainCombiner combine(ProtectionDomain[] currentDomains, DomainCombiner parent) {
                return this;
            }
            public Subject getSubject() {
                return subject;
            }
        };

        // Create an AccessControlContext with the custom DomainCombiner
        AccessControlContext acc = new AccessControlContext(
            new ProtectionDomain[] {
                new ProtectionDomain(null, null, null, null) {
                    public DomainCombiner getDomainCombiner() {
                        return dc;
                    }
                }
            }
        );

        // Retrieve the Subject using getSubject
        Subject result = Subject.getSubject(acc);
        assert result != null : "Expected non-null Subject";
        assert result.getPrincipals().contains(principal) : "Principal not found in Subject";
        System.out.println("testGetSubjectWithValidContext passed.");
    }

    static void testGetSubjectWithNullContext() {
        try {
            Subject.getSubject(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            System.out.println("testGetSubjectWithNullContext passed.");
        }
    }

    static void testGetSubjectWithDifferentDomainCombiner() {
        // Create a DomainCombiner that does not return a Subject
        DomainCombiner dc = new DomainCombiner() {
            public DomainCombiner combine(ProtectionDomain[] currentDomains, DomainCombiner parent) {
                return this;
            }
            public Subject getSubject() {
                return null;
            }
        };

        // Create an AccessControlContext with the custom DomainCombiner
        AccessControlContext acc = new AccessControlContext(
            new ProtectionDomain[] {
                new ProtectionDomain(null, null, null, null) {
                    public DomainCombiner getDomainCombiner() {
                        return dc;
                    }
                }
            }
        );

        // Retrieve the Subject, expecting null
        Subject result = Subject.getSubject(acc);
        assert result == null : "Expected null Subject";
        System.out.println("testGetSubjectWithDifferentDomainCombiner passed.");
    }
}
