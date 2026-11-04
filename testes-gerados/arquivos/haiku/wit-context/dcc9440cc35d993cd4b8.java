/*
 * @test
 * @summary Checks bounds behavior of insert(int, boolean) through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        check("true", new StringBuilder().insert(0, true).toString());
        check("trueab", new StringBuilder("ab").insert(0, true).toString());
        check("afalseb", new StringBuilder("ab").insert(1, false).toString());
        check("abtrue", new StringBuilder("ab").insert(2, true).toString());

        expectStringIndexOutOfBounds(-1, new StringBuilder("ab"));
        expectStringIndexOutOfBounds(3, new StringBuilder("ab"));
        expectStringIndexOutOfBounds(1, new StringBuilder());
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void expectStringIndexOutOfBounds(int offset, StringBuilder sb) {
        try {
            sb.insert(offset, true);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no StringIndexOutOfBoundsException for offset " + offset);
    }
}
