package helpers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.openal.AL10.*;
import org.newdawn.slick.openal.WaveData;

public class Sound {

    private int source;
    
    private String name;
    
    private IntBuffer buffer = BufferUtils.createIntBuffer(3);

    private boolean isPlaying = false;

    public Sound(String type, String fileName, float volume,String name) throws FileNotFoundException{
        this.name = name;
        
        alGenBuffers(buffer);
        
        WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream("src\\res\\" + type + "\\" + fileName + ".wav")));
        alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
        data.dispose();
        
        source = alGenSources();
        
        alSourcei(source, AL_BUFFER,   buffer.get(0) );
        alSourcei(source, AL_BUFFER,   buffer.get(0) );
        alSourcei(source, AL_BUFFER,   buffer.get(0) );
        alSourcef(source, AL_PITCH,    1.0f          );
        alSourcef(source, AL_GAIN,     volume          );

    }
    
    public void play() {
        if (!isPlaying) {
            isPlaying = true;
            alSourcePlay(source);
            System.out.println("Play! " + name);
        }
        
    }
    
    public void stop() {
        if (isPlaying) {
            isPlaying = false;
            System.out.println("Stop! " + name);
            alSourceStop(source);
        }
    }
    
    public void pause() {
        if (isPlaying) {
            isPlaying = false;
            System.out.println("Pause!");
            alSourcePause(source);
        }
        
    }
    
    
    public void loop() {
        if (!isPlaying) {
            isPlaying = true;
            alSourcePlay(source);
            System.out.println("Loop");
            alSourcei(source, AL_LOOPING, AL_TRUE);
        }
    }

    public int getBuffer() {
        return buffer.get(0);
    }
    
    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
}
