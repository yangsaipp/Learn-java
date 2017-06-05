package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AopClassAdapter extends ClassVisitor implements Opcodes {

    public AopClassAdapter(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    	System.out.println("==AopClassAdapter.visitMethod(): name="+ name);
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.startsWith("test")) {
            mv = new AopMethodVisitor(this.api, mv, name, desc);
        }
        return mv;
    }

}