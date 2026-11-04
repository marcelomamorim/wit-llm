/* @test    @summary Test setCleanerImplAccess method for CleanerImpl class    @run main CleanerImplWitupTest */

import java.lang.ref.Cleaner;
import java.util.function.Function;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testSetCleanerImplAccess();
    }

    public static void testSetCleanerImplAccess() {
        // Reset static variable to null for test isolation
        try {
            java.lang.reflect.Field field = CleanerImpl.class.getDeclaredField("cleanerImplAccess");
            field.setAccessible(true);
            field.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // Define a dummy Function to set
        Function<Cleaner, CleanerImpl> dummyFunction = new Function<Cleaner, CleanerImpl>() {
            @Override
            public CleanerImpl apply(Cleaner cleaner) {
                return null;
            }
        };
        // Call setCleanerImplAccess to set the static variable
        CleanerImpl.setCleanerImplAccess(dummyFunction);
        // Verify that the static variable is set correctly
        try {
            java.lang.reflect.Field field = CleanerImpl.class.getDeclaredField("cleanerImplAccess");
            field.setAccessible(true);
            Object value = field.get(null);
            if (value != dummyFunction) {
                throw new AssertionError("cleanerImplAccess not set correctly");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // Attempt to set again, should throw InternalError
        boolean exceptionThrown = false;
        try {
            CleanerImpl.setCleanerImplAccess(dummyFunction);
        } catch (InternalError e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected InternalError when setting cleanerImplAccess twice");
        }
    }
}
