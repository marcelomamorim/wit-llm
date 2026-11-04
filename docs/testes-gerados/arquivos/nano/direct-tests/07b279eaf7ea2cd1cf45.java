/*
 * @test
 * @summary Test the release() method of java.util.concurrent.Semaphore
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testReleaseIncreasesPermits();
        testMultipleReleasesIncreasePermits();
    }

    private static void testReleaseIncreasesPermits() {
        Semaphore semaphore = new Semaphore(0);
        int permitsBefore = getPermitsCount(semaphore);
        semaphore.release();
        int permitsAfter = getPermitsCount(semaphore);
        if (permitsAfter != permitsBefore + 1) {
            throw new AssertionError("Expected permits after release to be " + (permitsBefore + 1) + " but was " + permitsAfter);
        }
    }

    private static void testMultipleReleasesIncreasePermits() {
        Semaphore semaphore = new Semaphore(0);
        int initialPermits = getPermitsCount(semaphore);
        semaphore.release();
        semaphore.release();
        int afterReleases = getPermitsCount(semaphore);
        if (afterReleases != initialPermits + 2) {
            throw new AssertionError("Expected permits after two releases to be " + (initialPermits + 2) + " but was " + afterReleases);
        }
    }

    private static int getPermitsCount(Semaphore semaphore) {
        try {
            java.lang.reflect.Field syncField = semaphore.getClass().getDeclaredField("sync");
            syncField.setAccessible(true);
            Object syncObject = syncField.get(semaphore);
            java.lang.reflect.Method getPermitsMethod = syncObject.getClass().getDeclaredMethod("getPermits");
            getPermitsMethod.setAccessible(true);
            return (Integer) getPermitsMethod.invoke(syncObject);
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Unable to access permits count", e);
        }
    }
}
