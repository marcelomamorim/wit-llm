/*
 * @test
 * @summary Verifies Formatter.locale() returns the configured locale and throws FormatterClosedException after close
 * @run main FormatterWitupTest
 */
public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.FRANCE);
        if (!Locale.FRANCE.equals(f.locale())) {
            throw new AssertionError("locale mismatch: " + f.locale());
        }
        f.format("%d", 1);
        if (!Locale.FRANCE.equals(f.locale())) {
            throw new AssertionError("locale changed after format: " + f.locale());
        }
        f.close();
        try {
            f.locale();
            throw new AssertionError("expected FormatterClosedException");
        } catch (FormatterClosedException expected) {
        }
        f.close();
        try {
            f.locale();
            throw new AssertionError("expected FormatterClosedException after second close");
        } catch (FormatterClosedException expected) {
        }

        Formatter g = new Formatter(Locale.US);
        if (!Locale.US.equals(g.locale())) {
            throw new AssertionError("locale mismatch for Formatter(Locale): " + g.locale());
        }
        g.close();
        try {
            g.locale();
            throw new AssertionError("expected FormatterClosedException for Formatter(Locale)");
        } catch (FormatterClosedException expected) {
        }
    }
}
