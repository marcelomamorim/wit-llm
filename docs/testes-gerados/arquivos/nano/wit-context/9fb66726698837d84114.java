/*
 * @test
 * @summary Test for visitSource method in CheckClassAdapter.
 * @run main CheckClassAdapterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitSourceNormal();
        testVisitSourceOnce();
        testVisitSourceWithSource();
    }

    static void testVisitSourceNormal() {
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter adapter = new jdk.internal.org.objectweb.asm.util.CheckClassAdapter(null);
        try {
            adapter.visitSource("TestSource.java", "DebugInfo");
            System.out.println("testVisitSourceNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitSourceNormal failed: " + e);
        }
    }

    static void testVisitSourceOnce() {
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter adapter = new jdk.internal.org.objectweb.asm.util.CheckClassAdapter(null);
        try {
            adapter.visitSource("TestSource.java", "DebugInfo");
            adapter.visitSource("AnotherSource.java", "DebugInfo");
            System.err.println("testVisitSourceOnce failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitSourceOnce passed");
        } catch (Exception e) {
            System.err.println("testVisitSourceOnce failed: " + e);
        }
    }

    static void testVisitSourceWithSource() {
        jdk.internal.org.objectweb.asm.util.CheckClassAdapter adapter = new jdk.internal.org.objectweb.asm.util.CheckClassAdapter(null);
        try {
            adapter.visitSource("TestSource.java", "DebugInfo");
            adapter.source = false; // forcibly reset to simulate source already set
            adapter.visitSource("AnotherSource.java", "DebugInfo");
            System.err.println("testVisitSourceWithSource failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitSourceWithSource passed");
        } catch (Exception e) {
            System.err.println("testVisitSourceWithSource failed: " + e);
        }
    }
}
