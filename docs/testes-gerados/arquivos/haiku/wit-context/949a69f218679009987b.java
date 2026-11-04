/*
 * @test
 * @summary Witup-derived checks for StringWriter.write(String, int, int) range handling
 * @run main StringWriterWitupTest
 */

public class StringWriterWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIOOBE(String s, int off, int len) {
        StringWriter sw = new StringWriter();
        boolean thrown = false;
        try {
            sw.write(s, off, len);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    static void testValidRanges() {
        StringWriter sw = new StringWriter();
        sw.write("abc", 0, 3);
        check(sw.toString().equals("abc"), "full range write failed");

        sw = new StringWriter();
        sw.write("abc", 1, 2);
        check(sw.toString().equals("bc"), "middle range write failed");

        sw = new StringWriter();
        sw.write("abc", 3, 0);
        check(sw.toString().equals(""), "zero-length write at end should append nothing");

        sw = new StringWriter();
        sw.write("abc", 0, 0);
        check(sw.toString().equals(""), "zero-length write at start should append nothing");
    }

    static void testInvalidRanges() {
        expectIOOBE("abc", -1, 2);
        expectIOOBE("abc", 1, -1);
        expectIOOBE("abc", 0, 5);
        expectIOOBE("abc", 4, 0);
        expectIOOBE("abc", 2, 2);
    }

    public static void main(String[] args) {
        testValidRanges();
        testInvalidRanges();
    }
}
