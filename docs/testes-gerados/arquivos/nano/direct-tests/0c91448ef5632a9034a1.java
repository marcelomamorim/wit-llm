/* @test    @summary Test Nodes.accept method for normal operation and index bounds    @run main NodesWitupTest */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptIndexOutOfBounds();
    }

    static void testAcceptNormal() {
        class Dummy {
            Object[] array = new Object[5];
            int index = 0;
            void accept(Object value) {
                if (index >= array.length) {
                    throw new IndexOutOfBoundsException();
                }
                array[index++] = value;
            }
        }
        Dummy dummy = new Dummy();
        try {
            for (int i = 0; i < 5; i++) {
                dummy.accept("val" + i);
            }
            boolean allValuesSet = true;
            for (int i = 0; i < 5; i++) {
                if (!"val" + i.equals(dummy.array[i])) {
                    allValuesSet = false;
                    break;
                }
            }
            if (!allValuesSet || dummy.index != 5) {
                throw new AssertionError("Normal accept test failed") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal accept test", e);
        }
    }

    static void testAcceptIndexOutOfBounds() {
        class Dummy {
            Object[] array = new Object[3];
            int index = 3;
            void accept(Object value) {
                if (index >= array.length) {
                    throw new IndexOutOfBoundsException();
                }
                array[index++] = value;
            }
        }
        Dummy dummy = new Dummy();
        boolean exceptionThrown = false;
        try {
            dummy.accept("overflow");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException was not thrown");
        }
    }
}
