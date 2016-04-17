package modelo;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class PausablePlayer {

    private final static int NOT_STARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;

    // the player actually doing all the work
    private final Player player;

    // locking object used to communicate with player thread
    private final Object playerLock = new Object();

    // status variable what player thread is doing/supposed to do
    private int playerStatus = NOT_STARTED;

    public PausablePlayer(final InputStream inputStream) throws JavaLayerException {
        this.player = new Player(inputStream);
    }

    public PausablePlayer(final InputStream inputStream, final AudioDevice audioDevice) throws JavaLayerException {
        this.player = new Player(inputStream, audioDevice);
    }

    /**
     * Starts playback (resumes if paused)
     */
    public void play() throws JavaLayerException {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOT_STARTED:
                	iniciarCancion();
                    break;
                case PAUSED:
                    resume();
                    break;
                case PLAYING:
                	resume();
                	break;
                default:
                    break;
            }
        }
    }
    
    
    public void iniciarCancion(){
    	playerStatus = PLAYING;
    	final Runnable r = new Runnable() {
            public void run() {
                playInternal();
            }
        };
        final Thread t = new Thread(r);
        t.setDaemon(true);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED.
     */
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING.
     */
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public boolean stop() {
        synchronized (playerLock) {
        	if(playerStatus == PLAYING){
        		playerStatus = NOT_STARTED;
               // playerLock.notifyAll();
        	}
        	return playerStatus == NOT_STARTED;
        }
    }

    private void playInternal() {
        while (playerStatus != FINISHED) {
        	if(playerStatus == NOT_STARTED){
        		break;
        	}
            try {
                if (!player.play(1)) {
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        close();
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = NOT_STARTED;
        }
        try {
        	
            player.close();
            
            int qq = 5;
            player.play();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }
    
    public boolean isPlaying() {
    	return playerStatus == PLAYING;
    }
    
    public boolean isPaused() {
    	return playerStatus == PAUSED;
    }
    
    public boolean isNotStarted() {
    	return playerStatus == NOT_STARTED;
    }

}