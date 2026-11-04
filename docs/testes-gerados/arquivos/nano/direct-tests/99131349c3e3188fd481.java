/* @test    @summary Test StringBuilder.insert(int, String) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertBasic();
        testInsertWithEmptyString();
        testInsertWithNullString();
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertWithLargeOffset();
    }

    static void testInsertBasic() {
        StringBuilder sb = new StringBuilder("Hello World");
        sb.insert(6, "Beautiful ");
        assert sb.toString().equals("Hello Beautiful World") : "Basic insert failed";
    }

    static void testInsertWithEmptyString() {
        StringBuilder sb = new StringBuilder("Test");
        sb.insert(2, "");
        assert sb.toString().equals("TeTest") : "Insert empty string failed";
    }

    static void testInsertWithNullString() {
        StringBuilder sb = new StringBuilder("NullTest");
        try {
            sb.insert(4, (String) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInsertAtBeginning() {
        StringBuilder sb = new StringBuilder("World");
        sb.insert(0, "Hello ");
        assert sb.toString().equals("Hello World") : "Insert at beginning failed";
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(sb.length(), " World");
        assert sb.toString().equals("Hello World") : "Insert at end failed";
    }

    static void testInsertWithLargeOffset() {
        StringBuilder sb = new StringBuilder("Test");
        try {
            sb.insert(10, "Fail");
            assert false : "Expected StringIndexOutOfBoundsException";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
