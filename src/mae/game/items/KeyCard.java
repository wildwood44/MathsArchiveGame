package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import mae.game.GamePanel;

public class KeyCard extends Item {
	GamePanel gp;
	private int id;
	private double value;
	private static final DecimalFormat df = new DecimalFormat("0.#");

	public KeyCard(GamePanel gp, int value) {
		super(gp);
		this.gp = gp;
		id = value;
		this.value = value;
		image = setup(("/res/keycard/number_cards"+value), gp.tileSize*2, gp.tileSize*2);
		getImage(image);
		x = 5;
		y = (4 * gp.tileSize) + 5;
	}
	
	public int setCard(int card) {
		if(gp.kc[card].opened || gp.testKey) {
			gp.currentCard = card;
		}
		return gp.currentCard;
	}
	
	public int nextCard(int current) {
		int temp = current;
		temp++;
		if(temp > 9) {
			temp = 0;
		}
		if(!gp.kc[temp].opened && !gp.testKey) {
			temp = nextCard(temp);
		}
		return temp;
	}
	
	public int prevCard(int current) {
		int temp = current;
		temp--;
		if(temp < 0) {
			temp = 9;
		}
		if(!gp.kc[temp].opened && !gp.testKey) {
			temp = prevCard(temp);
		}
		return temp;
	}
	
	public void setValue(double value) {
		System.out.println("Value: " + value);
		this.value = value;
	}
	
	public double useCard() {
		return value;
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
	    if (id == 0) {
			String text = df.format(value) + "";
			int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			int textX = x + gp.tileSize - length/2; 
        	g2.drawString(text, textX, (y+gp.tileSize)-10);
        }
	}
}
