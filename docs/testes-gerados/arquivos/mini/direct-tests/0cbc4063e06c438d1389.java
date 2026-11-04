/*
 * @test
 * @summary Test java.util.ArrayList.tryAdvance(Consumer) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNormal();
        testTryAdvanceNullConsumer();
        testTryAdvanceConcurrentModification();
        testTryAdvanceEmptyList();
    }

    static void testTryAdvanceNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        final StringBuilder sb = new StringBuilder();
        java.util.Spliterator<String> spliterator = list.spliterator();

        boolean advanced1 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced1) throw new RuntimeException("Expected tryAdvance to return true on first element");
        if (!sb.toString().equals("a")) throw new RuntimeException("Expected consumer to accept 'a'");

        boolean advanced2 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced2) throw new RuntimeException("Expected tryAdvance to return true on second element");
        if (!sb.toString().equals("ab")) throw new RuntimeException("Expected consumer to accept 'b'");

        boolean advanced3 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (advanced3) throw new RuntimeException("Expected tryAdvance to return false after all elements consumed");
    }

    static void testTryAdvanceNullConsumer() {
        ArrayList<String> list = new ArrayList<>();
        java.util.Spliterator<String> spliterator = list.spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testTryAdvanceConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        java.util.Spliterator<String> spliterator = list.spliterator();

        // Modify list structurally after spliterator creation to cause ConcurrentModificationException
        list.add("y");

        try {
            spliterator.tryAdvance(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new RuntimeException("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException cme) {
            // expected
        }
    }

    static void testTryAdvanceEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        java.util.Spliterator<String> spliterator = list.spliterator();

        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called on empty list");
            }
        });

        if (advanced) throw new RuntimeException("Expected tryAdvance to return false on empty list");
    }
}
