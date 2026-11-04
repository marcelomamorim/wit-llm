/*
 * @test
 * @summary Verifies argument validation and acceptance of PBEKeySpec(char[], byte[], int)
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        char[] pwd = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};

        new PBEKeySpec(pwd, salt, 1000);
        new PBEKeySpec(null, salt, 1);
        new PBEKeySpec(new char[0], salt, 1);

        expectNPE(() -> new PBEKeySpec(pwd, null, 1000));
        expectNPE(() -> new PBEKeySpec(pwd, null, 0));
        expectIAE(() -> new PBEKeySpec(pwd, new byte[0], 1000));
        expectIAE(() -> new PBEKeySpec(pwd, salt, 0));
        expectIAE(() -> new PBEKeySpec(pwd, salt, -1));
        expectIAE(() -> new PBEKeySpec(pwd, salt, Integer.MIN_VALUE));
        expectIAE(() -> new PBEKeySpec(null, new byte[0], 1));
    }

    private static void expectNPE(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    private static void expectIAE(Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }
}
