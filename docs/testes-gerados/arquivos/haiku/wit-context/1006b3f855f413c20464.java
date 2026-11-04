/*
 * @test
 * @summary Verifies Objects.checkFromIndexSize returns fromIndex for valid ranges and throws IndexOutOfBoundsException for invalid ones
 * @run main ObjectsWitupTest
 */
import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        expectOk(0, 0, 0);
        expectOk(0, 5, 5);
        expectOk(5, 0, 5);
        expectOk(2, 3, 5);
        expectOk(0, 0, Integer.MAX_VALUE);
        expectOk(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        expectOob(-1, 0, 5);
        expectOob(0, -1, 5);
        expectOob(0, 0, -1);
        expectOob(0, 6, 5);
        expectOob(3, 3, 5);
        expectOob(6, 0, 5);
        expectOob(Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
        expectOob(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    static void expectOk(int fromIndex, int size, int length) {
        int r = Objects.checkFromIndexSize(fromIndex, size, length);
        if (r != fromIndex) {
            throw new RuntimeException("unexpected result " + r + " for " + fromIndex + "," + size + "," + length);
        }
    }

    static void expectOob(int fromIndex, int size, int length) {
        try {
            Objects.checkFromIndexSize(fromIndex, size, length);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for " + fromIndex + "," + size + "," + length);
    }
}
