/*
 * @test
 * @summary Witup-style checks for StringBuilder.insert(int, char[]), which delegates to AbstractStringBuilder.insert
 * @run main AbstractStringBuilderWitupTest
 */
public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        insertAtStart();
        insertInMiddle();
        insertAtEnd();
        insertEmptyArray();
        returnsSameBuilder();
        negativeOffsetThrows();
        offsetBeyondLengthThrows();
        nullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void insertAtStart() {
        StringBuilder sb = new StringBuilder("bc");
        sb.insert(0, new char[]{'a'});
        check("abc".equals(sb.toString()), "insert at 0: " + sb);
    }

    static void insertInMiddle() {
        StringBuilder sb = new StringBuilder("ad");
        sb.insert(1, new char[]{'b', 'c'});
        check("abcd".equals(sb.toString()), "insert in middle: " + sb);
    }

    static void insertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, new char[]{'c', 'd'});
        check("abcd".equals(sb.toString()), "insert at end: " + sb);
        check(sb.length() == 4, "length after insert at end: " + sb.length());
    }

    static void insertEmptyArray() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, new char[0]);
        check("abc".equals(sb.toString()), "insert empty array: " + sb);
    }

    static void returnsSameBuilder() {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder result = sb.insert(0, new char[]{'y'});
        check(result == sb, "insert should return this builder");
        check("yx".equals(sb.toString()), "content after insert: " + sb);
    }

    static void negativeOffsetThrows() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, new char[]{'x'});
        } catch (StringIndexOutOfBoundsException e) {
            check("abc".equals(sb.toString()), "builder changed after failed insert: " + sb);
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for negative offset");
    }

    static void offsetBeyondLengthThrows() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(4, new char[]{'x'});
        } catch (StringIndexOutOfBoundsException e) {
            check("abc".equals(sb.toString()), "builder changed after failed insert: " + sb);
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset > length");
    }

    static void nullArrayThrowsNPE() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(0, (char[]) null);
        } catch (NullPointerException e) {
            check("abc".equals(sb.toString()), "builder changed after NPE: " + sb);
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }
}
