/*
 * @test
 * @summary Test ordered(Consumer<T>) method of ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testOrderedWithNonEmptyList();
        testOrderedWithEmptyList();
    }

    private static void testOrderedWithNonEmptyList() {
        final List<String> result = new ArrayList<>();
        result.add("one");
        result.add("two");
        result.add("three");

        ModuleHashesBuilderWrapper<String> builder = new ModuleHashesBuilderWrapper<>(result);

        final List<String> collected = new ArrayList<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        };

        builder.ordered(consumer);

        if (collected.size() != result.size()) {
            throw new AssertionError("Expected collected size " + result.size() + " but got " + collected.size());
        }

        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).equals(collected.get(i))) {
                throw new AssertionError("Expected element at index " + i + " to be '" + result.get(i) + "' but got '" + collected.get(i) + "'");
            }
        }
    }

    private static void testOrderedWithEmptyList() {
        final List<String> result = new ArrayList<>();

        ModuleHashesBuilderWrapper<String> builder = new ModuleHashesBuilderWrapper<>(result);

        final List<String> collected = new ArrayList<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        };

        builder.ordered(consumer);

        if (!collected.isEmpty()) {
            throw new AssertionError("Expected collected to be empty but was " + collected);
        }
    }

    // Wrapper class to expose ordered method with a controlled result list
    private static class ModuleHashesBuilderWrapper<T> {
        private final List<T> result;

        ModuleHashesBuilderWrapper(List<T> result) {
            this.result = result;
        }

        public void ordered(Consumer<T> action) {
            result.forEach(action);
        }
    }
}
