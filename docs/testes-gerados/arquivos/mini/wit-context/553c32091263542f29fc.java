/*
 * @test
 * @summary Test java.util.ArrayList.removeIf(Predicate) for expected behavior and exceptions
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfBasic();
        testRemoveIfNullPredicate();
        testRemoveIfConcurrentModification();
    }

    private static void testRemoveIfBasic() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Predicate<String> removeB = new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s);
            }
        };

        boolean removed = list.removeIf(removeB);
        if (!removed) {
            throw new RuntimeException("Expected removeIf to return true when element removed");
        }
        if (list.size() != 2 || list.contains("b")) {
            throw new RuntimeException("Element 'b' should be removed from list");
        }

        // Remove elements that do not exist, expect false
        Predicate<String> removeZ = new Predicate<String>() {
            public boolean test(String s) {
                return "z".equals(s);
            }
        };
        boolean removedNone = list.removeIf(removeZ);
        if (removedNone) {
            throw new RuntimeException("Expected removeIf to return false when no element removed");
        }
    }

    private static void testRemoveIfNullPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException when predicate is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfConcurrentModification() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // Create a thread that modifies the list concurrently
        Thread modifier = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50); // Wait a bit to let main thread start removeIf
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                list.add("z"); // Structural modification
            }
        });

        modifier.start();

        try {
            // Use a predicate that sleeps to increase chance of concurrent modification
            boolean result = list.removeIf(new Predicate<String>() {
                public boolean test(String s) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return false;
                }
            });
            // If no exception, test passes silently
        } catch (ConcurrentModificationException expected) {
            // Expected concurrent modification exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e);
        } finally {
            try {
                modifier.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
