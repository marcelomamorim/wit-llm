/*
 * @test
 * @summary Test for CopyOnWriteArrayList.set method to verify it throws UnsupportedOperationException.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testSetThrowsUnsupportedOperationException();
    }

    public static void testSetThrowsUnsupportedOperationException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("initial");
        boolean threw = false;
        try {
            list.set(0, "modified");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
