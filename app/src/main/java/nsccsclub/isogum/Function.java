package nsccsclub.isogum;

import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A basic function object for the ISO GUM calculator. Contains methods
 * relating to calculating uncertainty.
 * Created by csconway on 4/21/2016.
 */
public class Function implements Comparable<Function>{
    /**
     * The unique name of the function, must be tested in SQLLite data base for uniqueness.
     */
    private String name;
    /**
     * The function to evaluate.
     */
    private String function;
    /**
     * The unique DB id of the function, must be generated by the databass itself, use built in
     * fetching methods in the database to summon the function to avoid error.
     */
    private long id;

    /**
     * the symbolic derivative of the function needs to be implemented
     */
    private String derivative;


    /**
     * Creates a function object with no database id, useful for simple unstored calculations,
     * or initially storing the object.
     * @param name The name of the function.
     * @param function The functino to evaluate.
     */
    public Function(String name, String function){
        this.setName(name);
        this.setFunction(function);
        // id has not been set by database yet
        this.setId(-1);
        this.setDerivative("");
    }

    /**
     * Creates a function object with no database id, useful for simple unstored calculations,
     * or initially storing the object.
     * @param name The name of the function.
     * @param function The functino to evaluate.
     */
    public Function(String name, String function, String derivative){
        this.setName(name);
        this.setFunction(function);
        // id has not been set by database yet
        this.setId(-1);
        this.setDerivative(derivative);
    }

    /**
     * The full constructor for the function, use this whenever working with an existing object in
     * the database, or better yet the getFunction method in DBHandler.
     * @param name The unique name of the function.
     * @param function The function to evaluate
     * @param id The database id of the function.
     */
    public Function(String name, String function, String derivative, long id){
        this.setName(name);
        this.setFunction(function);
        this.setId(id);
        this.setDerivative(derivative);
    }

    /**
     * Gives the uniques name of the function object.
     * @return The name of the function.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the function.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The name must be unique, use appropriate isDuplicate method in the DBHandler
     *         Class to guarentee this.</li>
     *     </ul>
     * </div>
     * @param name The name of the function object.
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Gives the function associated with the object.
     * @return The function to evaluate.
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the function of the object, make sure it is valid with various class methods before
     * storing.
     * @param function The mathematical function to evaluate.
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * The unique database id of the function. Useful for updating the object in the SQLite
     * database.
     * @return The database identifier of the object.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the database identifier of the object, should only be used in conjunction with methods
     * that provide the database id in DBHandler.
     * @param id The databae identifier of the object.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Tests content equality of two function objects, ignores database id.
     * @param function The function to test equality to.
     * @return True for equal, false for not equal.
     */
    public boolean equals(Function function){
        boolean nameTest = this.name.compareTo(function.getName())==0;
        boolean functionTest = this.function.compareTo(function.getFunction())==0;
        return nameTest && functionTest;
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than.
     *
     * COMPARES BASED ON LEXOGRAPHIC NAME
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Function another) {
        return this.name.compareTo(another.getName());
    }

    /**
     * The generic derivative of the function.
     */
    public String getDerivative() {
        return derivative;
    }

    public void setDerivative(String derivative) {
        this.derivative = derivative;
    }

    public String translator(){
        FunctionParser functionParser = new FunctionParser(this.getFunction());
        ArrayList<FunctionParser.Token> list = functionParser.getTokens();
        Set<String> set = new HashSet<String>();
        Iterator<FunctionParser.Token> iterator = list.iterator();
        FunctionParser.Token token;
        //find all functions and remove duplicates
        while (iterator.hasNext()){
            token = iterator.next();
            if (token.getType()== FunctionParser.Type.VARIABLE){
                //remove parentheses and add
                set.add(token.getValue().toString().
                        substring(1,token.getValue().toString().length()-1));
            }
        }
        Iterator<String> stringIterator =set.iterator();
        String output = "(";
        while (stringIterator.hasNext()){
            output += stringIterator.next()+",";
        }
        output = output.substring(0,output.length()-1);
        output += ") = "+ this.function;
        output = output.replace("[","");
        output = output.replace("]","");
        return "f" + output.toUpperCase();
    }

    public int getNumVariables(){
        FunctionParser functionParser = new FunctionParser(this.getFunction());
        ArrayList<FunctionParser.Token> list = functionParser.getTokens();
        Iterator<FunctionParser.Token> iterator = list.iterator();
        FunctionParser.Token token;
        int count=0;
        //find all functions
        while (iterator.hasNext()){
            token = iterator.next();
            if (token.getType()== FunctionParser.Type.VARIABLE){
                count++;
            }
        }
        return count;
    }
    public Iterator<String> getVariableNames(){
        FunctionParser functionParser = new FunctionParser(this.getFunction());
        ArrayList<FunctionParser.Token> list = functionParser.getTokens();
        Set<String> set = new HashSet<String>();
        Iterator<FunctionParser.Token> iterator = list.iterator();
        FunctionParser.Token token;
        //find all functions and remove duplicates
        while (iterator.hasNext()){
            token = iterator.next();
            if (token.getType()== FunctionParser.Type.VARIABLE){
                //remove parentheses and add
                set.add(token.getValue().toString().
                        substring(1,token.getValue().toString().length()-1));
            }
        }
        Iterator<String> stringIterator =set.iterator();
        return stringIterator;
    }
}
