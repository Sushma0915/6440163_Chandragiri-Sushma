public class PatternMatchingSwitchExample {

    public static void main(String[] args) {
        printTypeInfo(42);
        printTypeInfo("Hello, Java!");
        printTypeInfo(3.14);
        printTypeInfo(true);
        printTypeInfo(null);
    }

    public static void printTypeInfo(Object obj) {
        // Enhanced switch with pattern matching (Java 21+)
        String result = switch (obj) {
            case Integer i   -> "It's an Integer with value: " + i;
            case String s    -> "It's a String with text: \"" + s + "\"";
            case Double d    -> "It's a Double with value: " + d;
            case Boolean b   -> "It's a Boolean with value: " + b;
            case null        -> "It's null!";
            default          -> "Unknown type: " + obj;
        };

        System.out.println(result);
    }
}