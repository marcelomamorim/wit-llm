/*
 * @test
 * @summary StringJoiner.merge observable behavior
 * @run main StringJoinerWitupTest
 */
import java.util.StringJoiner;

public class StringJoinerWitupTest {
    public static void main(String[] args) {
        testMergeNull();
        testMergeEmptyOther();
        testMergeWithElements();
        testMergeIntoEmpty();
        testMergeReturnsThis();
        testMergeEmptyStringElement();
        testOtherUnchangedObservable();
    }

    static void checkEq(Object expected, Object actual, String msg) {
        if (!String.valueOf(expected).equals(String.valueOf(actual))) {
            throw new RuntimeException(msg + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    static void testMergeNull() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("a");
        boolean thrown = false;
        try {
            sj.merge(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("merge(null) must throw NullPointerException");
        }
        checkEq("a", sj.toString(), "joiner changed after failed merge");
    }

    static void testMergeEmptyOther() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        sj.add("a").add("b");
        StringJoiner other = new StringJoiner("-");
        sj.merge(other);
        checkEq("[a, b]", sj.toString(), "merge of empty joiner must not change result");
    }

    static void testMergeWithElements() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        sj.add("a");
        StringJoiner other = new StringJoiner("-");
        other.add("x").add("y");
        sj.merge(other);
        checkEq("[a, x-y]", sj.toString(), "merge with elements");
    }

    static void testMergeIntoEmpty() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        StringJoiner other = new StringJoiner("-");
        other.add("x").add("y");
        sj.merge(other);
        checkEq("[x-y]", sj.toString(), "merge into empty joiner");
    }

    static void testMergeReturnsThis() {
        StringJoiner sj = new StringJoiner(",");
        StringJoiner other = new StringJoiner("-");
        other.add("x");
        StringJoiner result = sj.merge(other);
        if (result != sj) {
            throw new RuntimeException("merge must return this");
        }
        StringJoiner emptyOther = new StringJoiner("-");
        if (sj.merge(emptyOther) != sj) {
            throw new RuntimeException("merge with empty other must return this");
        }
    }

    static void testMergeEmptyStringElement() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("a");
        StringJoiner other = new StringJoiner("-");
        other.add("");
        sj.merge(other);
        checkEq("a,", sj.toString(), "merge of empty-string element");
    }

    static void testOtherUnchangedObservable() {
        StringJoiner sj = new StringJoiner(",");
        StringJoiner other = new StringJoiner("-");
        other.add("x").add("y");
        checkEq("x-y", other.toString(), "other before merge");
        sj.merge(other);
        checkEq("x-y", other.toString(), "other after merge");
    }
}
