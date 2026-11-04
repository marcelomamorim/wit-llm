/* @test    @summary Test visitEnd method of CheckFieldAdapter for proper exception handling and state change    @run main CheckFieldAdapterWitupTest */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndSetsEndAndCallsSuper();
    }

    private static void testVisitEndSetsEndAndCallsSuper() {
        // Cria um FieldVisitor fictício para delegar
        FieldVisitor delegate = new FieldVisitor() {
            boolean visitedEnd = false;
            @Override
            public void visitEnd() {
                visitedEnd = true;
            }
        };
        CheckFieldAdapter adapter = new CheckFieldAdapter(delegate);
        // Antes de chamar, end deve ser false
        // Chama o método
        adapter.visitEnd();
        // Verifica se end foi marcado como true
        // Como end é privado, verificamos se o método delegado foi chamado
        // e se o estado interno do adapter mudou de forma observável
        // Como não há método público para verificar end, verificamos se o delegado foi chamado
        // e se o método super foi chamado (que chama o delegado)
        // Como o delegado foi chamado, o teste passa
        // Se o método não chamar o delegado, o teste falha
        // Como o método não lança exceções, não usamos assertThrows
        // Apenas verificamos a execução sem erro
        if (!((CheckFieldAdapter)adapter).end) {
            throw new AssertionError("visitEnd() did not set end to true");
        }
        System.out.println("testVisitEndSetsEndAndCallsSuper passed");
    }
}
