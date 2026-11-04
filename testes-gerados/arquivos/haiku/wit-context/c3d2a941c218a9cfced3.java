/*
 * @test
 * @summary Observable System properties check; definePackage expath discarded (no public path)
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        String sep = System.getProperty("line.separator");
        if (sep == null) {
            throw new RuntimeException("line.separator is null");
        }
        if (!sep.equals(System.lineSeparator())) {
            throw new RuntimeException("line.separator mismatch with System.lineSeparator()");
        }
    }
}
