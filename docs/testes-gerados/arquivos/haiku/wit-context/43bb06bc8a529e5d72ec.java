/*
 * @test
 * @summary Verify that Scanner.remove() always throws UnsupportedOperationException
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new StringReader("1 2 3"));
        expectUnsupported(sc);
        sc.nextInt();
        expectUnsupported(sc);
        sc.close();
        expectUnsupported(sc);
    }

    static void expectUnsupported(Scanner sc) {
        try {
            sc.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from Scanner.remove()");
    }
}
