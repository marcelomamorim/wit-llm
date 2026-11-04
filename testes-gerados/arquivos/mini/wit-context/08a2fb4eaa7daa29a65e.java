/*
 * @test
 * @summary Test StringBuffer.deleteCharAt(int) for correct exception and normal behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtInvalidIndexNegative();
        testDeleteCharAtInvalidIndexTooLarge();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        // Delete char at index 1 ('e'), expect "hllo"
        sb.deleteCharAt(1);
        String result = sb.toString();
        if (!"hllo".equals(result)) {
            throw new AssertionError("Expected 'hllo' but got '" + result + "'");
        }
    }

    private static void testDeleteCharAtInvalidIndexNegative() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.deleteCharAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testDeleteCharAtInvalidIndexTooLarge() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.deleteCharAt(3); // index == length, invalid
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 3");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
