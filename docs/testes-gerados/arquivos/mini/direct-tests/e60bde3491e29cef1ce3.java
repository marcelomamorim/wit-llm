/*
 * @test
 * @summary Test javax.security.auth.Subject.getSubject(AccessControlContext) behavior
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.AccessControlContext;
import java.security.DomainCombiner;
import java.security.ProtectionDomain;
import java.security.PrivilegedAction;
import java.security.AccessController;

public class SubjectWitupTest {

    static class DummyDomainCombiner implements DomainCombiner {
        public ProtectionDomain[] combine(ProtectionDomain[] current, ProtectionDomain[] assigned) {
            return current;
        }
    }

    static class DummySubjectDomainCombiner extends SubjectDomainCombiner {
        private Subject subject;
        DummySubjectDomainCombiner(Subject subject) {
            super(subject);
            this.subject = subject;
        }
        @Override
        public Subject getSubject() {
            return subject;
        }
    }

    public static void main(String[] args) {
        // Test 1: Passing null AccessControlContext should throw NullPointerException
        try {
            Subject.getSubject(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: AccessControlContext with no DomainCombiner returns null
        AccessControlContext accNoDC = new AccessControlContext(new ProtectionDomain[0]);
        Subject result = Subject.getSubject(accNoDC);
        if (result != null) {
            throw new RuntimeException("Expected null Subject when DomainCombiner is not SubjectDomainCombiner");
        }

        // Test 3: AccessControlContext with DomainCombiner not instance of SubjectDomainCombiner returns null
        AccessControlContext accDummyDC = new AccessControlContext(new ProtectionDomain[0], new DummyDomainCombiner());
        Subject result2 = Subject.getSubject(accDummyDC);
        if (result2 != null) {
            throw new RuntimeException("Expected null Subject when DomainCombiner is not SubjectDomainCombiner");
        }

        // Test 4: AccessControlContext with SubjectDomainCombiner returns the Subject it holds
        Subject subj = new Subject();
        DummySubjectDomainCombiner sdc = new DummySubjectDomainCombiner(subj);
        AccessControlContext accWithSdc = new AccessControlContext(new ProtectionDomain[0], sdc);
        Subject result3 = Subject.getSubject(accWithSdc);
        if (result3 != subj) {
            throw new RuntimeException("Expected returned Subject to be the one held by SubjectDomainCombiner");
        }
    }
}
