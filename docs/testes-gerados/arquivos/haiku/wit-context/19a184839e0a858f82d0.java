/*
 * @test
 * @summary Witup generated behavioral tests for StringTokenIterator.first()
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */
public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        check("abc def", " ", "abc");
        check("", ",", "");
        check(",abc", ",", "");
        check("a;b,c", ",;", "a");
        check("single", ",", "single");

        StringTokenIterator it = new StringTokenIterator("x,y,z", ",");
        it.next();
        String first = it.first();
        if (!"x".equals(first)) {
            throw new RuntimeException("expected x after first() but got " + first);
        }
        if (it.currentStart() != 0 || it.currentEnd() != 1) {
            throw new RuntimeException("unexpected bounds after first(): "
                    + it.currentStart() + "," + it.currentEnd());
        }
        if (it.isDone()) {
            throw new RuntimeException("iterator should not be done after first()");
        }
    }

    private static void check(String text, String dlms, String expected) {
        StringTokenIterator it = new StringTokenIterator(text, dlms);
        String got = it.first();
        if (expected == null ? got != null : !expected.equals(got)) {
            throw new RuntimeException("first() for text=\"" + text + "\" dlms=\"" + dlms
                    + "\" expected \"" + expected + "\" but got \"" + got + "\"");
        }
    }
}
