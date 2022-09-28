import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;

public class Postfix {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static TokenType getTokenType(String tokenType) {
        if (isNumeric(tokenType)) {
            return TokenType.NUM;
        } else if (Objects.equals(tokenType, "+")) {
            return TokenType.PLUS;
        } else if (Objects.equals(tokenType, "-")) {
            return TokenType.MINUS;
        } else if (Objects.equals(tokenType, "*")) {
            return TokenType.STAR;
        } else if (Objects.equals(tokenType, "/")) {
            return TokenType.SLASH;
        } else {
            return TokenType.EOF;
        }
    }

    public static List<Token> readFile(String filename) {
        List<Token> tokenArray = new ArrayList<Token>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                TokenType tokenType = getTokenType(data);
                if (tokenType == TokenType.EOF) {
                    throw new Exception("Error: Unexpected character: " + data);
                } else {
                    tokenArray.add(new Token(tokenType, data));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tokenArray;
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

    public static int postfix(List<Token> expression) {
        Stack stk = new Stack();
        for (int itemIndex = 0; itemIndex < expression.size(); itemIndex++) {
            Token token = expression.get(itemIndex);
            if (token.type == TokenType.NUM) {
                stk.push(Integer.parseInt(token.lexeme));
            } else {
                Integer number1 = (Integer) stk.pop();
                Integer number2 = (Integer) stk.pop();
                stk.push(operation(number1, number2, token.lexeme));
            }
        }
        return (Integer) stk.pop();
    };

    public static void main(String[] args) {
        List<Token> expression = readFile("example.txt");
        System.out.println(postfix(expression));
    }
};
