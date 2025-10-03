package mae.game.puzzle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Algebra {
	private List<Integer> number = new ArrayList<Integer>();
	private List<String> letter = new ArrayList<String>();
	public String value="", xValue="", yValue="", zValue="";
	boolean hidden = false; 
	private String fullValue;
	
	public Algebra(String input) {
		fullValue = input;
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = fullValue.split(regex);
	    for(String s : ss) {
	    	s = s.replaceAll("\\s", "");
    		System.out.println("value " + s);
	    	if(!s.contains("a") && !s.contains("b") && 
		    	!s.contains("x") && !s.contains("y")  && 
		    	!s.contains("z")) {
				value = s;
		    } else if(s.contains("x")) {
				xValue = s;
		    } else if(s.contains("y")) {
				yValue = s;
		    } else if(s.contains("z")) {
				zValue = s;
		    }
	    	letter.add(s);
	    }
	}
	
	public String add(Algebra input) {
		DecimalFormat format = new DecimalFormat("0.#");
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = input.fullValue.split(regex);
	    String full = fullValue;
	    //Check for whole numbers
	    System.out.println(value +" "+ ss);
	    for(int i = 0; i < ss.length; i++) {
		    if(!ss[i].contains("a") && !ss[i].contains("b") && 
		    	!ss[i].contains("x") && !ss[i].contains("y") && 
		    	!ss[i].contains("z")) {
    	    	ss[i] = ss[i].replaceAll("\\s", "");
		    	System.out.println("num: "  + value + " " + ss[i]);
				double ans = (convert(value) + convert(ss[i]));
    			System.out.println("Answer " + ans);
				if(value!="") {
					//value = ss[i];
					full = full +"+"+ ss[i];
				} else {
					full = full.replace(value,format.format(ans));
				}
		    } else if(ss[i].contains("x")) {
    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
    			double ans = (convert(xValue.replaceAll("[^\\d.]", "")) + convert(ss[i]));
				if(xValue=="") {
					full = full +"+"+ ss[i]+"x";
				} else {
	    			full = full.replace(xValue,format.format(ans)+"x");
				}
		    } else if(ss[i].contains("y")) {
    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
    			double ans = (convert(yValue.replaceAll("[^\\d.]", "")) + convert(ss[i]));
				if(yValue=="") {
					full = full +"+"+ ss[i]+"y";
				} else {
	    			full = full.replace(yValue,format.format(ans)+"y");
				}
		    } else if(ss[i].contains("z")) {
    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
    			double ans = (convert(zValue.replaceAll("[^\\d.]", "")) + convert(ss[i]));
				if(zValue=="") {
					full = full +"+"+ ss[i]+"z";
				} else {
	    			full = full.replace(zValue,format.format(ans)+"z");
				}
		    }
	    }
	    return full;
	}
	
    public List<Integer> getNumber() {
		return number;
	}

	public List<String> getLetter() {
		return letter;
	}

	public String getFullValue() {
		if(hidden) {
		    String regex = "[+\\-\\*\\=]";
		    String[] ss = fullValue.split(regex);
		    String tempValue = fullValue;
			if(algebraLength()==1) {
				if(xValue!="") {
					return "?x";
				} else if(yValue!="") {
					return "?y";
				} else if(zValue!="") {
					return "?z";
				}
			} else if(algebraLength()>=2) {
				ss[0]=ss[0].replaceAll("[0-9]+", "a");
				ss[1]=ss[1].replaceAll("[0-9]+", "b");
			    if(algebraLength()>=3) {
			    	ss[2]=ss[2].replaceAll("[0-9]+", "c");
			    }
			    for(int i = 0; i < algebraLength(); i++) {
			    	if(ss[i].contains("x")) {
			    		tempValue=tempValue.replaceAll("[0-9]+x",ss[i]);
					} else if(ss[i].contains("y")) {
						tempValue=tempValue.replaceAll("[0-9]+y",ss[i]);
					} else if(ss[i].contains("z")) {
						tempValue=tempValue.replaceAll("[0-9]+z",ss[i]);
					}
			    }
			    return tempValue;
			}
		}
		return fullValue;
	}
	
	public boolean isCorrect(String[] input) {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = fullValue.split(regex);
	    boolean matches = false;
		for(int i = 0; i < algebraLength(); i++) {
			System.out.println(input[i] + " " + ss[i]);
			if(input[i]!=null) {
				System.out.println(input[i] + " " + ss[i]);
				if(input[i].equals(ss[i].replaceAll("[^\\d.]", ""))){
					matches = true;
					hidden=false;
				} else {
					return false;
				}
			}
		}
		return matches;
	}
	
	public void hideAlgebra(boolean hidden) {
		this.hidden = hidden;
	}
	
	public int algebraLength() {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = fullValue.split(regex);
	    return ss.length;
	}

	// Function to convert String to double
    public static double convert(String str)
    {
    	double foo;
    	try {
    	   foo = Double.parseDouble(str);
    	}
    	catch (NumberFormatException e) {
    	   foo = 0;
    	}
    	return foo;
    }
}
