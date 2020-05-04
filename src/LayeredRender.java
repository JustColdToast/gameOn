// Kanwarpal Brar
// ICS3U0
// gameOn Project: 12/05/2019
// This class handles the rendering of all graphical components, as well as calculating battle damage
import javax.swing.*;
import java.awt.Color;
import java.lang.Math;
import java.util.Random;
class LayeredRender extends JFrame{
    public JLayeredPane pane;
// Create musicplayer object
    public static MusicPlayer backgroundMusic = new MusicPlayer();
// Create a hero character
    public static characterBuild hero = new characterBuild(100,
             25,
             25,
             15,
             "defense",
             "./src/HeroIdle.gif");
    public static JLabel heroHP = new JLabel(String.valueOf(hero.getBaseHP()));  // Create hero HP label
    // Create a villain character
    public static characterBuild vill = new characterBuild(100,
            25,
            30,
            20,
            "speed",
            "./src/SlimeIdle.gif");
    public static JLabel villHP = new JLabel(String.valueOf(vill.getBaseHP()));  // Create villain HP Label

     // Class constructor
     LayeredRender() {
         // Setup primary Frame
         super("Action");
         setSize(1366,768);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set fullscreen
         setUndecorated(true);  // Removes header bar

         // Get Layered pane object
         pane = getLayeredPane();

         // Setup and render background image
         JLabel background = new JLabel(new ImageIcon("./src/background.png"));
         background.setBounds(0,-250,1920,1080);
         pane.add(background, 3);  // Adds Background to layers
         
         // Setup labels for hero health and villain health
         heroHP.setBounds(50, 340, 300, 150);
         villHP.setBounds(1300, 340, 300, 150);
         pane.add(heroHP, 3, 4);
         pane.add(villHP, 3, 3);
     }
     public static void main(String[] args) throws Exception{  // Main method; executor of all functions
         LayeredRender frame = new LayeredRender();  // Instantiate a new rendering object
         // Call the Hero sprite and set it's location
         hero.getSprite().setBounds(0, 300, 500, 500);
         // Call the Villain sprite and set it's location
         vill.getSprite().setBounds(850, 240, 600, 600);
         // Add both Sprites to the game below
         frame.pane.add(vill.getSprite(), 0, 0);
         frame.pane.add(hero.getSprite(),0, 1);  // Render hero sprite
         // Begin rendering for the Frame
         frame.setVisible(true);  // Set the frame to visible
         Thread.sleep(1000);
         // Set up music player
         backgroundMusic.playSound("./src/BattleTheme.wav");  // Start the main battle loop music
         //-------------------------------------------------------------------------------------------------------------
         // Being the start of all game functions
         while ((hero.getBaseHP() > 0) && (vill.getBaseHP() > 0)) {  // start of loop
             if ((hero.getBaseHP() <= 0) || (vill.getBaseHP() <=0)) {  // Check if hero or villain should be dead
                 break;  // Ends attack loop
             }
             // Calculate Speed modifier for characters; affects attack accuracy
             double heroSpeed = (Math.random()*7)+1 + (hero.getBaseSPD())/100;
             double villSpeed = (Math.random()*7)+1 + (vill.getBaseSPD())/100;
             if (heroSpeed > villSpeed) {  // If hero is faster, he attacks
                 attack(hero, vill);
                 if (Math.random()>0.5) {  // If random number is more than .5 then the hero will dash attack
                     dashRight(hero);
                     shake(vill);
                 } else {  // Otherwise the hero will jump attack
                     jump(hero);
                     shake(vill);
                 }
                 // Below function checks if villain should be dead; gets HP, and updates the health label
                 if (vill.getBaseHP() <= 0) {
                     villHP.setText("Dead");
                 } else {
                     villHP.setText(String.valueOf(vill.getBaseHP()));
                 }
             } else if (villSpeed > heroSpeed) {  //  If Villain is faster, he attacks
                 attack(vill, hero);
                 if (Math.random()>0.5) {  // If random number is more than .5 then the villain will dash attack
                     dashLeft(vill);
                     shake(hero);
                 } else {  // Otherwise the villain will jump attack
                     jump(vill);
                     shake(hero);
                 }
                 // Below function checks if hero should be dead; gets HP, and updates the health label
                 if (hero.getBaseHP() <= 0) {
                     heroHP.setText("Dead");
                 } else {
                     heroHP.setText(String.valueOf(hero.getBaseHP()));
                 }
             } else {  // if Villain and Hero have the same speed (low chance)
                 if (Math.random()>0.5) {
                     shake(hero);  // Hero will shake
                 } else {
                     shake(vill);  // Villain will shake
                 }
             }
             System.out.println("Hero: "+hero.getBaseHP()+" Vill: "+vill.getBaseHP());
             Thread.sleep(500);  // Wait 500ms
         }  // Mainloop end
         System.out.println("Done");
         Thread.sleep(200);  // 200ms delay
         if (hero.getBaseHP() < 0) {
             death(hero);
         } else if (vill.getBaseHP() < 0) {
             death(vill);
         }

     }
     // Method for making a character jump
     private static void jump(characterBuild character) throws Exception {
         for (int counter = 0; counter<=10; counter++) {  // Go down 2 units 10 times, once every 8ms
             character.getSprite().setBounds(character.getSprite().getX(),character.getSprite().getY()+2, character.getSprite().getWidth(), character.getSprite().getHeight());
             character.setXY(character.getXY()[0], character.getXY()[1]+2);
             Thread.sleep(8);
         }
         for (int counter = 0; counter<=50; counter++) {  // Go up 5 units, 50 times, once every 5ms
             character.getSprite().setBounds(character.getSprite().getX(),character.getSprite().getY()-5, character.getSprite().getWidth(), character.getSprite().getHeight());
             character.setXY(character.getXY()[0], character.getXY()[1]+5);
             Thread.sleep(5);
         }
         Thread.sleep(100);  // Pause in the air for 100ms
         for (int counter = 46; counter >=0; counter--) {  // Go down 5 units, 46 times, one every 5ms
             // 46 is used over 50 to compensate for a positional bug
             character.getSprite().setBounds(character.getSprite().getX(),character.getSprite().getY()+5, character.getSprite().getWidth(), character.getSprite().getHeight());
             character.setXY(character.getXY()[0], character.getXY()[1]-5);
             Thread.sleep(5);
         }
         MusicPlayer.playSound("./src/Smash.wav");  // Play a thud sound
         Thread.sleep(100);  // 100ms delay at end of function for buffer
     }
     // Method for making a character dash right
     private static void dashRight(characterBuild character) throws Exception {
         for (int x = 0; x <= 13; x++) {  // Quadratic function x^2 for x = 0 to 15, moving to the right x^2 units
             character.getSprite().setBounds(character.getSprite().getX()+(x*x),character.getSprite().getY(), character.getSprite().getWidth(), character.getSprite().getHeight());
             character.setXY(character.getXY()[0]+(x*x), character.getXY()[1]);
             Thread.sleep(6);  // Pause for 6ms after each movement
         }
         MusicPlayer.playSound("./src/Dash Attack.wav");  // Play dashing attack sound
         Thread.sleep(100);
         for (int x = 0; x <= 13; x++) {  // Quadratic function x^2 for x = 0 to 15, moving to the left x^2 units
             character.getSprite().setBounds(character.getSprite().getX()-(x*x),character.getSprite().getY(), character.getSprite().getWidth(), character.getSprite().getHeight());
             character.setXY(character.getXY()[0]-(x*x), character.getXY()[1]);
             Thread.sleep(8);  // Pause for 8ms after each movement
         }
         Thread.sleep(100);  // 100ms end buffer period
     }
    // Method for making a character dash left
    private static void dashLeft(characterBuild character) throws Exception {
        for (int x = 0; x <= 13; x++) {  // Quadratic function x^2 for x = 0 to 15, moving to the left x^2 units
            character.getSprite().setBounds(character.getSprite().getX()-(x*x),character.getSprite().getY(), character.getSprite().getWidth(), character.getSprite().getHeight());
            character.setXY(character.getXY()[0]-(x*x), character.getXY()[1]);
            Thread.sleep(6);
        }
        MusicPlayer.playSound("./src/Dash Attack.wav");  // Play dashing attack sound
        Thread.sleep(100);
        for (int x = 0; x <= 13; x++) {  // Quadratic function x^2 for x = 0 to 15, moving to the right x^2 units
            character.getSprite().setBounds(character.getSprite().getX()+(x*x),character.getSprite().getY(), character.getSprite().getWidth(), character.getSprite().getHeight());
            character.setXY(character.getXY()[0]+(x*x), character.getXY()[1]);
            Thread.sleep(8);
        }
        Thread.sleep(100);  // 100ms end buffer period
    }
    private static void shake(characterBuild character) throws Exception {
         int x = character.getSprite().getX();
         int y = character.getSprite().getY();
        MusicPlayer.playSound("./src/Whoosh.wav");  // Play dashing attack sound
        for (int count = 0, ratio = 1; count <= 5; count++, ratio*=-1) {  // Quadratic function x^2 for x = 0 to 15, moving to the right x^2 units
            character.getSprite().setBounds(character.getSprite().getX()+ratio*(int)(Math.random()*40),
                    character.getSprite().getY()+ratio*(int)(Math.random()*15),
                    character.getSprite().getWidth(),
                    character.getSprite().getHeight());
            Thread.sleep(100);
        }
        character.getSprite().setBounds(x,y,character.getSprite().getWidth(), character.getSprite().getHeight());
        Thread.sleep(300);
    }
    private static void attack(characterBuild attacker, characterBuild defender) {
         defender.setBaseHP((int)((defender.getBaseHP() - Math.abs(attacker.getBaseATK() - (defender.getBaseDEF()*0.55)))+(Math.random()*5)));
         // Random modifier is used for critical hits
    }
    private static void death(characterBuild character) throws Exception{
        for (int counter = 0; counter<=70; counter++) {  // Go up 5 units, 50 times, once every 5ms
            character.getSprite().setBounds(character.getSprite().getX(),character.getSprite().getY()-5, character.getSprite().getWidth(), character.getSprite().getHeight());
            character.setXY(character.getXY()[0], character.getXY()[1]+5);
            Thread.sleep(5);
        }
        Thread.sleep(100);  // Pause in the air for 100ms
        character.getSprite().setIcon(new ImageIcon("./src/X_d.png"));
        for (int counter = 100; counter >=0; counter--) {  // Go down 5 units, 46 times, one every 5ms
            // 46 is used over 50 to compensate for a positional bug
            character.getSprite().setBounds(character.getSprite().getX(),character.getSprite().getY()+5, character.getSprite().getWidth(), character.getSprite().getHeight());
            character.setXY(character.getXY()[0], character.getXY()[1]-5);
            Thread.sleep(2);
        }
    }
}
