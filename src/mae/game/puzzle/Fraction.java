package mae.game.puzzle;

import java.awt.Graphics2D;

public class Fraction {
	private String numerator, denominator;
	private double numerator2, denominator2 = 1;
	private double value;
	private String text;
	
	public Fraction(String value) {
		numerator = "0";
		denominator = "0";
		if(!value.contains("/")) {
			this.value = convert(value);
			text = convertDecToFrac(this.value);
		} else {
			text = value;
		}
		String[] ss = text.split("/");
	    numerator = ss[0];
	    denominator = ss[1];
	}
	
	public int getNumerator() {
		return (int) convert(numerator);
	}
	
	public int getDenominator() {
		return (int) convert(denominator);
	}
	
	public double getValue() {
		return value;
	}
	
	public String convertDecToFrac(double decimal) {
		String stringNumber = String.valueOf(decimal);
	    int numberDigitsDecimals = stringNumber.length() - 1 - stringNumber.indexOf('.');
	    denominator2 = 1;
	    for (int k = 0; k < numberDigitsDecimals; k++) {
	        decimal *= 10;
	        denominator2 *= 10;
	    }
	    numerator2 = decimal;
	    double greatestCommonFactor = greatestCommonFactor(numerator2, denominator2);
	    int n = (int) Math.round(numerator2 / greatestCommonFactor);
	    int d = (int) Math.round(denominator2 / greatestCommonFactor);
	    return n+"/"+d;
	}

    public static double greatestCommonFactor(double num, double denom) {
        if (denom == 0) {
            return num;
        } else if (denom == 1000) {
        	if (num == 333) {
        		denom = denom - 1;
	        } else if (num == 143) {
	        	denom = denom + 1;
	        } else if (num == 83) {
	        	denom = denom - 4;
	        } else if (num == 42) {
	        	denom = denom + 8;
	        }
    	}
        return greatestCommonFactor(denom, num % denom);
    }
	
	public void printFraction(Graphics2D g2, int x, int y) {
		if(getNumerator()==0) {
			g2.drawString("a", x, y+16);
			g2.drawString("-", x, y+32);
			g2.drawString("b", x, y+48);
		} else {
			g2.drawString(getNumerator()+"", x, y+16);
			g2.drawString("-", x, y+32);
			g2.drawString(getDenominator()+"", x, y+48);
		}
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
}
