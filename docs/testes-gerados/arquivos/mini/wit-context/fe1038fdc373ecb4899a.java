/*
 * @test
 * @summary Test CleanerImpl.setCleanerImplAccess behavior including error on second set
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.function.Function;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testSetCleanerImplAccessFirstCallSucceeds();
        testSetCleanerImplAccessSecondCallThrowsInternalError();
        System.out.println("PASS");
    }

    private static void testSetCleanerImplAccessFirstCallSucceeds() {
        // Reset cleanerImplAccess via reflection is not possible, so we rely on JVM fresh start for isolation.
        // This test assumes it is the first call in the JVM lifecycle.
        Function<Cleaner, CleanerImpl> func = new Function<Cleaner, CleanerImpl>() {
            @Override
            public CleanerImpl apply(Cleaner cleaner) {
                return null;
            }
        };
        CleanerImpl.setCleanerImplAccess(func);
        // No exception means success
    }

    private static void testSetCleanerImplAccessSecondCallThrowsInternalError() {
        Function<Cleaner, CleanerImpl> func = new Function<Cleaner, CleanerImpl>() {
            @Override
            public CleanerImpl apply(Cleaner cleaner) {
                return null;
            }
        };
        boolean thrown = false;
        try {
            CleanerImpl.setCleanerImplAccess(func);
        } catch (InternalError e) {
            if ("cleanerImplAccess".equals(e.getMessage())) {
                thrown = true;
            } else {
                throw e; // unexpected message
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected InternalError not thrown on second setCleanerImplAccess call");
        }
    }
}
