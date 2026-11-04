/*
 * @test
 * @summary Test setCleanerImplAccess method behavior
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.function.Function;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testSetCleanerImplAccessFirstCall();
        testSetCleanerImplAccessSecondCallThrows();
    }

    private static void testSetCleanerImplAccessFirstCall() {
        // Reset cleanerImplAccess via reflection to null for test isolation
        // Reflection is needed because cleanerImplAccess is private static
        try {
            java.lang.reflect.Field f = CleanerImpl.class.getDeclaredField("cleanerImplAccess");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset cleanerImplAccess", e);
        }

        Function<Cleaner, CleanerImpl> func = new Function<Cleaner, CleanerImpl>() {
            @Override
            public CleanerImpl apply(Cleaner c) {
                return new CleanerImpl();
            }
        };

        CleanerImpl.setCleanerImplAccess(func);

        // Verify that the field was set (indirectly via getCleanerImpl)
        Cleaner cleaner = Cleaner.create();
        CleanerImpl impl = CleanerImpl.getCleanerImpl(cleaner);
        if (impl == null) {
            throw new AssertionError("CleanerImpl.getCleanerImpl returned null after setCleanerImplAccess");
        }
    }

    private static void testSetCleanerImplAccessSecondCallThrows() {
        Function<Cleaner, CleanerImpl> func2 = new Function<Cleaner, CleanerImpl>() {
            @Override
            public CleanerImpl apply(Cleaner c) {
                return new CleanerImpl();
            }
        };

        try {
            CleanerImpl.setCleanerImplAccess(func2);
            throw new AssertionError("Expected InternalError not thrown on second setCleanerImplAccess call");
        } catch (InternalError e) {
            if (!"cleanerImplAccess".equals(e.getMessage())) {
                throw new AssertionError("InternalError thrown with unexpected message: " + e.getMessage());
            }
        }
    }
}
