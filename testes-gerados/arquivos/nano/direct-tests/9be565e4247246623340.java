/*
 * @test
 * @summary Test CheckMethodAdapter.visitEnd() method for correct execution
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndCallsCheckEndMethod();
        testVisitEndCallsSuper();
    }

    public static void testVisitEndCallsCheckEndMethod() {
        CheckMethodAdapter adapter = new CheckMethodAdapter() {
            boolean checkEndMethodCalled = false;

            @Override
            public void checkEndMethod() {
                checkEndMethodCalled = true;
            }

            @Override
            public void visitEnd() {
                checkEndMethod();
                super.visitEnd();
            }
        };
        adapter.visitEnd();
        assert adapter.checkEndMethodCalled : "checkEndMethod() should be called";
    }

    public static void testVisitEndCallsSuper() {
        class SuperAdapter extends CheckMethodAdapter {
            boolean superVisitEndCalled = false;

            public SuperAdapter() {
                super();
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
                superVisitEndCalled = true;
            }
        }
        SuperAdapter adapter = new SuperAdapter();
        adapter.visitEnd();
        assert adapter.superVisitEndCalled : "super.visitEnd() should be called";
    }
}
