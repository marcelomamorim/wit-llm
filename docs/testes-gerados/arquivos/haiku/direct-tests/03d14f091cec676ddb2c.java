/*
 * @test
 * @summary Verifies StringBuffer.codePointAt returns code points for BMP and supplementary characters and rejects out-of-range indexes.
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.codePointAt(0) == 'a', "codePointAt(0) on abc should be 'a'");
        check(sb.codePointAt(1) == 'b', "codePointAt(1) on abc should be 'b'");
        check(sb.codePointAt(2) == 'c', "codePointAt(2) on abc should be 'c'");

        StringBuffer supp = new StringBuffer();
        supp.append(Character.toChars(0x1F600));
        check(supp.length() == 2, "supplementary char should occupy two chars");
        check(supp.codePointAt(0) == 0x1F600, "codePointAt(0) should combine surrogate pair");

        StringBuffer mixed = new StringBuffer("a");
        mixed.append(Character.toChars(0x1F600));
        check(mixed.codePointAt(1) == 0x1F600, "codePointAt(1) should return the pair starting at index 1");

        StringBuffer lone = new StringBuffer();
        lone.append((char) 0xD83D);
        check(lone.codePointAt(0) == 0xD83D, "lone high surrogate should be returned as its char value");

        expectIOOBE(new StringBuffer(), 0);
        expectIOOBE(sb, -1);
        expectIOOBE(sb, 3);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIOOBE(StringBuffer sb, int index) {
        try {
            sb.codePointAt(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no IndexOutOfBoundsException for index " + index);
    }
}
