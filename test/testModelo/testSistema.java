package testModelo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.CancionException;
import repositorio.CancionRepositorio;

public class testSistema {
	
	private JDBCTemplate p;

	/**
	 * añade una cancion
	 * @throws CancionException
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	@Test
	public void testSubirCancion() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		
		p = ConexionBD.conectar();
		int numCanciones = p.executeQueryCount("SELECT COUNT(*) FROM Cancion");
		
		File f = new File ("animals020.mp3");
		CancionRepositorio cancionRepositorio = new CancionRepositorio();
		if (f != null){
			FileInputStream fileInput = new FileInputStream(f);
			AudioFile audioFile = AudioFileIO.read(f);
			int duration = audioFile.getAudioHeader().getTrackLength();
			Cancion cancion = new Cancion(1, "animales", "artista",
					"animal", 0 , duration, fileInput);
			cancionRepositorio.subirCancion(cancion);
		}
		
		p = ConexionBD.conectar();
		int numCanciones2 = p.executeQueryCount("SELECT COUNT(*) FROM Cancion");
		
		assertEquals(numCanciones, numCanciones2-1);
	}
	/**
	 * borra una cancion
	 * @throws CancionException
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	@Test
	public void testBorrarCancion() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		p = ConexionBD.conectar();
		String sql = "DELETE FROM Cancion WHERE Cancion.Nombre LIKE 'animales'";
		p.executeSentence(sql);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void test3() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		File f = new File ("XXX.mp3");
		CancionRepositorio cancionRepositorio = new CancionRepositorio();
		if (f != null){
			FileInputStream fileInput = new FileInputStream(f);
			AudioFile audioFile = AudioFileIO.read(f);
			int duration = audioFile.getAudioHeader().getTrackLength();
			Cancion cancion = new Cancion(1, "animales", "artista",
					"animal", 0 , duration, fileInput);
			cancionRepositorio.subirCancion(cancion);
		}
	}
	
	@Test (expected = FileNotFoundException.class)
	public void test4() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
	
		File f = new File ("");
		CancionRepositorio cancionRepositorio = new CancionRepositorio();
		if (f != null){
			FileInputStream fileInput = new FileInputStream(f);
			AudioFile audioFile = AudioFileIO.read(f);
			int duration = audioFile.getAudioHeader().getTrackLength();
			Cancion cancion = new Cancion(1, "animales", "artista",
					"animal", 0 , duration, fileInput);
			cancionRepositorio.subirCancion(cancion);
		}
	}
	
	@Test (expected = CancionException.class)
	public void test5() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		
		File f = new File ("animals020.mp3");
		CancionRepositorio cancionRepositorio = new CancionRepositorio();
		if (f != null){
			FileInputStream fileInput = new FileInputStream(f);
			AudioFile audioFile = AudioFileIO.read(f);
			int duration = audioFile.getAudioHeader().getTrackLength();
			Cancion cancion = new Cancion(1, "animales", "artista",
					"", 0 , duration, fileInput);
			cancionRepositorio.subirCancion(cancion);
		}
	}
	
	
	@Test 
	public void testSubirBuscarCancion() throws CancionException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		File f = new File ("animals020.mp3");
		CancionRepositorio cancionRepositorio = new CancionRepositorio();
		if (f != null){
			FileInputStream fileInput = new FileInputStream(f);
			AudioFile audioFile = AudioFileIO.read(f);
			int duration = audioFile.getAudioHeader().getTrackLength();
			Cancion cancion = new Cancion(1, "animales", "artista",
					"animal", 0 , duration, fileInput);
			cancionRepositorio.subirCancion(cancion);
		}
		p = ConexionBD.conectar();
		String nombreCancion = p.executeQueryBuscar("SELECT Nombre FROM Cancion WHERE Cancion.Nombre = 'animales'");
		assertEquals("animales",nombreCancion);
	}


}
