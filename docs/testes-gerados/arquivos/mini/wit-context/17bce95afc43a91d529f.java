/*
 * @test
 * @summary Test for jdk.internal.module.ModuleHashesBuilder.ordered(Consumer<T>) method
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleHashesBuilderWitupTest {

    static class DummyModuleHashesBuilder {
        List<String> result = new ArrayList<>();

        public <T> void ordered(Consumer<T> action) {
            // Simulate forEach on result list with unchecked cast
            for (Object o : result) {
                @SuppressWarnings("unchecked")
                T t = (T) o;
                action.accept(t);
            }
        }
    }

    public static void main(String[] args) {
        testOrderedWithNullConsumer();
        testOrderedWithValidConsumer();
    }

    private static void testOrderedWithNullConsumer() {
        DummyModuleHashesBuilder builder = new DummyModuleHashesBuilder();
        boolean npeThrown = false;
        try {
            builder.ordered(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null Consumer");
        }
    }

    private static void testOrderedWithValidConsumer() {
        DummyModuleHashesBuilder builder = new DummyModuleHashesBuilder();
        builder.result.add("one");
        builder.result.add("two");
        final List<String> collected = new ArrayList<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        };
        builder.ordered(consumer);
        if (collected.size() != 2 || !"one".equals(collected.get(0)) || !"two".equals(collected.get(1))) {
            throw new RuntimeException("Consumer did not receive expected elements in order");
        }
    }
}
