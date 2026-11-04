/*
 * @test
 * @summary Basic tests for StringBuilder.insert(int, String)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertBasic();
        testInsertEmptyString();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertNullString();
    }

    private static void testInsertBasic() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        StringBuilder result = sb.insert(5, " ");
        if (!"Hello World".equals(sb.toString())) {
            throw new Error("testInsertBasic failed: expected 'Hello World' but got '" + sb.toString() + "'");
        }
        if (result != sb) {
            throw new Error("testInsertBasic failed: insert did not return this");
        }
    }

    private static void testInsertEmptyString() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(1, "");
        if (!"abc".equals(sb.toString())) {
            throw new Error("testInsertEmptyString failed: expected 'abc' but got '" + sb.toString() + "'");
        }
        if (result != sb) {
            throw new Error("testInsertEmptyString failed: insert did not return this");
        }
    }

    private static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("world");
        sb.insert(0, "hello ");
        if (!"hello world".equals(sb.toString())) {
            throw new Error("testInsertAtStart failed: expected 'hello world' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("hello");
        sb.insert(sb.length(), " world");
        if (!"hello world".equals(sb.toString())) {
            throw new Error("testInsertAtEnd failed: expected 'hello world' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertNullString() {
        StringBuilder sb = new StringBuilder("abc");
        boolean npeThrown = false;
        try {
            sb.insert(1, (String) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("testInsertNullString failed: expected NullPointerException when inserting null string");
        }
    }
}
