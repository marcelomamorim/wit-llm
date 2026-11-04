/*
 * @test
 * @summary Verifies Inflater.setInput argument validation and observable remaining input count
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) throws Exception {
        expectAioobe(new byte[4], -1, 2);
        expectAioobe(new byte[4], 0, -1);
        expectAioobe(new byte[4], 3, 2);
        expectAioobe(new byte[0], 0, 1);

        Inflater inf = new Inflater();
        try {
            inf.setInput(new byte[4], 1, 3);
            check(inf.getRemaining() == 3, "remaining after (1,3) should be 3");

            inf.setInput(new byte[4], 2, 2);
            check(inf.getRemaining() == 2, "remaining after (2,2) should be 2");

            inf.setInput(new byte[4], 4, 0);
            check(inf.getRemaining() == 0, "remaining after (4,0) should be 0");
        } finally {
            inf.end();
        }
    }

    static void expectAioobe(byte[] input, int off, int len) {
        Inflater inf = new Inflater();
        try {
            inf.setInput(input, off, len);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException for off="
                    + off + " len=" + len);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } finally {
            inf.end();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
