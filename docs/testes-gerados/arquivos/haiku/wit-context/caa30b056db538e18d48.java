/*
 * @test
 * @summary Witup-derived bounds checks for the list returned by Collections.nCopies (CopiesList.get)
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = Collections.nCopies(3, "x");
        for (int i = 0; i < 3; i++) {
            check("x".equals(list.get(i)), "get(" + i + ") should return the element");
        }
        expectIOOBE(list, -1);
        expectIOOBE(list, 3);
        expectIOOBE(list, Integer.MAX_VALUE);
        expectIOOBE(list, Integer.MIN_VALUE);

        List<String> empty = Collections.nCopies(0, "y");
        expectIOOBE(empty, 0);
        expectIOOBE(empty, -1);
    }

    static void expectIOOBE(List<String> l, int idx) {
        try {
            l.get(idx);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + idx);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
