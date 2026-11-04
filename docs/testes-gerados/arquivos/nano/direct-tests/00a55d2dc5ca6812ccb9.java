/* @test    @summary Test visitClassType method of CheckSignatureAdapter    @run main CheckSignatureAdapterWitupTest */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitClassTypeWithValidName();
        testVisitClassTypeWithNullName();
        testVisitClassTypeWithEmptyName();
        testVisitClassTypeWithInvalidName();
    }

    static void testVisitClassTypeWithValidName() {
        SignatureVisitor sv = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public void visitClassType(String name) {
                visited = true;
                if (name == null || name.isEmpty()) {
                    throw new AssertionError("Name should be non-null and non-empty");
                }
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, sv);
        adapter.visitClassType("com/example/MyClass");
    }

    static void testVisitClassTypeWithNullName() {
        SignatureVisitor sv = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public void visitClassType(String name) {
                visited = true;
                if (name != null) {
                    throw new AssertionError("Expected null name");
                }
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, sv);
        try {
            adapter.visitClassType(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testVisitClassTypeWithEmptyName() {
        SignatureVisitor sv = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public void visitClassType(String name) {
                visited = true;
                if (!"".equals(name)) {
                    throw new AssertionError("Expected empty string");
                }
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, sv);
        adapter.visitClassType("");
    }

    static void testVisitClassTypeWithInvalidName() {
        SignatureVisitor sv = new SignatureVisitor() {
            boolean visited = false;
            @Override
            public void visitClassType(String name) {
                visited = true;
                if (name.contains("!")) {
                    throw new AssertionError("Invalid character in name");
                }
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, sv);
        adapter.visitClassType("invalid!name");
    }
}
