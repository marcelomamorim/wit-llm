/*
 * @test
 * @summary Test jdk.internal.misc.VM.getSavedProperty(String) for expected behavior and exception
 * @run main VMWitupTest
 */

import jdk.internal.misc.VM;

public class VMWitupTest {
    public static void main(String[] args) {
        testGetSavedPropertyThrowsWhenNotInitialized();
    }

    private static void testGetSavedPropertyThrowsWhenNotInitialized() {
        // savedProps is null initially (per source code), so getSavedProperty should throw
        try {
            VM.getSavedProperty("anyKey");
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not yet initialized")) {
                throw new Error("IllegalStateException thrown with unexpected message: " + msg);
            }
        } catch (Throwable t) {
            throw new Error("Unexpected exception type thrown: " + t);
        }
    }
}
