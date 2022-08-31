import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Postfix {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String readFile(String filename) {
        String expression = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                expression = expression + data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return expression;
    }

    public static int operation(Integer number1, Integer number2, String operator) {
        switch (operator) {
            case "+":
                return number2 + number1;
            case "-":
                return number2 - number1;
            case "*":
                return number2 * number1;
            default:
                return number2 / number1;
        }
    }

    public static int postfix(String expression) {
        Stack stk = new Stack();
        for (int charIndex = 0; charIndex < expression.length(); charIndex++) {
            String str = String.valueOf(expression.charAt(charIndex));
            if (isNumeric(str)) {
                stk.push(Integer.parseInt(str));
            } else {
                Integer number1 = (Integer) stk.pop();
                Integer number2 = (Integer) stk.pop();
                stk.push(operation(number1, number2, str));
            }
        }
        return (Integer) stk.pop();
    };

    public static void main(String[] args) {
        String expression = readFile("example.txt");
        System.out.println(postfix(expression));
    }
};
