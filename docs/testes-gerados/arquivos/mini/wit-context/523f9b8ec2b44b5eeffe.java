/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator.setStart(int) method
 * @run main StringTokenIteratorWitupTest
 */

public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        testSetStartValid();
        testSetStartOffsetGreaterThanTextLength();
    }

    private static void testSetStartValid() {
        String text = "a,b,c";
        String dlms = ",";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);

        // setStart with valid offset 0
        it.setStart(0);
        String token = it.current();
        if (!"a".equals(token)) {
            throw new RuntimeException("Expected token 'a' at offset 0 but got: " + token);
        }

        // setStart with valid offset 2 (start of 'b')
        it.setStart(2);
        token = it.current();
        if (!"b".equals(token)) {
            throw new RuntimeException("Expected token 'b' at offset 2 but got: " + token);
        }

        // setStart with valid offset 4 (start of 'c')
        it.setStart(4);
        token = it.current();
        if (!"c".equals(token)) {
            throw new RuntimeException("Expected token 'c' at offset 4 but got: " + token);
        }
    }

    private static void testSetStartOffsetGreaterThanTextLength() {
        String text = "abc";
        String dlms = ",";
        sun.util.locale.StringTokenIterator it = new sun.util.locale.StringTokenIterator(text, dlms);

        boolean thrown = false;
        try {
            it.setStart(text.length() + 1); // offset > text.length()
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException when offset > text.length()");
        }
    }
}
