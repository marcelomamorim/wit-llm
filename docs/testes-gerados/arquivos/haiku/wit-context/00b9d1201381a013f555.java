/*
 * @test
 * @summary Witup-generated checks for StringBuffer.insert(int, boolean)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) throws Exception {
        StringBuffer empty = new StringBuffer();
        if (empty.insert(0, true) != empty) {
            throw new RuntimeException("insert must return the same StringBuffer instance");
        }
        if (!empty.toString().equals("true")) {
            throw new RuntimeException("unexpected content: " + empty);
        }

        StringBuffer middle = new StringBuffer("start");
        middle.insert(4, true);
        if (!middle.toString().equals("startruet")) {
            throw new RuntimeException("unexpected middle insert: " + middle);
        }

        StringBuffer end = new StringBuffer("abc");
        end.insert(3, false);
        if (!end.toString().equals("abcfalse")) {
            throw new RuntimeException("unexpected end insert: " + end);
        }

        StringBuffer begin = new StringBuffer("abc");
        begin.insert(0, false);
        if (!begin.toString().equals("falseabc")) {
            throw new RuntimeException("unexpected begin insert: " + begin);
        }

        expectOffsetFailure(new StringBuffer("abc"), "abc", -1, true);
        expectOffsetFailure(new StringBuffer("abc"), "abc", 4, false);
        expectOffsetFailure(new StringBuffer(), "", 1, true);
    }

    static void expectOffsetFailure(StringBuffer sb, String before, int offset, boolean b) {
        try {
            sb.insert(offset, b);
        } catch (StringIndexOutOfBoundsException e) {
            if (!sb.toString().equals(before)) {
                throw new RuntimeException("buffer modified after failed insert at offset " + offset);
            }
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
