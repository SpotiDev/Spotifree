package repositorio;

import java.util.ArrayList;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.CancionException;

public class CancionRepositorio {

	private JDBCTemplate p;
	
	public CancionRepositorio() {
		p = ConexionBD.conectar();
	}
	
	public void subirCancion(Cancion cancion) {
		String sql = "INSERT INTO Cancion (nombre, artista, genero, reproducciones, duracion, archivo)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		p.executeSentence(sql, cancion.getNombre(), cancion.getArtista(), cancion.getGenero(),
				cancion.getReproducciones(), cancion.getDuracion(), cancion.getArchivo());
	}
	
	public Cancion seleccionarCancion(int id) throws CancionException {
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion, Archivo FROM Cancion WHERE ID = " + id;
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		Cancion cancion = null;
		if (cursor.iterator().hasNext()) {
			cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
					cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
					cursor.getFileInputStream("archivo"));
			System.out.println(cancion.toString());
		}
		return cancion;
	}
	
//	public int findMaxId() {
//		String sql = "SELECT MAX(ID) AS Maxid FROM Cancion";
//		Cursor cursor = p.executeQueryAndGetCursor(sql);
//		return cursor.getInteger("Maxid");
//	}
	
	public ArrayList<Cancion> findCanciones (String busqueda) throws CancionException {
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion WHERE Cancion.Nombre LIKE '%"+busqueda+"%'" + "OR Cancion.Artista LIKE '%"+busqueda+"%'" + "OR Cancion.Genero LIKE '%"+busqueda+"%'";
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
	
	public ArrayList<Cancion> findLastest () throws CancionException{
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion";
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
	
}
