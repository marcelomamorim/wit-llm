/*
 * @test
 * @summary Test StringBuffer.delete(int, int) for valid and invalid ranges
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testDeleteValidRange();
        testDeleteEmptyRange();
        testDeleteStartNegative();
        testDeleteEndLessThanStart();
        testDeleteEndGreaterThanLength();
    }

    private static void testDeleteValidRange() {
        StringBuffer sb = new StringBuffer("Hello, world!");
        StringBuffer result = sb.delete(7, 12); // deletes "world"
        if (!result.toString().equals("Hello, !")) {
            throw new Error("testDeleteValidRange failed: " + result);
        }
    }

    private static void testDeleteEmptyRange() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.delete(1, 1); // no deletion
        if (!result.toString().equals("abc")) {
            throw new Error("testDeleteEmptyRange failed: " + result);
        }
    }

    private static void testDeleteStartNegative() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.delete(-1, 2);
            throw new Error("testDeleteStartNegative failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteEndLessThanStart() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.delete(2, 1);
            throw new Error("testDeleteEndLessThanStart failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteEndGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.delete(1, 5);
            throw new Error("testDeleteEndGreaterThanLength failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
