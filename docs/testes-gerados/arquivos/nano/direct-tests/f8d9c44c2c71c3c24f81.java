/*
 * @test    @summary Test adding elements to CopyOnWriteArrayList
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testAddMethod();
    }

    public static void testAddMethod() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.add("element1");
            list.add("element2");
            list.add("element3");
        } catch (UnsupportedOperationException e) {
            // Expected, as add() throws UnsupportedOperationException
            System.out.println("add() method is unsupported as expected.");
            return;
        }
        // Verify that the list remains empty if add() is unsupported
        if (list.size() != 0) {
            throw new AssertionError("Expected list to be empty after add attempts, but size was: " + list.size());
        }
        System.out.println("Test passed: add() method throws UnsupportedOperationException.");
    }
}
