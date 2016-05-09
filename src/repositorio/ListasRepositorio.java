package repositorio;

import java.util.ArrayList;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.CancionException;
import modelo.ListaReproduccion;
import modelo.Usuario;

public class ListasRepositorio {

	private JDBCTemplate p;

	public ListasRepositorio() {
		p = ConexionBD.conectar();
	}

	public void crearLista(ListaReproduccion lista) {
		String sql = "INSERT INTO ListaReproduccion (usuario, Titulo, Reproducciones)"
				+ " VALUES (?, ?, ?)";
		p.executeSentence(sql, lista.getUsuario(), lista.getTitulo(), lista.getReproducciones());
	}

	public void subirCancionLista(int idCancion, int idLista) {
		String sql = "INSERT INTO ListaCancion (cancion, idLista)"
				+ " VALUES (?, ?)";
		p.executeSentence(sql, idCancion, idLista);
	}

	public Cancion seleccionarLista(int id) throws CancionException {
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion, Archivo FROM Cancion,ListaReproduccion,ListaCancion"
				+ " WHERE ListaReproduccion.id = '"+id+"' and ListaReproduccion.idLista = Cancion.ID";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		Cancion cancion = null;
		if (cursor.iterator().hasNext()) {
			cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
					cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
					cursor.getFileInputStream("archivo",-1));
			System.out.println(cancion.toString());
		}
		return cancion;
	}

	public int findReproducciones(int id) {
		String sql = "SELECT Reproducciones FROM ListaReproduccion WHERE ID = " + id;
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		int repros = -1;
		if (cursor.iterator().hasNext()) {
			repros = cursor.getInteger("reproducciones");	
		}
		return repros;
	}

	public void updateReproducciones (int id){
		//		String sql = "UPDATE Cancion SET Reproducciones = " + String.valueOf(findReproducciones(id)) + " WHERE ID = " + id;
		//		p.executeQuery(sql);
	}

	//	public int findMaxId() {
	//		String sql = "SELECT MAX(ID) AS Maxid FROM Cancion";
	//		Cursor cursor = p.executeQueryAndGetCursor(sql);
	//		return cursor.getInteger("Maxid");
	//	}

	public ArrayList<Cancion> findCanciones (int id, Usuario u) throws CancionException {
		String sql = "SELECT Cancion.ID, Nombre, Artista, Genero, Cancion.Reproducciones,"
				+ " Duracion, Archivo FROM Cancion,ListaReproduccion,ListaCancion"
				+ " WHERE ListaReproduccion.id = '"+id+"' and "
				+ "ListaReproduccion.id = ListaCancion.idLista "
				+ "and ListaReproduccion.usuario = '"+u.getCorreo()+"' and ListaCancion.cancion = Cancion.ID ORDER BY Cancion.ID DESC";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<Cancion> listaCanciones = new ArrayList<>();
		int i = 1; //Max 10 canciones
		while (cursor.iterator().hasNext() && i < 10){
			Cancion cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
					cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
					null);
			cursor.iterator().next();
			listaCanciones.add(cancion);
			System.out.println(cancion.toString());
			i++;
		}
		System.out.println("Acabo de listar");
		return listaCanciones;
	}

	public ArrayList<ListaReproduccion> findMasReproducciones(Usuario u) throws CancionException{
		String sql = "SELECT id, Titulo, Reproducciones FROM ListaReproduccion WHERE usuario = '"+u.getCorreo()+"' ORDER BY Reproducciones DESC";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();
		int i = 1; //Max 10 canciones
		while (cursor.iterator().hasNext() && i < 10){
			ListaReproduccion listaReproduccion = new ListaReproduccion(cursor.getInteger("id"),
					cursor.getString("usuario"),cursor.getInteger("Reproducciones"),cursor.getString("Titulo"));
			cursor.iterator().next();
			listasReproduccion.add(listaReproduccion);
			System.out.println(listaReproduccion.toString());
			i++;
		}
		System.out.println("Acabo de listar");
		return listasReproduccion;
	}

	public int getIdLista(String titulo, Usuario u) {
		String sql = "SELECT * FROM ListaReproduccion WHERE usuario = '"+u.getCorreo()+"' AND titulo='"+titulo+"'";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		int idLista = -1;
		if (cursor.iterator().hasNext()) {
			idLista = cursor.getInteger("id");
		}
		return idLista;
	}
}
