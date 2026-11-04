/*
 * @test
 * @summary Test ObjectInputStream.get(int index) method for valid and invalid indices
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
    }

    static void testGetWithValidIndex() {
        ObjectInputStream ois = new ObjectInputStream() {
            private final int[] list = {10, 20, 30};
            private final int size = list.length;
            @Override
            public int get(int index) {
                if (index >= size) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return list[index];
            }
        };
        int result = ois.get(1);
        if (result != 20) {
            throw new AssertionError("Expected 20, got " + result);
        }
    }

    static void testGetWithNegativeIndex() {
        ObjectInputStream ois = new ObjectInputStream() {
            private final int[] list = {10, 20, 30};
            private final int size = list.length;
            @Override
            public int get(int index) {
                if (index >= size || index < 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return list[index];
            }
        };
        try {
            ois.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithIndexEqualToSize() {
        ObjectInputStream ois = new ObjectInputStream() {
            private final int[] list = {10, 20, 30};
            private final int size = list.length;
            @Override
            public int get(int index) {
                if (index >= size) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return list[index];
            }
        };
        try {
            ois.get(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
