package asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AopMethodVisitor extends MethodVisitor implements Opcodes {

	String name; 
	String desc;
	
    public AopMethodVisitor(int api, MethodVisitor mv, String name, String desc) {
        super(api, mv);
        this.name = name;
        this.desc = desc;
    }
    
    @Override
    public void visitCode() {
    	System.out.println("====AopMethodVisitor.visitCode()");
    	if("testSay(Ljava/lang/String;)V".equals(name + desc)) {
    		this.visitVarInsn(ALOAD, 1);
    		this.visitMethodInsn(INVOKESTATIC, "asm/AopInteceptor", "before", "(Ljava/lang/String;)V", false);
    	}else {
    		this.visitMethodInsn(INVOKESTATIC, "asm/AopInteceptor", "before", "()V", false);
    	}
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
    	System.out.println("====AopMethodVisitor.visitInsn(opcode): opcode=" + opcode);
    	if(opcode == ICONST_1) {
    		this.visitMethodInsn(INVOKESTATIC, "asm/AopInteceptor", "after", "()V", false);
    	}
        if (opcode >= IRETURN && opcode <= RETURN)// �ڷ���֮ǰ����after ���롣
            this.visitMethodInsn(INVOKESTATIC, "asm/AopInteceptor", "after", "()V", false);
        super.visitInsn(opcode);
    }

	/**
	 * 
	 * @see org.objectweb.asm.MethodVisitor#visitLineNumber(int, org.objectweb.asm.Label)
	 */
	@Override
	public void visitLineNumber(int line, Label start) {
		System.out.println(line + " : " + start);
		super.visitLineNumber(line, start);
	}
    
    

}