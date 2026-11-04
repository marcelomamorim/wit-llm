/*
 * @test
 * @summary Smoke check of public java.text.Normalizer behavior; Norm2AllModes.normalize(CharSequence, Appendable) is not directly reachable
 * @run main Norm2AllModesWitupTest
 */
public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        String s = java.text.Normalizer.normalize("abc", java.text.Normalizer.Form.NFC);
        if (!"abc".equals(s)) {
            throw new RuntimeException("unexpected: " + s);
        }
    }
}
