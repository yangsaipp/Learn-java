package asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class PeopleAop2Dump implements Opcodes {

	public static byte[] dump() throws Exception {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "main/AsmPeopleAop2", null,
				"main/People", null);

		cw.visitSource("AsmPeopleAop2.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(4, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "main/People", "<init>", "()V",
					false);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lmain/AsmPeopleAop2;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "testSay", "(Ljava/lang/String;)V",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(7, l0);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitLdcInsn(".......AsmPeopleAop2:before.......");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/String;)V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(8, l1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKESPECIAL, "main/People", "testSay",
					"(Ljava/lang/String;)V", false);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(9, l2);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitLdcInsn(".......AsmPeopleAop2:after.......");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/String;)V", false);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(10, l3);
			mv.visitInsn(RETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLocalVariable("this", "Lmain/AsmPeopleAop2;", null, l0, l4, 0);
			mv.visitLocalVariable("word", "Ljava/lang/String;", null, l0, l4, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "testDriver", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(13, l0);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitLdcInsn(".......AsmPeopleAop2:before.......");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/String;)V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(14, l1);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitLdcInsn("Driver.");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/String;)V", false);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(15, l2);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitLdcInsn(".......AsmPeopleAop2:after.......");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/String;)V", false);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(16, l3);
			mv.visitInsn(RETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLocalVariable("this", "Lmain/AsmPeopleAop2;", null, l0, l4, 0);
			mv.visitMaxs(2, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}
