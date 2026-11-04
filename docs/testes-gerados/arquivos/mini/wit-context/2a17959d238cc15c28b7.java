/*
 * @test
 * @summary Test java.util.stream.Nodes.SizedCollectorTask.OfInt.accept(int) for correct bounds checking and value acceptance
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class SizedCollectorTaskOfInt {
        private final int[] array;
        private final int fence;
        private int index = 0;

        SizedCollectorTaskOfInt(int size) {
            this.array = new int[size];
            this.fence = size;
        }

        public void accept(int value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }

        int getAt(int i) {
            return array[i];
        }

        int getIndex() {
            return index;
        }

        int getFence() {
            return fence;
        }
    }

    public static void main(String[] args) {
        testAcceptWithinBounds();
        testAcceptAtFenceThrows();
        testAcceptBeyondFenceThrows();
    }

    private static void testAcceptWithinBounds() {
        SizedCollectorTaskOfInt task = new SizedCollectorTaskOfInt(3);
        task.accept(10);
        task.accept(20);
        task.accept(30);
        if (task.getIndex() != 3) {
            throw new AssertionError("Index should be 3 after three accepts");
        }
        if (task.getAt(0) != 10 || task.getAt(1) != 20 || task.getAt(2) != 30) {
            throw new AssertionError("Values not stored correctly in array");
        }
    }

    private static void testAcceptAtFenceThrows() {
        SizedCollectorTaskOfInt task = new SizedCollectorTaskOfInt(1);
        task.accept(42);
        try {
            task.accept(43);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown when index == fence");
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().equals("1")) {
                throw new AssertionError("Exception message should be the index value");
            }
        }
    }

    private static void testAcceptBeyondFenceThrows() {
        SizedCollectorTaskOfInt task = new SizedCollectorTaskOfInt(1);
        task.accept(99);
        try {
            task.accept(100);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown when index > fence");
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().equals("1")) {
                throw new AssertionError("Exception message should be the index value");
            }
        }
    }
}
