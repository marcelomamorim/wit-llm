/*
 * @test
 * @summary Witup-derived checks for StringBuilder.insert(int, CharSequence)
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        insertsInMiddle();
        insertsAtBoundaries();
        insertsNullAsLiteralNull();
        rejectsOutOfRangeOffset();
        rejectsOutOfRangeOffsetWithNullArgument();
        returnsSameBuilder();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void insertsInMiddle() {
        StringBuilder sb = new StringBuilder("start");
        sb.insert(4, (CharSequence) "le");
        check("starlet".equals(sb.toString()), "middle insert: " + sb);
    }

    static void insertsAtBoundaries() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, (CharSequence) "x");
        check("xabc".equals(sb.toString()), "insert at 0: " + sb);
        sb.insert(sb.length(), (CharSequence) "y");
        check("xabcy".equals(sb.toString()), "insert at end: " + sb);
    }

    static void insertsNullAsLiteralNull() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, (CharSequence) null);
        check("anullb".equals(sb.toString()), "null insert: " + sb);
    }

    static void rejectsOutOfRangeOffset() {
        expectSIOOBE(new StringBuilder("abc"), -1, (CharSequence) "xy");
        expectSIOOBE(new StringBuilder("abc"), 4, (CharSequence) "xy");
    }

    static void rejectsOutOfRangeOffsetWithNullArgument() {
        expectSIOOBE(new StringBuilder("abc"), -1, (CharSequence) null);
        expectSIOOBE(new StringBuilder("abc"), 4, (CharSequence) null);
    }

    static void expectSIOOBE(StringBuilder sb, int off, CharSequence s) {
        try {
            sb.insert(off, s);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + off);
    }

    static void returnsSameBuilder() {
        StringBuilder sb = new StringBuilder("q");
        StringBuilder r = sb.insert(0, (CharSequence) "p");
        check(r == sb, "insert must return this");
        check("pq".equals(sb.toString()), "content after insert: " + sb);
    }
}
