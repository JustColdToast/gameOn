// Kanwarpal Brar
// ICS3U0
// gameOn Project: 12/05/2019
// This class handles the creation of both villain characters and hero characters, specifically their base stats
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Math;
public class characterBuild {
    private int baseHP;
    private int baseSPD;
    private int baseATK;
    private int baseDEF;
    private String specType;
    private JLabel sprite;  // This image variable represents the sprite for this character
    private int[] xy = {0,0};
    private int[] dxy = {0,0};
    private int spriteW = 50;
    private int spriteH = 60;

    // Below is the constructor for the character class, it sets up the basic states for the villain/hero which is being created
    characterBuild(int basehp, int basespd, int baseatk, int basedef, String spectype, String spritesrc) {
        if (spectype.equalsIgnoreCase("speed")) {
            // Class specialization for speed based character
            baseHP = basehp;
            baseSPD = basespd*2;
            baseATK = (int)(baseatk*1.25);
            baseDEF = (int)(basedef*.75);
        } else if(spectype.equalsIgnoreCase("attack")) {
            // Class specialization for attack based character
            baseHP = (int)(basehp*1.25);
            baseSPD = basespd;
            baseATK = baseatk*2;
            baseDEF = (int)(basedef*1.25);
        } else if(spectype.equalsIgnoreCase("defense")) {
            // Class specialization for defense based character
            baseHP = (int)(basehp*1.25);
            baseSPD = (int)(basespd*.75);
            baseATK = baseatk;
            baseDEF = basedef*2;
        } else if(spectype.equalsIgnoreCase("health")) {
            // Class specialization for health based character
            baseHP = basehp*2;
            baseSPD = (int)(basespd*.75);
            baseATK = baseatk;
            baseDEF = (int)(basedef*1.25);
        } else {
            baseHP = basehp;
            baseSPD = basespd;
            baseATK = baseatk;
            baseDEF = basedef;
        }
        specType = spectype;
        sprite = new JLabel(new ImageIcon(spritesrc));
    }  // End of constructor
    // The below class handles the setting of character sprites; each object has a unique sprite
    public void loadSprite(String src, int x, int y) {
        sprite = new JLabel(new ImageIcon(src));
        spriteW = xy[0];
        spriteH = xy[1];
    }
    // baseHP Getter and Setter
    public int getBaseHP() {
        return this.baseHP;
    }
    public void setBaseHP(int newhp) {
        this.baseHP = newhp;
    }
    // baseSPD Getter and Setter
    public int getBaseSPD() {
        return this.baseSPD;
    }
    public void setBaseSPD(int newspd) {
        this.baseSPD = newspd;
    }
    // baseATK Getter and Setter
    public int getBaseATK() {
        return this.baseATK;
    }
    public void setBaseATK(int newatk) {
        this.baseATK = newatk;
    }
    // baseDEF Getter and Setter
    public int getBaseDEF() {
        return this.baseDEF;
    }
    public void setBaseDEF(int newdef) {
        this.baseDEF = newdef;
    }
    // specType Getter and Setter
    public String getSpecType() {
        return this.specType;
    }
    // Sprite getter
    public JLabel getSprite() { return sprite; }
    // sprite xy coords getter and setter
    public int[] getXY() { return xy;}
    public void setXY(int x, int y) { this.xy[0] = x; this.xy[1] = y; }
    // Movement ratio xy adjustment
    public int[] getDXY() { return dxy;}
    // getter for height of sprite
    public int getSpriteH() { return spriteH;}
    // getter for width of sprite
    public int getSpriteW() { return spriteW;}
}  // End of class