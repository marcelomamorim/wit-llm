/*
 * @test
 * @summary Test for the forEach method in Nodes class
 * @run main NodesWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NodesWitupTest {

    public static void main(String[] args) {
        testForEachWithEmptyConsumer();
        testForEachWithSingleElement();
        testForEachWithMultipleElements();
    }

    static void testForEachWithEmptyConsumer() {
        // Setup: create a dummy node with a forEach method that calls the consumer
        DummyNode<Integer> node = new DummyNode<>();
        List<Integer> result = new ArrayList<>();
        Consumer<Integer> consumer = result::add;
        node.setForEachConsumer(consumer);
        // Act
        node.forEach(consumer);
        // Assert
        if (result.size() != 0) {
            throw new AssertionError("Expected no elements, but got: " + result.size());
        }
    }

    static void testForEachWithSingleElement() {
        // Setup
        DummyNode<Integer> node = new DummyNode<>();
        List<Integer> result = new ArrayList<>();
        Consumer<Integer> consumer = result::add;
        node.setForEachConsumer(consumer);
        node.setElements(List.of(42));
        // Act
        node.forEach(consumer);
        // Assert
        if (result.size() != 1 || result.get(0) != 42) {
            throw new AssertionError("Expected element 42, but got: " + result);
        }
    }

    static void testForEachWithMultipleElements() {
        // Setup
        DummyNode<Integer> node = new DummyNode<>();
        List<Integer> result = new ArrayList<>();
        Consumer<Integer> consumer = result::add;
        node.setForEachConsumer(consumer);
        node.setElements(List.of(1, 2, 3));
        // Act
        node.forEach(consumer);
        // Assert
        if (result.size() != 3 || !result.containsAll(List.of(1, 2, 3))) {
            throw new AssertionError("Expected elements 1, 2, 3, but got: " + result);
        }
    }

    // DummyNode is a stand-in to simulate Nodes behavior for testing
    static class DummyNode<T> {
        private Consumer<T> forEachConsumer;
        private List<T> elements = List.of();

        void setForEachConsumer(Consumer<T> consumer) {
            this.forEachConsumer = consumer;
        }

        void setElements(List<T> elements) {
            this.elements = elements;
        }

        public void forEach(Consumer<? super T> consumer) {
            for (T element : elements) {
                consumer.accept(element);
            }
        }
    }
}
