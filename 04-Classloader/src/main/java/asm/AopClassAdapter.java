package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class AopClassAdapter extends ClassVisitor implements Opcodes {

    public AopClassAdapter(int api, ClassVisitor cv) {
        super(api, cv);
    }
}