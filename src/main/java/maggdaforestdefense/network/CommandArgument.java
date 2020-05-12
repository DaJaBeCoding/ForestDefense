/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.network;

/**
 *
 * @author David
 */
public class CommandArgument {

    private static final String SEPARATOR = "=";
    private String name, value;

    public CommandArgument(String n, String val) {
        try {
            if (!(isValid(n) && isValid(val))) {
                throw new NotValidStringException();
            }
            name = n;
            value = val;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isValid(String s) {
        char[] charArr = s.toLowerCase().toCharArray();
        for (char currChar : charArr) {
            if (currChar < 'a' || 'z' < currChar) {
                if (currChar != '.') {
                    try {
                        Double.parseDouble(new String(new char[]{currChar}));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return name + SEPARATOR + value;
    }

    public static CommandArgument fromString(String s) {
        String[] splitted = s.split(SEPARATOR);
        return new CommandArgument(splitted[0], splitted[1]);
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }

    public class NotValidStringException extends Exception {

    }
}