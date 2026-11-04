/*
 * @test
 * @summary Witup-derived tests for insert(int, float) offset validation and float formatting
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        check("1.5", new StringBuilder().insert(0, 1.5f).toString());
        check("a2.0b", new StringBuilder("ab").insert(1, 2.0f).toString());
        check("ab-0.5", new StringBuilder("ab").insert(2, -0.5f).toString());
        check("NaN", new StringBuilder().insert(0, Float.NaN).toString());

        checkThrows(-1, "ab");
        checkThrows(3, "ab");
        checkThrows(1, "");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void checkThrows(int offset, String base) {
        StringBuilder sb = new StringBuilder(base);
        try {
            sb.insert(offset, 1.5f);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
