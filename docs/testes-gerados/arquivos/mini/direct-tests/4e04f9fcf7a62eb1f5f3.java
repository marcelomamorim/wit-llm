/*
 * @test
 * @summary Test constructor FieldVisitor(int api) delegates correctly
 * @run main FieldVisitorWitupTest
 */

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        // Create a concrete subclass to instantiate abstract FieldVisitor
        class FV extends jdk.internal.org.objectweb.asm.FieldVisitor {
            public FV(int api) {
                super(api);
            }
        }

        // Test with a valid ASM API version (e.g., 7 for ASM7, but here use 5 as allowed)
        int api = 5;
        FV fv = new FV(api);

        // Check that the api field is set correctly
        if (fv.api != api) {
            throw new AssertionError("FieldVisitor.api expected: " + api + " but was: " + fv.api);
        }

        // Check that the fv field is null as per constructor
        if (fv.fv != null) {
            throw new AssertionError("FieldVisitor.fv expected to be null but was: " + fv.fv);
        }
    }
}
