package asm;
public class MyClassLoader extends ClassLoader  
{  
    @SuppressWarnings("unchecked")  
    public  Class defineClassByName(String name,byte[] b,int off,int len){   
        Class clazz = super.defineClass(name,b, off, len);  
        return clazz;   
      }   
} 