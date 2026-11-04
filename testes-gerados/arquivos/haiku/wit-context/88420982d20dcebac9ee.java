/*
 * @test
 * @summary Witup-derived checks for StringBuffer.insert(int, float) offset validation and float insertion
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        checkValidInsert();
        checkOutOfBounds(4);
        checkOutOfBounds(-1);
        checkOutOfBounds(100);
    }

    static void checkValidInsert() {
        String r1 = new StringBuffer("abc").insert(3, 1.5f).toString();
        if (!"abc1.5".equals(r1)) {
            throw new RuntimeException("insert at end failed: " + r1);
        }
        String r2 = new StringBuffer("abc").insert(0, 2.0f).toString();
        if (!"2.0abc".equals(r2)) {
            throw new RuntimeException("insert at start failed: " + r2);
        }
        String r3 = new StringBuffer("abc").insert(1, 2.0f).toString();
        if (!"a2.0bc".equals(r3)) {
            throw new RuntimeException("insert in middle failed: " + r3);
        }
    }

    static void checkOutOfBounds(int offset) {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(offset, 1.5f);
            throw new RuntimeException("no exception for offset " + offset);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        if (!"abc".equals(sb.toString())) {
            throw new RuntimeException("buffer modified after failed insert: " + sb);
        }
    }
}
