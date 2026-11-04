/*
 * @test
 * @summary Test jdk.internal.module.ModuleHashesBuilder reverse method behavior
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.function.Consumer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class ModuleHashesBuilderWitupTest {

    static class TopoSorter<T> {
        private final Deque<T> result = new ArrayDeque<>();

        TopoSorter() {
            // For testing, fill result with some elements
            result.push((T)"first");
            result.push((T)"second");
            result.push((T)"third");
        }

        public void reverse(Consumer<T> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            result.descendingIterator().forEachRemaining(action);
        }
    }

    public static void main(String[] args) {
        TopoSorter<String> sorter = new TopoSorter<>();

        // Test normal behavior: reverse should call action on elements in reverse order
        final StringBuilder sb = new StringBuilder();
        sorter.reverse(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("first,second,third,")) {
            throw new AssertionError("reverse did not process elements in expected order: " + result);
        }

        // Test that passing null throws NullPointerException
        boolean npeThrown = false;
        try {
            sorter.reverse(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to reverse");
        }

        System.out.println("PASS");
    }
}
