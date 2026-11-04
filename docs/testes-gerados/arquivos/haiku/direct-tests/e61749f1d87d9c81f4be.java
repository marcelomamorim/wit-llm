/*
 * @test
 * @summary Behavioral checks for Arrays.copyOfRange(T[], int, int) using the public API
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        String[] src = {"a", "b", "c"};

        String[] r = Arrays.copyOfRange(src, 0, 2);
        check(r.length == 2, "length of prefix copy");
        check("a".equals(r[0]), "first element of prefix copy");
        check("b".equals(r[1]), "second element of prefix copy");
        check(r.getClass() == String[].class, "runtime type preserved for prefix copy");

        r[0] = "changed";
        check("a".equals(src[0]), "copy must not alias the original array");

        String[] p = Arrays.copyOfRange(src, 1, 5);
        check(p.length == 4, "length when to exceeds original length");
        check("b".equals(p[0]), "element copied from middle");
        check("c".equals(p[1]), "last element copied");
        check(p[2] == null, "padding beyond original must be null");
        check(p[3] == null, "padding beyond original must be null (last slot)");
        check(p.getClass() == String[].class, "runtime type preserved when padded");

        Integer[] empty = Arrays.copyOfRange(new Integer[]{1, 2}, 1, 1);
        check(empty.length == 0, "empty range yields zero-length array");
        check(empty.getClass() == Integer[].class, "runtime type preserved for empty range");

        String[] whole = Arrays.copyOfRange(src, 0, 3);
        check(whole.length == 3, "full range length");
        check("c".equals(whole[2]), "full range last element");

        String[] sub = Arrays.copyOfRange(new String[]{"x"}, 0, 1);
        check(sub.length == 1 && "x".equals(sub[0]), "single element copy");

        boolean npe = false;
        try {
            Arrays.copyOfRange((String[]) null, 0, 1);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "null original must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
