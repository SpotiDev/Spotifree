package testSistema;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import modelo.Cancion;
import modelo.CancionException;
import modelo.PausablePlayer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class testReproductor {
	
	static Cancion c;
	PausablePlayer player;

	@BeforeClass
	public static void initCancion() throws CancionException, JavaLayerException, FileNotFoundException{
		File file = new File("test_NO_BORRAR.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		c = new Cancion (0, "nombre", "artista", "genero", 0, 120, fileInput);
	}
	
	@Before
	public void initPlayer() throws CancionException, JavaLayerException, FileNotFoundException{
		player = new PausablePlayer(c.getArchivo());
	}
	
	@Test
	public void testPlay() throws JavaLayerException {
		player.play();
		assertTrue(player.isPlaying());
	}
	
	@Test
	public void testPause() throws JavaLayerException {
		player.play();
		player.pause();
		assertTrue(player.isPaused());
	}
	
	@Test
	public void testStop() throws JavaLayerException {
		player.play();
		player.stop();
		assertTrue(!player.isPlaying());
	}

}
