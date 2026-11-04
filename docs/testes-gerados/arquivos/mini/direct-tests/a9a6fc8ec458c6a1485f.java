/*
 * @test
 * @summary Test Collections.get(int) method that always throws IndexOutOfBoundsException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testGetThrowsIndexOutOfBoundsException();
    }

    public void testGetThrowsIndexOutOfBoundsException() {
        try {
            new Collections() {
                @Override
                public Object get(int index) {
                    throw new IndexOutOfBoundsException("Index: " + index);
                }
            }.get(0);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().equals("Index: 0")) {
                throw new RuntimeException("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
