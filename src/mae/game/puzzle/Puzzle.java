package mae.game.puzzle;

import java.text.DecimalFormat;
import java.util.Arrays;

import mae.game.GamePanel;
import mae.game.object.SumType;

public class Puzzle {
	GamePanel gp;
	private int a, b, c, d;
	private String text;
	private int res;
	private SumType sum;
	private PuzzleType puzzle;
	private int input = 0;
	private int count = 0;
	private double[] ans = new double[5];
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public Puzzle(GamePanel gp, String text, SumType sum) {
		this(gp, text, sum, PuzzleType.MISSING);
	}
	
	public Puzzle(GamePanel gp, String text, SumType sum, PuzzleType puzzle) {
		this(gp, text, 1, sum, puzzle);
		this.text = text;
		this.sum = sum;
		this.puzzle = puzzle;
	}
	
	public Puzzle(GamePanel gp, String text, int input, SumType sum, PuzzleType puzzle) {
		this.gp = gp;
		this.text = text;
		this.sum = sum;
		this.puzzle = puzzle;
		this.input = input;
	}
	
	public double getAnswer(int input) {
		return findFraction(input);
	}

	public double answer(int input) {
		text.replaceAll("\\s", "");
		switch (puzzle) {
		case EQUALS:return isEqualToNumber();
		case MISSING:return findMissing();
		case PERCENTAGE:return findPercentage();
		case FRACTION:return findFraction(input);
		case ALGEBRA:return findAlgebra(input);
		case MEASURE:return findMeasurement();
		case TIME:return findTime();
		case GEOMATRY:return findGeomatry();
		case WEIGHT:return findWeight();
		case STAT:return findStatistic();
		default: return -145;
		}
	}
	public void setInput(int input) {
		this.input = input;
	}
	public int getInputCount() {
		return input;
	}
	public double isEqualToNumber() {
	    String regex = "=";
	    String[] ss = text.split(regex);
	    int result = 0;
	    for(String s: ss) {
	    	result += convert(s);
	    }
	    return result;
	}
	public double findMissing() {
		// Array of string to store individual strings
	    // after splitting the strings from spaces
	     
	    // Using stringstream to read a string object
	    // and split
	    String regex = "[+\\-\\*\\/\\=]";
	    String[] ss = text.split(regex);
	    
	    int pos = -1;
	     
	    // Find position of missing character
	    if(ss[0].equals("?") && ss[1].equals("?")) {
	    	pos = 3;
	    }
	    else if(ss[0].equals("?")) {
	        pos = 0;}
	    else if(ss[1].equals("?")) {
	        pos = 1;}
	    else {
	        pos = 2;}
	    return calculate(pos, ss[0], ss[1], ss[2]);
	}
	
	public double findPercentage() {
		// Array of string to store individual strings
	    // after splitting the strings from spaces
	     
	    // Using stringstream to read a string object
	    // and split
	    String regex = "[+\\-\\*\\/\\=\\%]";
	    String[] ss = text.split(regex);
	    int pos = -1;
	     
	    // Find position of missing character
	    if(ss[0].equals("?0")) {
	        pos = 0;}
	    else if(ss[2].equals("?")) {
	        pos = 1;}
	    else {
	        pos = 1;}
	    if(pos == 0) {
	    	double a;
	        ss[1] = ss[1].replaceAll("[^\\d.]", "");
	    	a =convert(ss[2]) / convert(ss[1]) * 10;
	    	return a;
	    }
	    else if(pos == 1) {
	        double a;
	        ss[1] = ss[1].replaceAll("[^\\d.]", "");
	    	a = (convert(ss[0])/100) * convert(ss[1]);
	    	if(ss.length > 3) {
	    		String b = ss[3];
		        switch(sum) {
		        case MINUS: a=a + convert(b); break;
		        case MULTI: a=a / convert(b); break;
		        case DIVIDE: a=a * convert(b); break;
		        default: a=a - convert(b); break;
		        }
	    	}
	    	return a;
	    }
		return 0;
	}
	
