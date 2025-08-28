import java.util.Scanner;

public class Main {

    private static final int MAX_EQUATION_LENGTH = 5;
    private static final int MATH_OPERAND_INDEX = 1;
    private static final int EQUAL_OPERAND_INDEX = 3;

    public static void main(String[] args) {
        Scanner inputStream = new Scanner(System.in);
        String equation;
        System.out.printf("Введите простое уровнение, с цифрами от 0 до 9.%nСтрока не может быть больше пяти символов.%nДопустимые математические операции: + и -.%n");
        while(true){
            equation = inputStream.nextLine();
            int stringLength = equation.length();
            if(equation.length() <= MAX_EQUATION_LENGTH)
                break;

            System.out.println("Уравнение не может содержать больше пяти символов. Введите уравнение снова.");
        }

        Character equalOperand = equation.charAt(EQUAL_OPERAND_INDEX);
        if(!equalOperand.equals('=')){
            System.out.println("Уравнение должно содержать знак \"=\".");
            return;
        }

        Character operand = equation.charAt(MATH_OPERAND_INDEX);
        if(!operand.equals('+') && !operand.equals('-')){
            System.out.println("Уравнение не содержит допустимую математическую операцию.");
            return;
        }

        int unknowValueIdx = equation.indexOf('x');
        if(unknowValueIdx < 0){
            System.out.println("Уравнение должно содержать неизвестное (искомую переменную).");
            return;
        }

        Character unknowValue = equation.charAt(unknowValueIdx);
        int variable1, variable2;
        int result = 0;

        if(unknowValueIdx > EQUAL_OPERAND_INDEX){
            variable1 = getVariable(equation, MATH_OPERAND_INDEX-1);
            variable2 = getVariable(equation, MATH_OPERAND_INDEX+1);

            if(variable1 < 0 || variable2 < 0)
                return;

            switch (operand){
                case '+':
                    result = variable1 + variable2;
                    break;
                case '-':
                    result =  variable1 - variable2;
                    break;
            }
        }
        else  if (unknowValueIdx < MATH_OPERAND_INDEX) {

            variable1 = getVariable(equation, EQUAL_OPERAND_INDEX+1);
            variable2 = getVariable(equation, MATH_OPERAND_INDEX+1);

            if(variable1 < 0 || variable2 < 0)
                return;

            switch (operand){
                case '-':
                    result = variable1 + variable2;
                    break;
                case '+':
                    result =  variable1 - variable2;
                    break;
            }

        }
        else{
            variable1 = getVariable(equation, EQUAL_OPERAND_INDEX+1);
            variable2 = getVariable(equation, MATH_OPERAND_INDEX-1);

            if(variable1 < 0 || variable2 < 0)
                return;

            switch (operand){
                case '-':
                    result = variable2 - variable1;
                    break;
                case '+':
                    result =  variable1 - variable2;
                    break;
            }
        }

        System.out.println("X = " + result);
    }

    private static int getVariable(String equation, int charIndex){
        char variable = equation.charAt(charIndex);
        if(isVariableIncorrect(variable)){
            System.out.println("Значение '" + variable + "' недопустимо. Уравнение должно содержать только цифры от 0 до 9.");
            return -1;
        }

        return Character.getNumericValue(variable);
    }

    private static boolean isVariableIncorrect(char variable){
        if(Character.isDigit(variable) && (variable > '0' && variable < '9'))
            return false;

        return true;
    }
}