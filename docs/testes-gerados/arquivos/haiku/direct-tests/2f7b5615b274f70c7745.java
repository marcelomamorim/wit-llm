/*
 * @test
 * @summary Verify StringBuilder.insert(int, float) inserts the String.valueOf(float) form at the given offset and returns this builder
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertIntoEmptyBuilder();
        testInsertAtStart();
        testInsertAtMiddle();
        testInsertAtEnd();
        testInsertReturnsSameInstance();
        testInsertSpecialFloatValues();
        testInsertWholeNumberFloat();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 1.5f);
        check(sb.toString().equals("1.5"), "expected \"1.5\" but was \"" + sb + "\"");
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, 1.5f);
        check(sb.toString().equals("1.5ab"), "expected \"1.5ab\" but was \"" + sb + "\"");
    }

    static void testInsertAtMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, 1.5f);
        check(sb.toString().equals("a1.5b"), "expected \"a1.5b\" but was \"" + sb + "\"");
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, 1.5f);
        check(sb.toString().equals("ab1.5"), "expected \"ab1.5\" but was \"" + sb + "\"");
    }

    static void testInsertReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder result = sb.insert(0, 0.1f);
        check(result == sb, "insert(int, float) must return this builder");
        check(sb.toString().equals("0.1x"), "expected \"0.1x\" but was \"" + sb + "\"");
    }

    static void testInsertSpecialFloatValues() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, Float.NaN);
        check(sb.toString().equals("NaN"), "expected \"NaN\" but was \"" + sb + "\"");

        sb.setLength(0);
        sb.insert(0, Float.POSITIVE_INFINITY);
        check(sb.toString().equals("Infinity"), "expected \"Infinity\" but was \"" + sb + "\"");
    }

    static void testInsertWholeNumberFloat() {
        StringBuilder sb = new StringBuilder("[]");
        sb.insert(1, 100f);
        check(sb.toString().equals("[100.0]"), "expected \"[100.0]\" but was \"" + sb + "\"");
    }
}
