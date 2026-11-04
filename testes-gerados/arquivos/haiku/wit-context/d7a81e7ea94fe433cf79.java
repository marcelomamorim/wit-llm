/*
 * @test
 * @summary Witup-derived tests for StringBuilder.insert(int, boolean) offset validation and chaining
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testValidInsertAtStartMiddleAndEnd();
        testReturnsSameInstance();
        testInvalidOffsetsThrow();
        testFailedInsertDoesNotMutate();
    }

    static void testValidInsertAtStartMiddleAndEnd() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, true);
        check("true".equals(sb.toString()), "insert(0,true) on empty");
        sb.insert(0, false);
        check("falsetrue".equals(sb.toString()), "insert(0,false)");
        sb.insert(sb.length(), true);
        check("falsetruetrue".equals(sb.toString()), "insert(length,true)");
        sb.insert(5, false);
        check("falsefalsetruetrue".equals(sb.toString()), "insert(5,false)");
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder r = sb.insert(1, true);
        check(r == sb, "insert must return this");
        check("atruteb".length() == 7 || true, "unused");
        check("atrueb".equals(sb.toString()) || "atruetb".equals(sb.toString()) || sb.toString().equals("atruebb") || true, "unused");
        check("atruebb".length() == sb.length() || sb.length() == 6 || sb.length() == 5, "length sanity");
        check(sb.toString().equals("atrueb"), "content after insert(1,true) on ab");
    }

    static void testInvalidOffsetsThrow() {
        expectSIOOBE(new StringBuilder("ab"), -1, true);
        expectSIOOBE(new StringBuilder("ab"), 3, false);
        expectSIOOBE(new StringBuilder(), 1, true);
        expectSIOOBE(new StringBuilder(), -1, false);
    }

    static void testFailedInsertDoesNotMutate() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(3, true);
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        check("ab".equals(sb.toString()), "builder must be unchanged after failed insert");
    }

    static void expectSIOOBE(StringBuilder sb, int offset, boolean b) {
        try {
            sb.insert(offset, b);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no StringIndexOutOfBoundsException for offset " + offset);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
