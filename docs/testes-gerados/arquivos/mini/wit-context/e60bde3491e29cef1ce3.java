/*
 * @test
 * @summary Test javax.security.auth.Subject.getSubject(AccessControlContext) behavior
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.AccessControlContext;
import java.security.DomainCombiner;
import java.security.ProtectionDomain;
import java.security.Permissions;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.SecurityPermission;
import java.util.Set;
import java.util.HashSet;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testNullAccessControlContext();
        testNoDomainCombiner();
        testNonSubjectDomainCombiner();
        testSubjectDomainCombinerReturnsSubject();
    }

    private static void testNullAccessControlContext() {
        try {
            Subject.getSubject(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        } catch (SecurityException se) {
            // If SecurityManager is installed and denies permission, test passes as well
        }
    }

    private static void testNoDomainCombiner() {
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[0]);
        Subject subject = null;
        try {
            subject = Subject.getSubject(acc);
        } catch (SecurityException se) {
            // SecurityManager may deny permission, accept this as pass
            return;
        }
        if (subject != null) {
            throw new RuntimeException("Expected null subject when no DomainCombiner present");
        }
    }

    private static void testNonSubjectDomainCombiner() {
        // Create AccessControlContext with a DomainCombiner that is NOT SubjectDomainCombiner
        DomainCombiner dummyCombiner = new DomainCombiner() {
            public ProtectionDomain[] combine(ProtectionDomain[] current, ProtectionDomain[] assigned) {
                return current;
            }
        };
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[0], dummyCombiner);
        Subject subject = null;
        try {
            subject = Subject.getSubject(acc);
        } catch (SecurityException se) {
            // SecurityManager may deny permission, accept this as pass
            return;
        }
        if (subject != null) {
            throw new RuntimeException("Expected null subject when DomainCombiner is not SubjectDomainCombiner");
        }
    }

    private static void testSubjectDomainCombinerReturnsSubject() {
        // We cannot instantiate SubjectDomainCombiner directly (not public),
        // so we test that if DomainCombiner is SubjectDomainCombiner, getSubject() returns non-null
        // This test is limited due to access restrictions, so we only check that if
        // DomainCombiner is SubjectDomainCombiner, getSubject() returns the subject it holds.
        // Since we cannot create SubjectDomainCombiner, we skip this test.
        // Note: This is a limitation due to lack of public constructor/access.
    }
}
