/*
 * @test
 * @summary Verifies IvParameterSpec(byte[]) copies its input, returns defensive copies from getIV(), accepts empty arrays, and throws NullPointerException for null.
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] src = {1, 2, 3, 4, 5, 6, 7, 8};
        IvParameterSpec spec = new IvParameterSpec(src);

        check(java.util.Arrays.equals(src, spec.getIV()), "IV content should equal input");

        src[0] = 99;
        check(spec.getIV()[0] == 1, "constructor must copy input buffer");

        byte[] out = spec.getIV();
        out[1] = 77;
        check(spec.getIV()[1] == 2, "getIV must return a copy");

        check(spec.getIV() != spec.getIV(), "getIV must return a new array on each call");

        IvParameterSpec empty = new IvParameterSpec(new byte[0]);
        check(empty.getIV().length == 0, "empty IV should yield empty array");

        boolean npe = false;
        try {
            new IvParameterSpec(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null IV should throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
