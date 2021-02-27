package com.hari;

import java.util.Scanner;

public class BigIntegerTest {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	Scanner in = new Scanner(System.in);

    	try{
    		BigInteger firstNumber;
        	BigInteger secondNumber;
    	 
	        System.out.println("Enter first number: ");
	        firstNumber = inputBigIntNumber();
	
	        System.out.println("Enter second number: ");
	        secondNumber = inputBigIntNumber();
	        
	        
	        System.out.println("Enter your option like ADD/SUB :");
	        String option = in.nextLine();
	        
	        if("ADD".equalsIgnoreCase(option)){
	        	System.out.println("The result of addition is: " + firstNumber.plus(secondNumber));
	        }else if("SUB".equalsIgnoreCase(option)){
	        	System.out.println("The result of substraction is: " + firstNumber.minus(secondNumber));
	        }else{
	        	System.out.println("Given input type not implemented");
	        }

    	}catch(Exception e){
    		System.out.println("Entered format is not correct");
    	}
        
        

    }

    private static BigInteger inputBigIntNumber(){

        String str = scanner.nextLine();
        return new BigInteger(str);
            
        
    }
}
