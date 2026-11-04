/*
 * @test
 * @summary Witup-generated checks for Subject.getSubject(AccessControlContext)
 * @run main SubjectWitupTest
 */

import java.security.AccessControlContext;
import java.security.ProtectionDomain;
import javax.security.auth.Subject;
import javax.security.auth.SubjectDomainCombiner;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testNullContextThrowsNPE();
        testContextWithoutSubjectDomainCombinerReturnsNull();
        testContextWithSubjectDomainCombinerReturnsSubject();
    }

    static void testNullContextThrowsNPE() {
        boolean thrown = false;
        try {
            Subject.getSubject(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null context");
        }
    }

    static void testContextWithoutSubjectDomainCombinerReturnsNull() {
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[0]);
        Subject result = Subject.getSubject(acc);
        if (result != null) {
            throw new RuntimeException("expected null when no SubjectDomainCombiner is present");
        }
    }

    static void testContextWithSubjectDomainCombinerReturnsSubject() {
        Subject subject = new Subject();
        AccessControlContext base = new AccessControlContext(new ProtectionDomain[0]);
        AccessControlContext acc = new AccessControlContext(base, new SubjectDomainCombiner(subject));
        Subject result = Subject.getSubject(acc);
        if (result != subject) {
            throw new RuntimeException("expected the Subject bound to the SubjectDomainCombiner");
        }
    }
}
