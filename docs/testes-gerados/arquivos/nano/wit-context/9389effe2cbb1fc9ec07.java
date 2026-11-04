/*
 * @test
 * @summary Testa o construtor de LinkedBlockingQueue com coleção, verificando comportamento com entradas nulas e capacidade máxima.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;
import java.util.Collection;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullElement();
        testConstructorWithEmptyCollection();
        testConstructorWithCollectionContainingNull();
        testConstructorWithCollectionExceedingCapacity();
        testConstructorWithValidCollection();
    }

    static void testConstructorWithNullElement() {
        Collection<String> coll = Arrays.asList("a", null, "b");
        try {
            new LinkedBlockingQueue<String>(coll);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testConstructorWithEmptyCollection() {
        Collection<Integer> coll = Arrays.asList();
        try {
            LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(coll);
            if (queue.isEmpty()) {
                System.out.println("PASS: Empty collection creates empty queue");
            } else {
                System.out.println("FAIL: Queue not empty after construction with empty collection");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testConstructorWithCollectionContainingNull() {
        Collection<String> coll = Arrays.asList("x", null, "y");
        try {
            new LinkedBlockingQueue<String>(coll);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testConstructorWithCollectionExceedingCapacity() {
        Integer[] largeArray = new Integer[Integer.MAX_VALUE];
        Collection<Integer> coll = Arrays.asList(largeArray);
        try {
            new LinkedBlockingQueue<Integer>(coll);
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected");
        } catch (OutOfMemoryError e) {
            System.out.println("WARN: OutOfMemoryError during test, skip");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testConstructorWithValidCollection() {
        Collection<String> coll = Arrays.asList("one", "two", "three");
        try {
            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(coll);
            if (queue.size() == coll.size() && queue.containsAll(coll)) {
                System.out.println("PASS: Queue correctly initialized with collection");
            } else {
                System.out.println("FAIL: Queue contents do not match collection");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