	public double findFraction(int input) {
	    String regex = "[+\\-\\*\\=\\/]";
	    String[] ss = text.split(regex);
	    String fractions1Upper = ss[0];
	    String fractions1Lower = ss[1];
	    String fractions2Upper = ss[2];
	    String fractions2Lower = ss[3];
	    String fractions3Upper = ss[4];
	    String fractions3Lower = ss[5];
	    int pos = 0;
	    if(input == 0) {
		    if (fractions1Upper.contains("a")) {
		    	pos = 0;
		    } else if (fractions2Upper.contains("a")) {
		    	pos = 1;
		    } else if (fractions3Upper.contains("a")) {
		    	pos = 2;
		    }
		    double upper = calculate(pos, fractions1Upper, fractions2Upper, fractions3Upper);
		    return upper;
	    } else if (input == 1) {
		    if (fractions1Lower.contains("b")) {
		    	pos = 0;
		    } else if (fractions2Lower.contains("b")) {
		    	pos = 1;
		    } else if (fractions3Lower.contains("b")) {
		    	pos = 2;
		    }
		    double lower = calculate(pos, fractions1Lower, fractions2Lower, fractions3Lower);
		    return lower;
	    }
		return -145;
	}

    public static int greatestCommonFactor(int num, int denom) {
        if (denom == 0) {
            return num;
        }
        return greatestCommonFactor(denom, num % denom);
    }
    
    public double findAlgebra(int input) {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = text.split(regex);
	    double count = 0;
	    double res = 0;
	    ans[0] = 0; 
	    ans[1] = 0; 
	    //Check for whole numbers
	    if(!ss[2].contains("a") && !ss[2].contains("b") && 
	    	!ss[2].contains("x") && !ss[2].contains("y")) {
	    	res = convert(ss[2]);
	    }
	    for(int i = 0; i < ss.length; i++) {
		    	if(ss[i].contains("x")) {
		    		if(Character.isDigit(ss[i].charAt(0))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[0] += convert(ss[i]);
		    		} else if(Character.isDigit(ss[i].charAt(ss[i].length()-1))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[0] += convert(ss[i]);
		    		} else if(ss[i].length() == 1) {
		    			ans[0]++;
		    		}
		    	} else if(ss[i].contains("a")) {
		    		if(Character.isDigit(ss[i].charAt(0))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[0] = convert(ss[i]);// * gp.kc[gp.currentCard].useCard();
		    		} else if(Character.isDigit(ss[i].charAt(ss[i].length()-1))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[0] = convert(ss[i]);// * gp.kc[gp.currentCard].useCard();
		    		}
		    	}
		    	//ans[0] = count;
		    //}
		    //if(input == 1 && ss[i] != null) {
		    	if(ss[i].contains("y")) {
		    		if(Character.isDigit(ss[i].charAt(0))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[1] += convert(ss[i]);
		    		} else if(Character.isDigit(ss[i].charAt(ss[i].length()-1))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[1] += convert(ss[i]);
		    		} else if(ss[i].length() == 1) {
		    			ans[1]++;
		    		}
		    	} else if(ss[i].contains("b")) {
		    		if(Character.isDigit(ss[i].charAt(0))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[1] = convert(ss[i]);// * gp.kc[gp.currentCard].useCard();
		    		} else if(Character.isDigit(ss[i].charAt(ss[i].length()-1))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			ans[1] += convert(ss[i]);// * gp.kc[gp.currentCard].useCard();;
		    		}
		    	}
	    }
	    if(res != 0) {
	    	count = findMuliple(ans[0], ans[1], res, input);
	    	return count;
	    } else {
	    	System.out.println("Count  "+input+": "+ans[input]);
	    	return ans[input];
	    }
	    //System.out.println("Count: "+count);
    	//return count;
    }
    
    public double findMuliple(double input1, double input2, double res, int pos) {
    	for(int i = 1; i < 10; i++) {
    		for(int j = 1; j < 10; j++) {
        		if(calculate(2, Double.toString(input1 * i), Double.toString(input2 * j), Double.toString(res)) == res) {
        			if(pos == 0) {
        				return i;
        			} else if(pos == 1) {
        				return j;
        			} else {
        				return res;
        			}
        		}
        	}
    	}
    	return -145;
    }
    
