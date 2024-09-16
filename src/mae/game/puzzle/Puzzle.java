package mae.game.puzzle;

import java.util.Iterator;

import mae.game.object.SumType;

public class Puzzle {
	private int a, b, c, d;
	private String text;
	private int res;
	private SumType sum;
	private PuzzleType puzzle;
	private int input = 0;
	private int count = 0;
	
	public Puzzle(String text, SumType sum) {
		this(text, sum, PuzzleType.MISSING);
	}
	
	public Puzzle(String text, SumType sum, PuzzleType puzzle) {
		this(text, 1, sum, puzzle);
		this.text = text;
		this.sum = sum;
		this.puzzle = puzzle;
	}
	
	public Puzzle(String text, int input, SumType sum, PuzzleType puzzle) {
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
		case ALGEBRA:return findAlgebra();
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
	    System.out.println(ss[0] + ss[1]);
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
	    	System.out.println(a + " : " + b);
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
	    System.out.println(input);
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
    
    public double findAlgebra() {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = text.split(regex);
	    for(String s:ss) {
	    	
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
