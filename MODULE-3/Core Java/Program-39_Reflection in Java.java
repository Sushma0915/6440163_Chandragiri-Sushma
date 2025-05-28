public class SampleClass {
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }

    private int add(int a, int b) {
        return a + b;
    }
}
import java.lang.reflect.*;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        // Load class dynamically by name
        Class<?> clazz = Class.forName("SampleClass");

        System.out.println("Methods of class " + clazz.getName() + ":");

        // List all declared methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.print(method.getName() + "(");
            Class<?>[] params = method.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                System.out.print(params[i].getSimpleName());
                if (i < params.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }

        // Create instance of the class
        Object instance = clazz.getDeclaredConstructor().newInstance();

        // Invoke public method greet(String)
        Method greetMethod = clazz.getMethod("greet", String.class);
        greetMethod.invoke(instance, "Alice");

        // Invoke private method add(int, int)
        Method addMethod = clazz.getDeclaredMethod("add", int.class, int.class);
        addMethod.setAccessible(true);  // allow access to private method
        Object result = addMethod.invoke(instance, 5, 7);
        System.out.println("Result of add(5,7): " + result);
    }
}