    public double findMeasurement() {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = text.split(regex);
	    System.out.println(ss[0] + " " + ss[1]);
	    if(ss[0].contains("?")){
	    	double convert = toMeter(ss[1]);
	    	convert = fromMeter(""+convert);
	    	return convert;
	    } else if(ss[1].contains("?")){
	    	double convert = toMeter(ss[0]);
	    	convert = fromMeter(""+convert);
	    	return convert;
	    }
	    return -145;
    }
    
    public double toMeter(String unit2){
    	if(unit2.contains("cm")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) / 100;
		} else if(unit2.contains("km")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) * 1000;
		} else if(unit2.contains("mm")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) / 1000;
		}
    	String to = unit2.replaceAll("[^\\d.]", "");
    	return convert(to);
    }
    
    public double fromMeter(String unit2){
    	if(unit2.contains("cm")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) * 100;
		} else if(unit2.contains("km")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) / 1000;
		} else if(unit2.contains("mm")) {
			String to = unit2.replaceAll("[^\\d.]", "");
			return convert(to) * 1000;
		}
    	String to = unit2.replaceAll("[^\\d.]", "");
    	return convert(to);
    }
    
    public double findTime() {
	    String regex = "[+\\-\\*\\/\\=]";
	    String[] ss = text.split(regex);
	    int pos = -1;
	    if(ss[0].contains("?")) {
	        pos = 0;}
	    else if(ss[1].contains("?")) {
	        pos = 1;}
	    else {
	        pos = 2;}
	    if(ss[0].contains("mi")) {
	    	ss[0] = ss[0].replaceAll("[^\\d.]", "");
	    }
	    if(ss[1].contains("min")) {
	    	ss[1] = ss[1].replaceAll("[^\\d.]", "");
	    	ss[1] = ""+ (convert(ss[1]) * 0.016666667);
	    }
    	ss[2] = ss[2].replaceAll("[^\\d.]", "");
	    double i = calculate(pos, ss[0], ss[1], ss[2]);
    	i = round(i, 2);
	    return i;
    }
    
    public double findGeomatry() {
	    String regex = "[+\\-\\*\\/\\,\\(]";
	    String[] ss = text.split(regex);
	    int pos = 0;
	    double ans = 0;
	    double value = 0;
	    if(ss[0].contains("Triangle=")) {
	    	ans = 180;
	    	if(ss[1].contains("?")) {
		    	ss[1] = ss[1].replaceAll("[^\\d.]", "");
		        value = convert(ss[2]) + convert(ss[3]);
		        return calculate(pos, ss[1], ""+value, ""+ans);
		    } else if(ss[2].contains("?")) {
		    	ss[2] = ss[2].replaceAll("[^\\d.]", "");
		        value = convert(ss[1]) + convert(ss[3]);
		        return calculate(pos, ss[2], ""+value, ""+ans);
		    } else if(ss[3].contains("?")) {
		    	ss[3] = ss[3].replaceAll("[^\\d.]", "");
		        value = convert(ss[1]) + convert(ss[2]);
		        return calculate(pos, ss[3], ""+value, ""+ans);
		    }
	    } else if(ss[0].contains("Square=")) {
	    	ans = 360;
	    }
	    
	    return -145;
    }
    
    public double findWeight() {
    	if(text.contains("lb")) {
	    	text = text.replaceAll("[^\\d.]", "");
    		return convert(text) * 2.20462;
    	} else if(text.contains("mg")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) / 1000000;
    	} else if(text.contains("g")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) * 1000;
    	}  else if(text.contains("t")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) / 1000;
    	} 
    	return -145;
    }
    
    public double findStatistic() {
	    String regex = "[\\(]";
	    String[] ss = text.split(regex);
	    int pos = 0;
	    double sum = 0;
	    double value = 0;
	    String regex2 = "[\\,\\)]";
	    String[] sss = ss[1].split(regex2);
	    if(ss[0].contains("mean=")) {
	    	for(int i=0; i<sss.length; i++) {
	    		sum+=convert(sss[i]);
	    	}
	    	return sum / sss.length;
	    	
	    } else if(ss[0].contains("mode=")) {
	    	int maxCount = 0;
	    	for (int i = 0; i < sss.length; ++i) {
	            //System.out.println(sss[i]);
	    		int count = 0;
	            for (int j = 0; j < sss.length; ++j) {
	                if (convert(sss[j]) == convert(sss[i])) ++count;
	            }
	            if (count > maxCount) {
	                maxCount = count;
	                sum = convert(sss[i]);
	            }
	        }
	        return sum;
	    } else if(ss[0].contains("medium=")) {
	    	int middle = sss.length/2;
	    	Arrays.sort(sss);
	        if (sss.length%2 == 1) {
	        	System.out.println(sss[middle]);
	            return convert(sss[middle]);
	        } else {
	            return convert((sss[middle-1]) + convert(sss[middle])) / 2.0;
	        }
	    }
	    return -145;
    }
	
	public String drawFraction() {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = text.split(regex);
	    String[] fractions1 = ss[0].split("\\/");
	    String[] fractions2 = ss[1].split("\\/");
	    String[] fractions3 = ss[2].split("\\/");
	    String upper = fractions1[0] + " " + fractions2[0] + "  " + fractions3[0];
	    String calc = "";
        switch(sum) {
        case MINUS: calc = "-"; break;
        case MULTI: calc = "*"; break;
        case DIVIDE: calc = "/"; break;
        default: calc = "+"; break;
        }
	    String middle = "£-" + calc + "- =-";
	    String lower = "£"+fractions1[1] + " " + fractions2[1] + "  " + fractions3[1];
		return upper + middle + lower;
	}
	
	public String printPuzzle() {
		//text.replaceFirst("pc", "of");
		//if(puzzle == PuzzleType.FRACTION) {
		//    text=drawFraction();
		//}
		if(text != null) {
			return text;
		}
		return "X";
	}
	
	public boolean isCorrect(double input) {
		if(text!="X") {
			if(count >= this.input) {
				count = 0;
			}
			if(input != answer(count++)) {
				return false;
			}
			return true;
		}
		return false;
	}
    // Function to convert String to integer
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
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public double calculate(int pos, String num1, String num2, String result) {

    	if(pos == 0)
	    {
	        String b,c;
	        b = num2;
	        c = result;
	         
	        // Using convert() to convert strings to int
	        double a;
	        switch(sum) {
	        case MINUS: a=convert(c) + convert(b); break;
	        case MULTI: a=convert(c) / convert(b); break;
	        case DIVIDE: a=convert(c) * convert(b); break;
	        default: a=convert(c) - convert(b); break;
	        }
	        return a;
	    }
	    else if(pos==1)
	    {
	        String a,c;
	        a = num1;
	        c = result;
	         
	        // Using convert() to convert strings to int
	        double b;
	        switch(sum) {
	        case MINUS: b=convert(a) - convert(c); break;
	        case MULTI: b=convert(c) / convert(a); break;
	        case DIVIDE: b=convert(c) * convert(a); break;
	        default: b=convert(c) - convert(a); break;
	        }
	        return b;
	    }
	    else if(pos == 2)
	    {
	        String b,a;
	        a = num1;
	        b = num2;
	         
	        // Using convert() to convert strings to int
	        double c;
	        switch(sum) {
	        case MINUS: c=convert(a) - convert(b); break;
	        case MULTI: c=convert(a) * convert(b); break;
	        case DIVIDE: c=convert(a) / convert(b); break;
	        default: c=convert(a) + convert(b); break;
	        }
	         
	        return c;
	    }
	    else if(pos == 3) {
	        String ab;
	        ab = result;
	         
	        // Using convert() to convert strings to int
	        double a;
	        switch(sum) {
	        case MINUS: a=convert(ab) * 2; break;
	        case MULTI: a=cuberoot(convert(ab)); break;
	        case DIVIDE: a=0; break;
	        default: a=convert(ab) / 2; break;
	        }
	         
	        return a;
	    }
    	return -145;
    }
    
    static double cuberoot(double n)
    {
        int ans = 0;
 
        for (int i = 1; i <= n; ++i) {
           
            // checking every number cube
            if (i * i <= n) {
                ans = i;
            }
        }
        return ans;
    }
}
