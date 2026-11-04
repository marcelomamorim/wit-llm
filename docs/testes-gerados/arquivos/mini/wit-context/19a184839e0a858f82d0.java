/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator.first() method behavior
 * @run main StringTokenIteratorWitupTest
 */

public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        testFirstWithNormalDelimiter();
        testFirstWithMultiCharDelimiters();
        testFirstWithEmptyText();
        testFirstWithDelimiterAtStart();
    }

    private static void testFirstWithNormalDelimiter() {
        String text = "a,b,c";
        String dlms = ",";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a' but got '" + first + "'");
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0 but got " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Iterator should not be done after first()");
        }
    }

    private static void testFirstWithMultiCharDelimiters() {
        String text = "a|b,c";
        String dlms = "|,";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a' but got '" + first + "'");
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0 but got " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Iterator should not be done after first()");
        }
    }

    private static void testFirstWithEmptyText() {
        String text = "";
        String dlms = ",";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);
        String first = it.first();
        if (first == null || !"".equals(first)) {
            throw new RuntimeException("Expected first token '' (empty string) but got '" + first + "'");
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0 but got " + it.currentStart());
        }
        if (!it.isDone()) {
            throw new RuntimeException("Iterator should be done for empty text");
        }
    }

    private static void testFirstWithDelimiterAtStart() {
        String text = ",abc";
        String dlms = ",";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);
        String first = it.first();
        if (!"".equals(first)) {
            throw new RuntimeException("Expected first token '' (empty string) but got '" + first + "'");
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0 but got " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Iterator should not be done after first() even if first token is empty");
        }
    }
}
