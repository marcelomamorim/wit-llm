/*
 * @test
 * @summary Test StringBuilder.insert(int, Object) for exception and normal behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertNullObject();
        testInsertValidObject();
        testInsertIndexOutOfBoundsNegative();
        testInsertIndexOutOfBoundsTooLarge();
    }

    private static void testInsertNullObject() {
        StringBuilder sb = new StringBuilder("hello");
        // Inserting null object should insert "null" string
        sb.insert(0, (Object) null);
        if (!sb.toString().equals("nullhello")) {
            throw new Error("Failed to insert null object as string 'null'");
        }
    }

    private static void testInsertValidObject() {
        StringBuilder sb = new StringBuilder("world");
        Object obj = Integer.valueOf(123);
        sb.insert(5, obj); // insert at end
        if (!sb.toString().equals("world123")) {
            throw new Error("Failed to insert object string representation at end");
        }
        sb.insert(0, obj); // insert at start
        if (!sb.toString().equals("123world123")) {
            throw new Error("Failed to insert object string representation at start");
        }
    }

    private static void testInsertIndexOutOfBoundsNegative() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, "fail");
            throw new Error("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertIndexOutOfBoundsTooLarge() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(sb.length() + 1, "fail");
            throw new Error("Expected StringIndexOutOfBoundsException for index > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
