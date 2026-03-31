package petstore.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class JSValidatorAP {

    private static final ScriptEngine engine;

    static {
        ScriptEngineManager manager = new ScriptEngineManager();

        
        ScriptEngine graal = manager.getEngineByName("js");
        engine = (graal != null) ? graal : manager.getEngineByName("nashorn");

        if (engine == null) {
            throw new RuntimeException(
                "No JavaScript engine found! " +
                "Add graalvm js-scriptengine dependency or use Java ≤ 14 (Nashorn)."
            );
        }
        System.out.println("JS Engine loaded: " + engine.getClass().getSimpleName());
    }

  
    public static boolean validate(String responseBody, String jsExpression) {
        try {
            engine.put("responseBody", responseBody);
            Object result = engine.eval(jsExpression);

            if (result instanceof Boolean) {
                return (Boolean) result;
            }

            // Coerce string "true"/"false"
            return Boolean.parseBoolean(result.toString());

        } catch (ScriptException e) {
            throw new RuntimeException(
                "❌ JS validation script error: " + e.getMessage(), e
            );
        }
    }

    public static void assertValid(String responseBody, String jsExpression, String description) {
        boolean result = validate(responseBody, jsExpression);
        if (!result) {
            throw new AssertionError(
                "JS Validation FAILED — " + description +
                "\n  Script: " + jsExpression +
                "\n  Response: " + responseBody
            );
        }
        System.out.println("JS Validation PASSED — " + description);
    }

    // Pre-built common validations 
    
     // Validates that a JSON field equals an expected string value.
     
    public static void assertFieldEquals(String responseBody, String field, String expected) {
        String script = String.format(
            "var obj = JSON.parse(responseBody); obj['%s'] === '%s';",
            field, expected
        );
        assertValid(responseBody, script,
            "Field '" + field + "' should equal '" + expected + "'");
    }

    
     // Validates that a JSON field is not null or empty.
     
    public static void assertFieldNotEmpty(String responseBody, String field) {
        String script = String.format(
            "var obj = JSON.parse(responseBody); " +
            "obj['%s'] !== null && obj['%s'] !== undefined && obj['%s'] !== '';",
            field, field, field
        );
        assertValid(responseBody, script,
            "Field '" + field + "' should not be empty");
    }

    
     // Validates that a numeric JSON field is greater than zero.
     
    public static void assertNumericFieldPositive(String responseBody, String field) {
        String script = String.format(
            "var obj = JSON.parse(responseBody); " +
            "typeof obj['%s'] === 'number' && obj['%s'] > 0;",
            field, field
        );
        assertValid(responseBody, script,
            "Field '" + field + "' should be a positive number");
    }

    
     //Validates that a string field contains a substring.
     
    public static void assertFieldContains(String responseBody, String field, String substring) {
        String script = String.format(
            "var obj = JSON.parse(responseBody); " +
            "obj['%s'] !== undefined && obj['%s'].toString().indexOf('%s') !== -1;",
            field, field, substring
        );
        assertValid(responseBody, script,
            "Field '" + field + "' should contain '" + substring + "'");
    }

    
    public static void assertValidEmail(String responseBody, String field) {
        String script = String.format(
            "var obj = JSON.parse(responseBody); " +
            "var email = obj['%s']; " +
            "/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/.test(email);",
            field
        );
        assertValid(responseBody, script,
            "Field '" + field + "' should be a valid email");
    }

   
    public static void assertMessageContains(String responseBody, String expectedFragment) {
        try {
            engine.put("responseBody", responseBody);
            engine.put("fragment", expectedFragment);
            Object result = engine.eval("responseBody.indexOf(fragment) !== -1;");
            if (!(Boolean) result) {
                throw new AssertionError(
                    "JS Validation FAILED — response should contain '" +
                    expectedFragment + "'\n  Actual: " + responseBody
                );
            }
            System.out.println("JS Validation PASSED — response contains '" + expectedFragment + "'");
        } catch (ScriptException e) {
            throw new RuntimeException("JS engine error", e);
        }
    }
}
