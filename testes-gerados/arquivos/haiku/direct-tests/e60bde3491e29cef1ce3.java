/*
 * @test
 * @summary Verifies Subject.getSubject(AccessControlContext) observable behavior
 * @run main SubjectWitupTest
 */

import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testNullContextThrowsNullPointerException();
        testContextWithoutSubjectDomainCombinerReturnsNull();
        testEmptyProtectionDomainContextReturnsNull();
        testSubjectReturnedInsideDoAs();
    }

    static void testNullContextThrowsNullPointerException() {
        try {
            Subject.getSubject(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null context");
    }

    static void testContextWithoutSubjectDomainCombinerReturnsNull() {
        AccessControlContext acc = AccessController.getContext();
        Subject s = Subject.getSubject(acc);
        if (s != null) {
            throw new RuntimeException("Expected null subject for context without SubjectDomainCombiner");
        }
    }

    static void testEmptyProtectionDomainContextReturnsNull() {
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[0]);
        Subject s = Subject.getSubject(acc);
        if (s != null) {
            throw new RuntimeException("Expected null subject for context with no combiner");
        }
    }

    static void testSubjectReturnedInsideDoAs() {
        final Subject expected = new Subject();
        Subject result = Subject.doAs(expected, new PrivilegedAction<Subject>() {
            public Subject run() {
                return Subject.getSubject(AccessController.getContext());
            }
        });
        if (result != expected) {
            throw new RuntimeException("Expected the Subject used in doAs to be returned by getSubject");
        }
    }
}
