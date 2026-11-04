/*
 * @test    @summary Test for CopyOnWriteArrayList.set method to ensure it throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testSetMethodThrowsException();
    }

    public static void testSetMethodThrowsException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("element");
        boolean exceptionThrown = false;
        try {
            list.set("newElement");
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by set()");
        }
    }
}
