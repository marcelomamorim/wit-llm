/*
 * @test
 * @summary Test StringBuilder.deleteCharAt(int) for index bounds and normal behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtInvalidIndexNegative();
        testDeleteCharAtInvalidIndexTooLarge();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuilder sb = new StringBuilder("abc");
        sb.deleteCharAt(1);
        String result = sb.toString();
        if (!"ac".equals(result)) {
            throw new RuntimeException("Failed: deleteCharAt valid index did not remove correct char, got: " + result);
        }
    }

    private static void testDeleteCharAtInvalidIndexNegative() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.deleteCharAt(-1);
            throw new RuntimeException("Failed: deleteCharAt with negative index did not throw StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtInvalidIndexTooLarge() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.deleteCharAt(3); // index == length
            throw new RuntimeException("Failed: deleteCharAt with index equal to length did not throw StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
