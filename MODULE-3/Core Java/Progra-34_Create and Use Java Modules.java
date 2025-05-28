module com.utils {
    exports com.utils;
}

// File: com.utils/com/utils/StringUtils.java
package com.utils;

public class StringUtils {
    public static String toUpperCase(String input) {
        return input.toUpperCase();
    }
}

module com.greetings {
    requires com.utils;
}

// File: com.greetings/com/greetings/Main.java
package com.greetings;

import com.utils.StringUtils;

public class Main {
    public static void main(String[] args) {
        String greeting = "hello from com.greetings module!";
        String upper = StringUtils.toUpperCase(greeting);
        System.out.println(upper);
    }
}