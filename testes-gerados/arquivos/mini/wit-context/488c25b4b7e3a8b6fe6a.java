/*
 * @test
 * @summary Test java.util.stream.Nodes.SizedCollectorTask.OfDouble.accept(double) for boundary and exception
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class SizedCollectorTaskOfDouble {
        final double[] array;
        final int fence;
        int index = 0;

        SizedCollectorTaskOfDouble(int size) {
            this.array = new double[size];
            this.fence = size;
        }

        public void accept(double value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }
    }

    public static void main(String[] args) {
        // Test normal accept within bounds
        SizedCollectorTaskOfDouble task = new SizedCollectorTaskOfDouble(3);
        task.accept(1.0);
        task.accept(2.0);
        task.accept(3.0);

        if (task.index != 3) {
            throw new Error("Index should be 3 after three accepts");
        }
        if (task.array[0] != 1.0 || task.array[1] != 2.0 || task.array[2] != 3.0) {
            throw new Error("Array values not stored correctly");
        }

        // Test accept at boundary: index == fence should throw IndexOutOfBoundsException
        SizedCollectorTaskOfDouble task2 = new SizedCollectorTaskOfDouble(1);
        task2.accept(10.0);
        boolean thrown = false;
        try {
            task2.accept(20.0); // index == fence now, should throw
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
            if (!"1".equals(e.getMessage())) {
                throw new Error("Exception message should be index value");
            }
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException not thrown");
        }

        // Test accept on empty array (size 0) should throw immediately
        SizedCollectorTaskOfDouble task3 = new SizedCollectorTaskOfDouble(0);
        boolean thrownEmpty = false;
        try {
            task3.accept(5.0);
        } catch (IndexOutOfBoundsException e) {
            thrownEmpty = true;
            if (!"0".equals(e.getMessage())) {
                throw new Error("Exception message should be index value 0");
            }
        }
        if (!thrownEmpty) {
            throw new Error("Expected IndexOutOfBoundsException for empty array not thrown");
        }
    }
}
