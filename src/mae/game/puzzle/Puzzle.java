package mae.game.puzzle;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.util.Arrays;

import mae.game.Entity;
import mae.game.GamePanel;
import mae.game.object.SumType;

public class Puzzle extends Entity {
	GamePanel gp;
	private String text;
	private SumType sum;
	private PuzzleType puzzle;
	protected int input = 0;
	private int count = 0;
	private double[] ans = new double[5];

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
		super(gp);
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
		case D_PERCENTAGE:return findDPercentage();
		case FRACTION:return findFraction(input);
		case ALGEBRA:return findAlgebra(input);
		case MEASURE:return findMeasurement();
		case TIME:return findTime();
		case GEOMATRY:return findGeomatry();
		case WEIGHT:return findWeight();
		case STAT:return findStatistic();
		case RADICAL:return findRadical(input);
		case GRAPH:return findGraph(input);
		case PROBABIL:return findProbability(input);
		default: return -145;
		}
	}
	public boolean isNotNull(){
		if(text == "X") {
			return false;
		}
		return true;
	}
	public void setInput(int input) {
		this.input = input;
	}
	public int getInputCount() {
		return input;
	}
	public PuzzleType getPuzzleType() {
		return puzzle;
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
	    String regex = "[ \\=]";
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
	
	public void drawMissing(Graphics2D g2, String text, int x, int y) {
		char sumType = '+';
		switch(sum) {
		case PLUS: sumType='+'; break;
		case MINUS: sumType='-'; break;
		case MULTI: sumType='*'; break;
		case DIVIDE: sumType='/'; break;
		default: sumType='+'; break;
		}
		text = text.replace(' ', sumType);
		g2.drawString(text, x, y);
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
	    else if(ss[1].contains("?")) {
	        pos = 1;}
	    else {
	        pos = 2;}
	    if(pos == 0) {
	    	double a;
	        ss[1] = ss[1].replaceAll("[^\\d.]", "");
	    	a =convert(ss[2]) / convert(ss[1]) * 10;
	    	return a;
	    }else if(pos == 1) {
	        double a;
	        ss[1] = ss[1].replaceAll("[^\\d.]", "");
	    	a =convert(ss[2]) / convert(ss[0]) * 100;
	    	return a;
	    }else if(pos == 2) {
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
	
	public double findDPercentage() {
	    String regex = "[\\ \\%]";
	    String[] ss = text.split(regex);
        double a = convert(ss[1]) * (convert(ss[3])/100);
		if(ss[0].contains("Decrease")) {
	    	return convert(ss[1]) - a;
	    } else if(ss[0].contains("Increase")) {
	    	return convert(ss[1]) + a;
	    }
		return 0;
	}
	
	public double findFraction(int input) {
	    String regex = "[ \\=]";
	    String[] ss = text.split(regex);
	    Fraction f1 = new Fraction(ss[0]);
	    Fraction f2 = new Fraction(ss[1]);
	    Fraction f3 = new Fraction(ss[2]);
	    int pos = 0;
	    int upper1=0, upper2=0, upper3=0;
	    int lower1=0, lower2=0, lower3=0;
	    double upper=0, lower=0;
	    //Equalise factors
	    if(sum == SumType.PLUS||sum == SumType.MINUS) {
		    if (f1.getNumerator()==0) {
		    	pos = 0;
		    	int multi1 = lcm(f2.getDenominator(),f3.getDenominator())/f2.getDenominator();
		    	int multi2 = lcm(f2.getDenominator(),f3.getDenominator())/f3.getDenominator();
		    	upper2 = f2.getNumerator()*multi1;
		    	upper3 = f3.getNumerator()*multi2;
		    	lower2 = f2.getDenominator()*multi1;
		    	lower3 = f3.getDenominator()*multi2;
		    	lower=lower2;
		    } else if (f2.getNumerator()==0) {
		    	pos = 1;
		    	int multi1 = lcm(f1.getDenominator(),f3.getDenominator())/f1.getDenominator();
		    	int multi2 = lcm(f1.getDenominator(),f3.getDenominator())/f3.getDenominator();
		    	upper1 = f1.getNumerator()*multi1;
		    	upper3 = f3.getNumerator()*multi2;
		    	lower1 = f1.getDenominator()*multi1;
		    	lower3 = f3.getDenominator()*multi2;
		    	lower=lower3;
		    } else if (f3.getNumerator()==0) {
		    	pos = 2;
		    	int multi1 = lcm(f1.getDenominator(),f2.getDenominator())/f1.getDenominator();
		    	int multi2 = lcm(f1.getDenominator(),f2.getDenominator())/f2.getDenominator();
		    	upper1 = f1.getNumerator()*multi1;
		    	upper2 = f2.getNumerator()*multi2;
		    	lower1 = f1.getDenominator()*multi1;
		    	lower2 = f2.getDenominator()*multi2;
		    	lower=lower1;
		    }
		    upper = calculate(pos, upper1+"", upper2+"", upper3+"");
	    } else {
		    if (f1.getNumerator()==0) {
		    	pos = 0;
		    } else if (f2.getNumerator()==0) {
		    	pos = 1;
		    } else if (f3.getNumerator()==0) {
		    	pos = 2;
		    }
	    	upper1 = f1.getNumerator();
	    	upper2 = f2.getNumerator();
	    	upper3 = f3.getNumerator();
	    	lower1 = f1.getDenominator();
	    	lower2 = f2.getDenominator();
	    	lower3 = f3.getDenominator();
		    upper = calculate(pos, upper1+"", upper2+"", upper3+"");
		    lower = calculate(pos, lower1+"", lower2+"", lower3+"");
	    }

	    int divider = gcd(upper, lower);
	    upper = upper/divider;
	    lower = lower/divider;
	    if(input == 0) {
		    return upper;
	    } else if (input == 1) {
	    	return lower;
	    }
		return -145;
	}
	//Least common multiple
	public static int lcm(double number1, double number2) {
	    if (number1 == 0 || number2 == 0) {
	        return 0;
	    }
	    int absNumber1 = (int) Math.abs(number1);
	    int absNumber2 = (int) Math.abs(number2);
	    int absHigherNumber = Math.max(absNumber1, absNumber2);
	    int absLowerNumber = Math.min(absNumber1, absNumber2);
	    int lcm = absHigherNumber;
	    while (lcm % absLowerNumber != 0) {
	        lcm += absHigherNumber;
	    }
	    return lcm;
	}
	// Greatest common divider
    static int gcd(double a, double b)
    {
        // stores minimum(a, b)
    	int i;
        if (a < b)
            i = (int) a;
        else
            i = (int) b;
 
        // take a loop iterating through smaller number to 1
        for (i = i; i > 1; i--) {
 
            // check if the current value of i divides both
            // numbers with remainder 0 if yes, then i is
            // the GCD of a and b
            if (a % i == 0 && b % i == 0)
                return i;
        }
 
        // if there are no common factors for a and b other
        // than 1, then GCD of a and b is 1
        return 1;
    }
	
	public void drawFraction(Graphics2D g2, String text, int x, int y, boolean res) {
	    String regex = "[ \\=]";
	    String[] ss = text.split(regex);
	    Fraction f1 = new Fraction(ss[0]);
	    Fraction f2 = new Fraction(ss[1]);
	    Fraction f3 = new Fraction(ss[2]);
	    x-=10;
	    y+=10;
	    f1.printFraction(g2, x, y);
	    switch (sum) {
		case PLUS: g2.drawString("+", x+16, y+32);break;
		case MINUS: g2.drawString("-", x+16, y+32);break;
		case MULTI: g2.drawString("*", x+16, y+32);break;
		case DIVIDE: g2.drawString("/", x+16, y+32);break;
		default: g2.drawString("+", x+16, y+32);break;
	    }
	    f2.printFraction(g2, x + 32, y);
	    g2.drawString("=", x+48, y+32);
	    f3.printFraction(g2, x + 64, y);
	}
    
    public double findAlgebra(int input) {
	    String regex = "[+\\-\\*\\=]";
	    String[] ss = text.split(regex);
	    double count = 0;
	    double res = 0;
	    double whole = 0, squrd = 0;
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
		    			whole += convert(ss[i]);
		    		} else if(Character.isDigit(ss[i].charAt(ss[i].length()-1))) {
		    			ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    			squrd += convert(ss[i]);
		    		} else if(ss[i].length() == 1) {
		    			switch(sum) {
		    			case PLUS: whole++;
		    			case MULTI: squrd++;
		    			}
		    			ans[0]++;
		    		}
	    			if(whole==0) {
	    				ans[0] = squrd;
	    			} else {
	    				ans[0] = whole;
	    			} if (squrd != 0) {
	    				ans[1] = squrd + 1;
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
	    	return ans[input];
	    }
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
	    	if(ss[1].contains("?")) {
		    	ss[1] = ss[1].replaceAll("[^\\d.]", "");
		        value = convert(ss[2]) + convert(ss[3]) + convert(ss[4]);
		        return calculate(pos, ss[1], ""+value, ""+ans);
		    } else if(ss[2].contains("?")) {
		    	ss[2] = ss[2].replaceAll("[^\\d.]", "");
		        value = convert(ss[1]) + convert(ss[3]) + convert(ss[4]);
		        return calculate(pos, ss[2], ""+value, ""+ans);
		    } else if(ss[3].contains("?")) {
		    	ss[3] = ss[3].replaceAll("[^\\d.]", "");
		        value = convert(ss[1]) + convert(ss[2]) + convert(ss[4]);
		        return calculate(pos, ss[3], ""+value, ""+ans);
		    } else if(ss[4].contains("?")) {
		    	ss[4] = ss[4].replaceAll("[^\\d.]", "");
		        value = convert(ss[1]) + convert(ss[2]) + convert(ss[3]);
		        return calculate(pos, ss[4], ""+value, ""+ans);
		    }
	    }
	    
	    return -145;
    }
    public void drawGeomatry(Graphics2D g2, String text, int x, int y) {
    	x += gp.tileSize;
    	y += gp.tileSize/2;
	    String regex = "[\\,\\(\\)]";
	    String[] ss = text.split(regex);
		g2.setStroke(new BasicStroke());
	    if(ss[0].contains("Triangle=")) {
			g2.drawPolygon(new int[] {x, x+30, x+60}, new int[] {y+50, y, y+50}, 3);
		    x-=gp.tileSize/2;
		    for(int i = 0; i < ss.length; i++) {
		    	if(ss[i].contains("?")) {
		    		g2.drawString("?", x, y);
		    	} else { 
			    	ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    		g2.drawString(ss[i], x, y);
		    	}
		    	x+=30;
		    	if(i==0 || i==2) {
		    		y+=50;
		    	}else if(i==1) {
		    		y-=50;
		    	}
		    }
	    }else if(ss[0].contains("Square=")) {
			g2.drawPolygon(new int[] {x,x,x+60,x+60}, new int[] {y, y+50, y+50, y}, 4);
		    x-=gp.tileSize;
		    for(int i = 0; i < ss.length; i++) {
		    	if(ss[i].contains("?")) {
		    		g2.drawString("?", x, y);
		    	} else { 
			    	ss[i] = ss[i].replaceAll("[^\\d.]", "");
		    		g2.drawString(ss[i], x, y);
		    	}
		    	x+=60;
		    	if(i==2) {
		    		y+=50;
			    	x-=120;
		    	}
		    }
	    }
    }
    
    public double findWeight() {
    	if(text.contains("lb")) {
	    	text = text.replaceAll("[^\\d.]", "");
    		return convert(text) * 2.20462;
    	} else if(text.contains("mg")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) / 1000000;
    	} else if(text.contains("ton")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) * 1000;
    	} else if(text.contains("0g")) {
    		text = text.replaceAll("[^\\d.]", "");
    		return convert(text) / 1000;
    	} 
    	return -145;
    }
    
    public double findStatistic() {
	    String regex = "[\\(\\)]";
	    String[] ss = text.split(regex);
	    //int pos = 0;
	    double sum = 0;
	    String regex2 = "[,]";
	    String[] sss = ss[1].split(regex2);
	    if(ss[0].contains("mean")) {
	    	for(int i=0; i<sss.length; i++) {
	    		sum+=convert(sss[i]);
	    	}
	    	return sum / sss.length;
	    	
	    } else if(ss[0].contains("mode")) {
	    	int maxCount = 0;
	    	for (int i = 0; i < sss.length; ++i) {
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
	    } else if(ss[0].contains("medium")) {
	    	int middle = sss.length/2;
	    	double[] nss = new double[sss.length];
        	for (int i = 0; i < sss.length; i++) {
        		nss[i] = convert(sss[i]);
			}
	    	Arrays.sort(nss);
	        if (nss.length%2 == 1) {
	            return nss[middle];
	        } else {
	            return (nss[middle-1] + nss[middle])/ 2.0;
	        }
	    } else if(ss[0].contains("range")) {
	    	Arrays.sort(sss);
	        sum = convert(sss[sss.length-1]) - convert(sss[0]);
	        return sum;
	    }
	    return -145;
    }
    
    public double findRadical(int input) {
	    String regex = "[\\=\\√]";
	    String[] ss = text.split(regex);
	    //double[] result = new double[2];
	    double outside = 1;
        //double inside = convert(ss[1]);
        double[] simplified = { 1, convert(ss[1]) };
        
        // Check if it's already a perfect square
	    ss[1] = ss[1].replaceAll("[^\\d.]", "");
        outside = Math.sqrt(convert(ss[1]));
        if (outside == Math.floor(outside))
        {
            simplified[0] = (long)outside;
            simplified[1] = 1;
            if(ss[0] != "") {
            	return convert(ss[0]) * simplified[0];
            }
            return simplified[0];
        }

        // Find all the squares that could be factors and see if they are
        for (long factor = 2, sqr = 4; sqr <= (convert(ss[1]) / 2); factor++, sqr = (factor * factor))
        {
            // Is this square a factor?
            double in = (double)convert(ss[1]) / sqr;
            if (in == Math.floor(in))
            {
                simplified[0] = factor;
                simplified[1] = (long)in;
                //if(ss[2] != "") {
                //	return convert(ss[0]) * simplified[input];
                //}
                return simplified[input];
            }
        }

        // Otherwise, since it hasn't simplified, return the original radical
        return simplified[0];
    	//return -145;
    }
    
    public double findGraph(int input) {
	    String regex = "[+\\*\\=\\/]";
	    String[] ss = text.split(regex);
	    String regex2 = "[\\,\\(\\)]";
	    String[] point = ss[0].split(regex2);
    	String[] point2 = ss[1].split(regex2);
	    if(point[0].contains("Transform")) {
	    	if(input == 0) {
			    if(point2[4].contains("right")) {
			    	return convert(point[2]) + convert(point2[5]);
			    } else if(point2[4].contains("left")) {
			    	return convert(point[2]) - convert(point2[5]);
			    }
	    	} else if(input == 1) {
			    if(point2[1].contains("up")) {
			    	switch (sum) {
			        case MULTI: return convert(point[1]) * convert(point2[2]);
			        default: return convert(point[1]) + convert(point2[2]);
			    	}
			    } else if(point2[1].contains("down")) {
			    	switch (sum) {
			        case DIVIDE: return convert(point[1]) / convert(point2[2]);
			        default: return convert(point[1]) - convert(point2[2]);
			    	}
			    }
	    	}
	    } else if(point[0].equals("ReflectX")) {
	    	if(input == 0) {
	    		return convert(point[1]);
	    	} else {
		    	double x = convert(point[2]);
		    	x = x - x*2;
		    	return x;
	    	}
	    } else if(point[0].equals("ReflectY")) {
	    	if(input == 0) {
		    	double y = convert(point[1]);
		    	y = y - y*2;
		    	return y;
	    	} else {
	    		return convert(point[2]);
	    	}
	    } else if(point[0].equals("Rotate90")) {
	    	if(input == 0) {
		    	return convert(point[2]);
	    	} else {
		    	double x = convert(point[1]);
		    	x = x - x*2;
	    		return x;
	    	}
	    } else if(point[0].equals("Rotate-90")) {
	    	if(input == 0) {
		    	double y = convert(point[2]);
		    	y = y - y*2;
	    		return y;
	    	} else {
		    	return convert(point[1]);
	    	}
	    } else if(point[0].equals("Rotate180")) {
	    	if(input == 0) {
		    	double y = convert(point[1]);
		    	y = y - y*2;
		    	return y;
	    	} else {
		    	double x = convert(point[2]);
		    	x = x - x*2;
		    	return x;
	    	}
	    }
    	return -145;
    }
    
    public void drawGraph(Graphics2D g2, String text, int x, int y, boolean res, double ans1, double ans2) {
    	int lineX = x;
    	int lineY = y;
    	int pointX=x;
    	int pointY=y;
    	int resPointX=x;
    	int resPointY=y;
    	int start = -2;
    	int length = (gp.tileSize/4)-4;
	    String regex = "[+\\*\\=\\/]";
	    String[] ss = text.split(regex);
	    String txt = "";
	    if(ss[0].contains("Transform")) {
	    	start = -2;
	    	length = (gp.tileSize/4)-4;
	    	txt = ss[1];
	    } else if(ss[0].contains("Reflect")) {
	    	start = -5;
	    	length = (gp.tileSize/4)-7;
	    	txt = ss[1];
	    } else if(ss[0].contains("Rotate")) {
	    	start = -5;
	    	length = (gp.tileSize/4)-7;
	    	txt = ss[1];
	    }
	    String regex2 = "[,\\(\\)]";
	    String[] point = ss[0].split(regex2);
    	for(int i = length; i > start; i--) {
			g2.setStroke(new BasicStroke(1));
    		if(i == 0) {
    			g2.setStroke(new BasicStroke(2));
    		}

    	    if(ss[0].contains("Transform")) {
    	    	if(i == (int)(convert(point[1]))){
        			pointY=lineY;
        		}
    	    } else if(ss[0].contains("Reflect")||ss[0].contains("Rotate")) {
    	    	if(i == (int)(convert(point[2]))){
        			pointY=lineY;
        		}
    	    }
    	    if(i == ans2) {
    	    	resPointY=lineY;
    	    }
    		g2.drawLine(x, lineY, x +gp.tileSize+1, lineY);
    		lineY = lineY + 5;
    	}
    	for(int i = start; i < length; i++) {
			g2.setStroke(new BasicStroke(1));
    		if(i == 0) {
    			g2.setStroke(new BasicStroke(2));
    		}
    		if(ss[0].contains("Transform")) {
	    		if(i == (int)(convert(point[2]))){
	    			pointX=lineX;
	    		}
    		} else if(ss[0].contains("Reflect")||ss[0].contains("Rotate")) {
    	    	if(i == (int)(convert(point[1]))){
        			pointX=lineX;
        		}
    	    }
    	    if(i == ans1) {
    	    	resPointX=lineX;
    	    }
    		g2.drawLine(lineX, y, lineX, y +gp.tileSize+1);
    		lineX = lineX + 5;
    	}
	    if(ss[0].contains("Transform")) {
	    	txt = ss[1];
	    } else if(ss[0].contains("Reflect")||ss[0].contains("Rotate")) {
	    	txt = point[0];
	    }
	    if(res) {
	    	drawPoint(g2, (int)(resPointX-0.5), (int)(resPointY-0.5));
	    }
    	drawPoint(g2, (int)(pointX-0.5), (int)(pointY-0.5));
		g2.setFont(g2.getFont().deriveFont(1, 18.0F));
    	//g2.drawString(txt, pointX, pointY);
    	//g2.drawString(txt, x-gp.tileSize/2, y-gp.tileSize/3);
    	lineY = y-gp.tileSize/3;
    	for(String lines: gp.ui.breakLines(txt, 9, ",")) {
    		g2.drawString(lines, x-gp.tileSize/3, lineY);
    		lineY += gp.tileSize/3;
    	}
    }
    
    public void drawPoint(Graphics2D g2, int x, int y) {
	    //g2.setColor(Color.BLUE);
    	g2.drawOval(x,y, 3, 3);
    	g2.fillOval(x, y, 3, 3);
    }
    
    public double findProbability(int input) {
	    String regex = "[+\\-\\*\\=\\/\\(]";
	    String[] ss = text.split(regex);
	    String regex2 = "[,]";
	    String[] numbers = ss[1].split(regex2);
	    double count = 0;
	    if(ss[0].contains("even")) {
	    	for(String n : numbers) {
		        n = n.replaceAll("[^\\d.]", "");
		    	if(isEven(convert(n))) {
		    		count++;
		    	}
		    }
	    	//return count;
	    } else if(ss[0].contains("odd")) {
	    	for(String n : numbers) {
		        n = n.replaceAll("[^\\d.]", "");
		    	if(!isEven(convert(n))) {
		    		count++;
		    	}
		    }
	    	//return count;
	    } else if(ss[0].contains("prime")) {
	    	for(String n : numbers) {
		        n = n.replaceAll("[^\\d.]", "");
		    	if(isPrime(convert(n))) {
		    		count++;
		    	}
		    }
	    	//return count;
	    }
	    if(input==0) {
	    	if(ss[2].contains("?0")) {
	    		double a = (count*100) / numbers.length;
	    		return a / 10;
	    	} else if(ss[2].contains("%")) {
	    		double a = (count*100) / numbers.length;
	    		return a;
	    	} else {
	    		return count;
	    	}
	    } else {
	    	return numbers.length;
	    }
    	//return count;
    }
    
    public static boolean isWhole(double input) {
    	if(input % 1 == 0) { return true; }
    	else { return false; }
    }
    
    public static boolean isEven(double input) {
    	if(input % 2 == 0) { return true; }
    	else { return false; }
    }
    public static boolean isPrime(double n)
    {
        // Corner case
        if (n <= 1) {
            return false;
        }

        // Check from 2 to n-1
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static int largestPos(double[] arr) {
    	// Initialize maximum element 
        double max = arr[0]; 
        int pos = 0;
      	// Traversing and comparing max element
        for (int i = 0; i < arr.length; i++) {
         // If current element is greater than max
            if (arr[i] > max) {
                // Then update max element
                max = arr[i]; 
                pos = i;
            }
        }
        return pos; 
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
    
    public void drawPuzzle(Graphics2D g2) {}
	
	public void update() {
		if(isCorrect || isWrong) {
			spriteCounter++;
			if (spriteCounter > 60) {
				isCorrect = false;
				isWrong = false;
				spriteCounter=0;
			}
		}
	}
}
