import org.junit.Test;

import java.lang.reflect.Method;

public class MainTest {
    @Test
    public void main() throws Exception {
        Main main = new Main();

        //arg countValidLine
        Class[] parameterTypes = new Class[1];
        //arg type
        parameterTypes[0] = String.class;
        //private method name
        Method method = main.getClass().getDeclaredMethod(
                "execute", String.class);

        Object[] args = new Object[1];
        //allow to access this method
        method.setAccessible(true);

        //do invoke
        String line = "cat a.txt b.txt c.txt";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "grep th.* .*.txt";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "sort a.txt";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "wc a.txt";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "cut c.txt -c -4,3-6,13-";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "cat a.txt b.txt c.txt | grep th.* | sort";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        line = "cat a.txt b.txt c.txt | grep th.* | sort |wc > rs.txt";
        System.out.println("CommandSimulator> " + line);
        method.invoke(main, line);
        //cancel access
        method.setAccessible(false);
    }

}