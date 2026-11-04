/*
 * @test
 * @summary Test Cleaner.register method for basic functionality
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.Objects;

public class CleanerWitupTest {

    public static void main(String[] args) {
        testRegisterWithValidObjectAndRunnable();
        testRegisterWithNullObject();
        testRegisterWithNullRunnable();
    }

    private static void testRegisterWithValidObjectAndRunnable() {
        Cleaner cleaner = Cleaner.create();
        Object obj = new Object();
        boolean[] flag = {false};
        Runnable action = () -> flag[0] = true;
        Cleaner.Cleanable cleanable = cleaner.register(obj, action);
        // Remove strong reference to obj to make it phantom reachable
        obj = null;
        System.gc();
        // Wait briefly to allow GC to process
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }
        // Explicitly invoke clean to simulate cleanup
        cleanable.clean();
        assert flag[0] : "Cleaning action was not executed";
    }

    private static void testRegisterWithNullObject() {
        Cleaner cleaner = Cleaner.create();
        Runnable action = () -> {};
        try {
            cleaner.register(null, action);
            assert false : "Expected NullPointerException for null object";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("obj") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testRegisterWithNullRunnable() {
        Cleaner cleaner = Cleaner.create();
        Object obj = new Object();
        try {
            cleaner.register(obj, null);
            assert false : "Expected NullPointerException for null Runnable";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("action") : "Unexpected message: " + e.getMessage();
        }
    }
}
