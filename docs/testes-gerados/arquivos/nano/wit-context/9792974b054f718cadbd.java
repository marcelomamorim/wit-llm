/*
 * @test
 * @summary Tests the set method of InsnList for null remove condition.
 * @run main InsnListWitupTest
 */

import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        testSetWithNullRemove();
    }

    private static void testSetWithNullRemove() {
        InsnList list = new InsnList();
        Object dummyObject = new Object();
        try {
            list.set(dummyObject);
            // Expecting IllegalStateException to be thrown
            System.out.println("Test failed: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
