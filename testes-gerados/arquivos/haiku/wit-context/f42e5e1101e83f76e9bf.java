/*
 * @test
 * @summary Smoke test for Textifier.visitOpen with varied export and module arguments
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitOpen("pkg/a", 0);
        t.visitOpen("pkg/b", 0x20, (String[]) null);
        t.visitOpen("pkg/c", 0, new String[0]);
        t.visitOpen("pkg/d", 0x8000, "mod.one");
        t.visitOpen("pkg/e", 1, "mod.one", "mod.two");
        t.visitOpen("pkg/f", 0, "mod.one", null);
        t.visitOpen(null, 0);
    }
}
