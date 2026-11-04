/*
 * @test
 * @summary Public Module API behaviour related to reads of the unnamed module, observable without internal access
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        Module unnamed = ClassLoader.getSystemClassLoader().getUnnamedModule();
        Module javaBase = Object.class.getModule();
        if (unnamed.isNamed()) {
            throw new AssertionError("unnamed module must not be named");
        }
        if (!unnamed.canRead(javaBase)) {
            throw new AssertionError("unnamed module must read java.base");
        }
        try {
            javaBase.addReads(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }
}
