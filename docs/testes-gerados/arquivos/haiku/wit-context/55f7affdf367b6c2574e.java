/*
 * @test
 * @summary StringTokenizer.nextElement returns tokens in order and throws NoSuchElementException when exhausted
 * @run main StringTokenizerWitupTest
 */
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("this is a test");
        String[] expected = {"this", "is", "a", "test"};
        for (String e : expected) {
            Object o = st.nextElement();
            if (!e.equals(o)) {
                throw new RuntimeException("expected " + e + " but got " + o);
            }
        }
        if (st.hasMoreElements()) {
            throw new RuntimeException("expected no more elements");
        }
        expectNoSuchElement(st);

        StringTokenizer empty = new StringTokenizer("");
        expectNoSuchElement(empty);

        StringTokenizer withDelims = new StringTokenizer("a,b", ",", true);
        String[] expectedDelims = {"a", ",", "b"};
        for (String e : expectedDelims) {
            Object o = withDelims.nextElement();
            if (!e.equals(o)) {
                throw new RuntimeException("expected " + e + " but got " + o);
            }
        }
        expectNoSuchElement(withDelims);

        StringTokenizer viaElement = new StringTokenizer("x y");
        StringTokenizer viaToken = new StringTokenizer("x y");
        while (viaToken.hasMoreTokens()) {
            Object o = viaElement.nextElement();
            if (!viaToken.nextToken().equals(o)) {
                throw new RuntimeException("nextElement diverges from nextToken");
            }
        }
        expectNoSuchElement(viaElement);
    }

    static void expectNoSuchElement(StringTokenizer st) {
        try {
            st.nextElement();
        } catch (NoSuchElementException ex) {
            return;
        }
        throw new RuntimeException("NoSuchElementException expected");
    }
}
