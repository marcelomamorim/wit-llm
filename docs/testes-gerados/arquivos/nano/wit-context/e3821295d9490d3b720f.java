/*
 * @test
 * @summary Test for java.util.Collections.forEachRemaining(Consumer<? super E> action)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithEmptyIterator();
    }

    private static void testForEachRemainingWithNullAction() {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            private boolean hasNext = true;
            public boolean hasNext() { return hasNext; }
            public Integer next() {
                if (!hasNext) {
                    throw new NoSuchElementException();
                }
                hasNext = false;
                return 1;
            }
        };
        try {
            iterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        Iterator<Integer> iterator = new Iterator<Integer>() {
            private int count = 0;
            public boolean hasNext() { return count < 3; }
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return count++;
            }
        };
        try {
            iterator.forEachRemaining(consumer);
            if (sb.toString().equals("012")) {
                System.out.println("PASS: forEachRemaining processed elements correctly");
            } else {
                System.out.println("FAIL: Unexpected output: " + sb.toString());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithEmptyIterator() {
        Iterator<Integer> emptyIterator = new Iterator<Integer>() {
            public boolean hasNext() { return false; }
            public Integer next() { throw new NoSuchElementException(); }
        };
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        try {
            emptyIterator.forEachRemaining(consumer);
            if (sb.length() == 0) {
                System.out.println("PASS: forEachRemaining on empty iterator does nothing");
            } else {
                System.out.println("FAIL: Unexpected output on empty iterator: " + sb.toString());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
