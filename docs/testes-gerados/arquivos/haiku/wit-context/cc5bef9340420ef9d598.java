/*
 * @test
 * @summary Verify StringBuilder.insert(int, int) returns this, inserts the decimal form of the value, and rejects out-of-range offsets without modifying the builder
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder ret = sb.insert(1, 42);
        check(ret == sb, "insert must return this");
        check(sb.toString().equals("a42bc"), "unexpected content: " + sb);

        StringBuilder end = new StringBuilder("abc");
        end.insert(3, 42);
        check(end.toString().equals("abc42"), "insert at length failed: " + end);

        StringBuilder start = new StringBuilder("abc");
        start.insert(0, -5);
        check(start.toString().equals("-5abc"), "insert at 0 failed: " + start);

        StringBuilder empty = new StringBuilder();
        empty.insert(0, 9);
        check(empty.toString().equals("9"), "insert into empty failed: " + empty);

        StringBuilder bad = new StringBuilder("abc");
        expectSIOOBE(bad, 4, 7);
        expectSIOOBE(bad, -1, 7);
        check(bad.toString().equals("abc"), "builder modified after failed insert: " + bad);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectSIOOBE(StringBuilder sb, int offset, int value) {
        try {
            sb.insert(offset, value);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
