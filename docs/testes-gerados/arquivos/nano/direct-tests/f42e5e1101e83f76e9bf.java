/*
 * @test
 * @summary Test visitOpen method of Textifier class
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitOpenWithModules();
        testVisitOpenWithoutModules();
    }

    static void testVisitOpenWithModules() {
        Textifier t = new Textifier();
        String export = "com/example/MyClass";
        int access = 0x0001;
        String[] modules = {"moduleA", "moduleB"};
        t.visitOpen(export, access, modules);
        String output = t.getText().get(t.getText().size() - 1);
        assert output.contains("opens ") : "Output should contain 'opens'";
        assert output.contains(export) : "Output should contain export string";
        assert output.contains("to") : "Output should contain 'to'";
        assert output.contains("0x1") || output.contains("0X1") || output.contains("1") : "Access flags should be in output";
        for (String module : modules) {
            assert output.contains(module) : "Output should contain module: " + module;
        }
    }

    static void testVisitOpenWithoutModules() {
        Textifier t = new Textifier();
        String export = "com/example/OtherClass";
        int access = 0x0002;
        String[] modules = null;
        t.visitOpen(export, access, modules);
        String output = t.getText().get(t.getText().size() - 1);
        assert output.contains("opens ") : "Output should contain 'opens'";
        assert output.contains(export) : "Output should contain export string";
        assert output.contains(";") : "Output should end with semicolon";
        assert output.contains("0x2") || output.contains("0X2") || output.contains("2") : "Access flags should be in output";
    }
}
