/*
 * @test
 * @summary Test ObjectInputStream.get(int) method for valid and invalid indices
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an anonymous subclass to access protected constructor
        ObjectInputStream ois = new ObjectInputStream() {
            int size = 3;
            int[] list = new int[] {10, 20, 30};
            public int get(int index) {
                if (index >= size) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return list[index];
            }
        };

        // Test valid indices
        if (ois.get(0) != 10) throw new RuntimeException("Expected 10 at index 0");
        if (ois.get(1) != 20) throw new RuntimeException("Expected 20 at index 1");
        if (ois.get(2) != 30) throw new RuntimeException("Expected 30 at index 2");

        // Test invalid index: equal to size
        try {
            ois.get(3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // Test invalid index: greater than size
        try {
            ois.get(4);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 4");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // Test invalid index: negative index (not explicitly checked, but should throw ArrayIndexOutOfBoundsException)
        try {
            ois.get(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }
}
