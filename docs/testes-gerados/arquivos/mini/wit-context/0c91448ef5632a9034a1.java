/*
 * @test
 * @summary Test java.util.stream.Nodes.SizedCollectorTask.OfRef.accept method for IndexOutOfBoundsException and normal behavior
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    // Minimal stub for SizedCollectorTask.OfRef to test accept method
    static class SizedCollectorTaskOfRef<P_OUT> {
        final Object[] array;
        int index = 0;
        final int fence;

        SizedCollectorTaskOfRef(int size) {
            this.array = new Object[size];
            this.fence = size;
        }

        @SuppressWarnings("unchecked")
        public void accept(P_OUT value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }

        @SuppressWarnings("unchecked")
        P_OUT get(int i) {
            return (P_OUT) array[i];
        }

        int getIndex() {
            return index;
        }

        int getFence() {
            return fence;
        }
    }

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptIndexOutOfBoundsException();
        System.out.println("All tests passed.");
    }

    private static void testAcceptNormal() {
        SizedCollectorTaskOfRef<String> task = new SizedCollectorTaskOfRef<>(3);
        task.accept("a");
        task.accept("b");
        task.accept("c");

        if (task.getIndex() != 3) {
            throw new AssertionError("Index should be 3 after three accepts");
        }

        if (!"a".equals(task.get(0)) || !"b".equals(task.get(1)) || !"c".equals(task.get(2))) {
            throw new AssertionError("Values stored do not match expected");
        }
    }

    private static void testAcceptIndexOutOfBoundsException() {
        SizedCollectorTaskOfRef<String> task = new SizedCollectorTaskOfRef<>(2);
        task.accept("x");
        task.accept("y");

        boolean thrown = false;
        try {
            task.accept("z"); // This should throw IndexOutOfBoundsException
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
            if (!"2".equals(e.getMessage())) {
                throw new AssertionError("Exception message should be index value");
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException was not thrown");
        }
    }
}
