package com.hari;
import java.util.ArrayList;

public class BigInteger implements Comparable<BigInteger> {

    private static final char MINUS_CHAR = '-';
    private static final char PLUS_CHAR = '+';

    private ArrayList<Integer> numberDigits = new ArrayList<>();

    private boolean negative;

    private String stringNumber;

    BigInteger(String number){

        if (number.equals("")){
            stringNumber = "0";
            numberDigits.add(0);
        }
        else {
            char firstChar = number.charAt(0);
            if (firstChar == MINUS_CHAR || firstChar == PLUS_CHAR) {
                if (firstChar == MINUS_CHAR)
                    negative = true;

                number = number.substring(1);
            }

            number = number.replaceFirst("^0+(?!$)", "");
            stringNumber = number;

            for (int index = 0; index < number.length(); index++) {
                int curDigNumericVal = Character.getNumericValue(number.charAt(index));

                if (curDigNumericVal == -1)
                    throw new IllegalArgumentException();

                numberDigits.add(curDigNumericVal);
            }
        }
    }

    private boolean isNegative() {
        return negative;
    }

    private void flipNegativity() {
        if (stringNumber == "0")
            return;

        negative = !negative;
        if (stringNumber.charAt(0) == MINUS_CHAR){
            stringNumber = stringNumber.substring(1);
        } else {
            stringNumber = MINUS_CHAR + stringNumber;
        }
    }

    BigInteger plus(BigInteger otherNumber) {

        if (negative && !otherNumber.isNegative()) {
            return otherNumber.minus(new BigInteger(stringNumber));
        }

        if (otherNumber.isNegative()) {
            return minus(new BigInteger(otherNumber.toString()));
        }

        ArrayList<Integer> longerNumber, shorterNumber;
        if (numberDigits.size() >= otherNumber.numberDigits.size()) {
            longerNumber = numberDigits;
            shorterNumber = otherNumber.numberDigits;
        }
        else {
            longerNumber = otherNumber.numberDigits;
            shorterNumber = numberDigits;
        }

        int lengthsDifferences = longerNumber.size() - shorterNumber.size();


        StringBuilder resultString = new StringBuilder();

        int carry = 0;


        for (int index = shorterNumber.size() - 1; index >= 0; index--) {
            int shorterNumberDigit = shorterNumber.get(index);
            int longerNumberDigit = longerNumber.get(index + lengthsDifferences);

            int newDigit = shorterNumberDigit + longerNumberDigit + carry;

            carry = newDigit / 10;
            newDigit = newDigit % 10;

            resultString.append(newDigit);
        }

        for (int index = lengthsDifferences - 1; index >= 0; index--) {
            int currDig = longerNumber.get(index);

            if (currDig + carry == 10) {
                resultString.append(0);
                carry = 1;
            } else {
                resultString.append(currDig + carry);
                carry = 0;
            }
        }

        if (carry > 0)
            resultString.append(carry);

        return new BigInteger(resultString.reverse().toString());
    }

    BigInteger minus(BigInteger otherNumber){

        if (otherNumber.isNegative()) {
            return plus(new BigInteger(otherNumber.stringNumber));
        }

        if (this.compareTo(otherNumber) < 0) {
        	BigInteger result = otherNumber.minus(this);
            result.flipNegativity();
            return result;
        }

        int lengthsDifferences = numberDigits.size() - otherNumber.numberDigits.size();

        StringBuilder resultString = new StringBuilder();

        int carry = 0;

        for (int index = otherNumber.numberDigits.size() - 1; index >=0 ; index--) {
            int biggerNumDig = numberDigits.get(index + lengthsDifferences) - carry;
            int smallerNumDig = otherNumber.numberDigits.get(index);

            carry = 0;

            if (biggerNumDig < smallerNumDig){
                carry = 1;
                biggerNumDig += 10;
            }

            resultString.append(biggerNumDig - smallerNumDig);
        }

        for (int index = lengthsDifferences - 1; index >=0 ; index--) {
            int currDig = numberDigits.get(index);

            if (carry > currDig){
                resultString.append(currDig + 10 - carry);
                carry = 1;
            } else {
                resultString.append(currDig - carry);
                carry = 0;
            }
        }

        return new BigInteger(resultString.reverse().toString());
    }

    private boolean isBigIntZero(BigInteger number) {
        return number.stringNumber.replace("0", "").equals("");

    }

   
    private void multiplyByTen() {
        this.numberDigits.add(0);
        stringNumber += '0';
    }

    @Override
    public int compareTo(BigInteger other) {

        if (isNegative() && !other.isNegative())
             return -1;

        else if (!isNegative() && other.isNegative()){
            return 1;
        }

        else if (isNegative()){
            if (numberDigits.size() > other.numberDigits.size())
                return -1;
            else if (numberDigits.size() < other.numberDigits.size())
                return 1;

            else
                for (int index = 0; index < numberDigits.size(); index++) {

                    if (numberDigits.get(index) > other.numberDigits.get(index))
                        return -1;

                    else if (numberDigits.get(index) < other.numberDigits.get(index))
                            return 1;
                }

                return 0;
        }


        if (numberDigits.size() > other.numberDigits.size()) {
            return 1;
        }

        else if (numberDigits.size() < other.numberDigits.size())
            return -1;

        else
            for (int index = 0; index < numberDigits.size(); index++) {

                if (numberDigits.get(index) > other.numberDigits.get(index))
                    return 1;

                else if (numberDigits.get(index) < other.numberDigits.get(index))
                    return -1;
            }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        BigInteger other = (BigInteger) o;

        return other.toString().equals(stringNumber);
    }

    @Override
    public String toString() {
        return stringNumber;
    }
}