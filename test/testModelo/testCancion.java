package testModelo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.*;

import modelo.Cancion;
import modelo.CancionException;

public class testCancion {
	
	private static final int ID = 1;
	private static final String NOMBRE = "Nombre";
	private static final String ARTISTA = "Artista";
	private static final String GENERO = "Pop";
	private static final int REPRODUCCIONES = 0;	
	private static final int DURACION = 150;
	
	
	@Test
	public void testCancionOK() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, ARTISTA, GENERO, REPRODUCCIONES, DURACION, fileInput);
		assertEquals(cancion.toString(), ID +"-"+ NOMBRE +"-"+ ARTISTA +"-"+ GENERO
				+"-"+ REPRODUCCIONES +"-"+ DURACION);
	}
	
	@Test (expected = CancionException.class)
	public void testIdNegativo() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(-1, NOMBRE, ARTISTA, GENERO, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testNombreVacio() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, "", ARTISTA, GENERO, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testCancionNombreNull() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, null, ARTISTA, GENERO, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testArtistaVacio() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, "", GENERO, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testArtistaNull() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, null, GENERO, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testCancionGeneroVacio() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, ARTISTA, "", REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testGeneroNull() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, ARTISTA, null, REPRODUCCIONES, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testReproduccionMayorIgual0() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, ARTISTA, GENERO, -2, DURACION, fileInput);
	}
	
	@Test (expected = CancionException.class)
	public void testDuracionMayor0() throws FileNotFoundException, CancionException {
		File file = new File("download.mp3");
		FileInputStream fileInput = new FileInputStream(file);
		Cancion cancion = new Cancion(ID, NOMBRE, ARTISTA, GENERO, REPRODUCCIONES, 0, fileInput);
	}
}
