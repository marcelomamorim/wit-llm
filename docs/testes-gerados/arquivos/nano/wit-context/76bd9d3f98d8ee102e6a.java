/*
 * @test
 * @summary Testa o método remove() de java.util.Collections
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
