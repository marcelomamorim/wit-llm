/*
 * @test
 * @summary Tests String.subSequence results and bounds exceptions
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        String s = "abcdef";

        if (!"abc".equals(s.subSequence(0, 3).toString())) {
            throw new RuntimeException("subSequence(0,3) returned wrong value");
        }
        if (!"".equals(s.subSequence(2, 2).toString())) {
            throw new RuntimeException("subSequence(2,2) should be empty");
        }
        if (!"cdef".equals(s.subSequence(2, 6).toString())) {
            throw new RuntimeException("subSequence(2,6) returned wrong value");
        }
        if (s.subSequence(0, s.length()).length() != s.length()) {
            throw new RuntimeException("full range length mismatch");
        }

        expectSIOOBE(s, -1, 2);
        expectSIOOBE(s, 3, 2);
        expectSIOOBE(s, 0, 7);
        expectSIOOBE(s, 7, 7);
    }

    private static void expectSIOOBE(String s, int begin, int end) {
        try {
            s.subSequence(begin, end);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for "
                + begin + "," + end);
    }
}
