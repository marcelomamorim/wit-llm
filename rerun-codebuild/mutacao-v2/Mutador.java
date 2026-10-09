import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.tree.*;
import java.nio.file.*;
import java.util.*;

/** Neutraliza o ATHROW no offset indicado: descarta a exceção e retorna. */
public class Mutador {
    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]); String meth = a[1], desc = a[2];
        int idxAlvo = Integer.parseInt(a[3]); Path out = Paths.get(a[4]);
        ClassNode cn = new ClassNode();
        new ClassReader(Files.readAllBytes(in)).accept(cn, 0);
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals(meth) || !mn.desc.equals(desc)) continue;
            // seleciona o N-ésimo ATHROW (ordem = ordem dos offsets do javap)
            int n = 0; AbstractInsnNode achado = null;
            for (AbstractInsnNode ins : mn.instructions.toArray()) {
                if (ins.getOpcode() == Opcodes.ATHROW && ++n == idxAlvo) { achado = ins; break; }
            }
            if (achado == null) { System.out.println("MISS: so ha " + n + " athrow(s), pedido #" + idxAlvo); return; }
            InsnList rep = new InsnList();
            rep.add(new InsnNode(Opcodes.POP));                 // descarta a exceção
            Type rt = Type.getReturnType(mn.desc);
            switch (rt.getSort()) {
                case Type.VOID: rep.add(new InsnNode(Opcodes.RETURN)); break;
                case Type.OBJECT: case Type.ARRAY:
                    rep.add(new InsnNode(Opcodes.ACONST_NULL)); rep.add(new InsnNode(Opcodes.ARETURN)); break;
                case Type.LONG: rep.add(new InsnNode(Opcodes.LCONST_0)); rep.add(new InsnNode(Opcodes.LRETURN)); break;
                case Type.FLOAT: rep.add(new InsnNode(Opcodes.FCONST_0)); rep.add(new InsnNode(Opcodes.FRETURN)); break;
                case Type.DOUBLE: rep.add(new InsnNode(Opcodes.DCONST_0)); rep.add(new InsnNode(Opcodes.DRETURN)); break;
                default: rep.add(new InsnNode(Opcodes.ICONST_0)); rep.add(new InsnNode(Opcodes.IRETURN));
            }
            mn.instructions.insertBefore(achado, rep);
            mn.instructions.remove(achado);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
                protected String getCommonSuperClass(String a, String b) { return "java/lang/Object"; }
            };
            cn.accept(cw);
            Files.createDirectories(out.getParent());
            Files.write(out, cw.toByteArray());
            System.out.println("OK: mutado " + cn.name + "." + meth + " athrow#" + idxAlvo);
            return;
        }
        System.out.println("MISS: metodo " + meth + desc + " nao encontrado");
    }
}
