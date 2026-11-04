/*
 * @test
 * @summary Witup-derived tests for ClassLoader.getSystemResource delegation and missing resource behavior
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        String missing = "witup/no/such/resource/does-not-exist.txt";
        if (ClassLoader.getSystemResource(missing) != null) {
            throw new RuntimeException("expected null for missing resource");
        }

        String[] names = { missing, "java/lang/Object.class" };
        for (String name : names) {
            String viaStatic = String.valueOf(ClassLoader.getSystemResource(name));
            String viaLoader = String.valueOf(
                    ClassLoader.getSystemClassLoader().getResource(name));
            if (!viaStatic.equals(viaLoader)) {
                throw new RuntimeException("getSystemResource does not delegate for " + name
                        + ": " + viaStatic + " vs " + viaLoader);
            }
        }
    }
}
