package testSistema;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.junit.Test;

import bd.ConexionBD;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.ListaReproduccion;
import modelo.Usuario;
import modelo.UsuarioException;
import repositorio.CancionRepositorio;
import repositorio.ListasRepositorio;
import repositorio.UsuarioRepositorio;

public class testListaReproduccion {
	
	private JDBCTemplate p;
	private UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
	private ListasRepositorio listaRepositorio = new ListasRepositorio();
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	private Usuario u;

	@Test
	public void testcrearListaReproduccion() throws UsuarioException {
		p = ConexionBD.conectar();
		String pass = "123456";
		String user = p.executeQueryBuscar("SELECT Correo FROM Usuario WHERE Usuario.Contrasena = '" + pass + "'");
		u = usuarioRepositorio.seleccionarUsuario(user);
		
		int numListas = p.executeQueryCount("SELECT COUNT(*) FROM ListaReproduccion");
		
		ListaReproduccion lr = new ListaReproduccion(0, u.getCorreo(), 0, "listaPrueba");
		listaRepositorio.crearLista(lr);
		
		int numListas2 = p.executeQueryCount("SELECT COUNT(*) FROM ListaReproduccion");
		assertEquals(numListas, numListas2-1);
	}
	
	@Test
	public void testBorrarListaReproduccion() throws UsuarioException{
		p = ConexionBD.conectar();
		String pass = "123456";
		String user = p.executeQueryBuscar("SELECT Correo FROM Usuario WHERE Usuario.Contrasena = '" + pass + "'");
		u = usuarioRepositorio.seleccionarUsuario(user);
		
		String sql = "DELETE FROM ListaReproduccion WHERE ListaReproduccion.Titulo LIKE 'listaPrueba'";
		p.executeSentence(sql);
	}
	
	/**
	 * se añade una cancion a la lista de reproduccion test
	 * @throws UsuarioException
	 */
	@Test
	public void testAnadirCancionListaReproduccion() throws UsuarioException {
		p = ConexionBD.conectar();
		String pass = "123456";
		String user = p.executeQueryBuscar("SELECT Correo FROM Usuario WHERE Usuario.Contrasena = '" + pass + "'");
		u = usuarioRepositorio.seleccionarUsuario(user);
	
		int idCancion = cancionRepositorio.buscarCancion("titulo");
		listaRepositorio.subirCancionLista(idCancion,2);
		
	}

}
