/* @test
   @summary Testa o método getCallerClass() de StackWalker para comportamento padrão.
   @run main StackWalkerWitupTest_Pe13155af702f
 */
import java.lang.Throwable;
import java.lang.Class;
import java.lang.UnsupportedOperationException;
import java.util.Objects;

public class StackWalkerWitupTest_Pe13155af702f {
    public static void main(String[] args) {
        testGetCallerClassWithRetainClassRef();
        testGetCallerClassWithoutRetainClassRef();
    }

    private static void testGetCallerClassWithRetainClassRef() {
        // Cria uma instância de StackWalker com retainClassRef habilitado
        java.lang.StackWalker walker = java.lang.StackWalker.getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = null;
        try {
            callerClass = walker.getCallerClass();
            // Verifica se a classe retornada é diferente de null
            if (callerClass == null) {
                throw new AssertionError("getCallerClass() retornou null quando retainClassRef está habilitado");
            }
        } catch (Throwable t) {
            throw new AssertionError("Erro ao chamar getCallerClass() com retainClassRef habilitado", t);
        }
        // Opcional: verificar se a classe é a do método de teste
        if (!callerClass.equals(StackWalkerWitupTest_Pe13155af702f.class)) {
            System.out.println("Aviso: getCallerClass() retornou classe diferente da classe de teste: " + callerClass);
        }
    }

    private static void testGetCallerClassWithoutRetainClassRef() {
        // Cria uma instância de StackWalker sem retainClassRef
        java.lang.StackWalker walker = java.lang.StackWalker.getInstance();
        try {
            Class<?> cls = walker.getCallerClass();
            throw new AssertionError("Esperado UnsupportedOperationException ao chamar getCallerClass() sem retainClassRef");
        } catch (UnsupportedOperationException e) {
            // Esperado
            String msg = e.getMessage();
            if (msg == null || !msg.contains("does not have RETAIN_CLASS_REFERENCE access")) {
                throw new AssertionError("Mensagem de exceção inesperada", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Erro inesperado ao chamar getCallerClass() sem retainClassRef", t);
        }
    }
}
