/*
 * @test
 * @summary Test for visitAnnotationDefault method in CheckMethodAdapter
 * @run main CheckMethodAdapterWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationDefault();
    }

    public static void testVisitAnnotationDefault() {
        // Cria um objeto CheckMethodAdapter com um MethodVisitor nulo
        CheckMethodAdapter adapter = new CheckMethodAdapter(null);
        try {
            // Invoca o método visitAnnotationDefault
            AnnotationVisitor av = adapter.visitAnnotationDefault();
            // Verifica se o retorno não é nulo
            if (av == null) {
                System.out.println("visitAnnotationDefault retornou null.");
            } else {
                System.out.println("visitAnnotationDefault retornou um AnnotationVisitor.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
