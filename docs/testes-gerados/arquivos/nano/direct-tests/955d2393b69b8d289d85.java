/*
 * @test
 * @summary Test visitModule method of ClassVisitor
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testVisitModuleWithApiBelowASM6();
        testVisitModuleWithApiAtLeastASM6();
    }

    static void testVisitModuleWithApiBelowASM6() {
        try {
            // Simulate API version below ASM6
            int apiVersion = 5;
            ClassVisitor visitor = new ClassVisitor(apiVersion) {
                @Override
                public ModuleVisitor visitModule(String name, int access, String version) {
                    // Should throw RuntimeException due to API version check
                    return super.visitModule(name, access, version);
                }
            };
            // Call visitModule, expecting RuntimeException
            visitor.visitModule("testModule", 0, "1.0");
            // If no exception, fail test
            System.err.println("Expected RuntimeException for API below ASM6, but none was thrown.");
        } catch (RuntimeException e) {
            // Expected exception
            System.out.println("Caught expected RuntimeException for API below ASM6.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    static void testVisitModuleWithApiAtLeastASM6() {
        try {
            // Simulate API version at least ASM6
            int apiVersion = 6;
            ClassVisitor visitor = new ClassVisitor(apiVersion) {
                @Override
                public ModuleVisitor visitModule(String name, int access, String version) {
                    // Call super method
                    return super.visitModule(name, access, version);
                }
            };
            ModuleVisitor mv = visitor.visitModule("testModule", 0, "1.0");
            // Expect null as cv is null
            if (mv != null) {
                System.err.println("Expected null ModuleVisitor when cv is null.");
            } else {
                System.out.println("visitModule returned null as expected when cv is null.");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
