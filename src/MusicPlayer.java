// Kanwarpal Brar
// ICS3U0
// gameOn Project: 12/05/2019
// This class handles the playing of music files; it is invoked by other classes
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.sound.sampled.Clip;
public class MusicPlayer {
    // Setup variables
    private static AudioInputStream audioInputStream;
    private static Clip clip;

    public static void playSound(String src) {  // This method sets up an audio stream with a specific audio file, then begins to play that file through the default audio device
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(src).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }//play sound
}
