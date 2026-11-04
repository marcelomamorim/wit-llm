/*
 * @test
 * @summary Test ClassVisitor single-argument constructor behavior
 * @run main ClassVisitorWitupTest
 */

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        // Test with a valid ASM API version (e.g., 7 for ASM7, which is > ASM6 but allowed here as int)
        // Since the constructor delegates to ClassVisitor(int api, ClassVisitor cv) with cv=null,
        // we test that the object is created and api field is set correctly.

        // Use a subclass to access protected fields for verification
        class TestCV extends jdk.internal.org.objectweb.asm.ClassVisitor {
            TestCV(int api) {
                super(api);
            }

            int getApi() {
                return api;
            }

            jdk.internal.org.objectweb.asm.ClassVisitor getCv() {
                return cv;
            }
        }

        int testApi = 7; // arbitrary int representing ASM API version
        TestCV cv = new TestCV(testApi);

        if (cv.getApi() != testApi) {
            throw new AssertionError("api field not set correctly: expected " + testApi + " but got " + cv.getApi());
        }

        if (cv.getCv() != null) {
            throw new AssertionError("cv field should be null but is not");
        }

        // No exceptions should be thrown
        System.out.println("Test passed: ClassVisitor(int api) constructor works as expected.");
    }
}
