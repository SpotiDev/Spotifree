package repositorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.CancionException;
import modelo.Genero;
import modelo.GeneroException;

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
	
	public Cancion seleccionarCancion(int id, boolean cache) throws CancionException {
		Cancion cancion = null;
		if (cache){
			String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion WHERE ID = " + id;
			Cursor cursor = p.executeQueryAndGetCursor(sql);
			if (cursor.iterator().hasNext()) {
				cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
						cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
						null);
				try {
					cancion.setArchivo(new FileInputStream(new File("cache/"+id+"_download.mp3")));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(cancion.toString());
			}
		}
		else{
			String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion, Archivo FROM Cancion WHERE ID = " + id;
			Cursor cursor = p.executeQueryAndGetCursor(sql);
			if (cursor.iterator().hasNext()) {
				cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
						cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
						cursor.getFileInputStream("archivo",id));
				System.out.println(cancion.toString());
			}
		}

		return cancion;
	}
	
	public void updateReproducciones (int id) {
		String sql = "UPDATE Cancion SET Reproducciones = Reproducciones + 1 WHERE ID = " + id;
		p.executeSentence(sql);
	}
	
	public ArrayList<Genero> findGenerosMasReproducciones() throws GeneroException {
		String sql = "SELECT Genero, SUM(Reproducciones) AS Reproducciones FROM Cancion GROUP BY Genero ORDER BY Reproducciones DESC";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<Genero> listaGeneros = new ArrayList<>();
		int i = 1; //Max 10 generos
		while (cursor.iterator().hasNext() && i < 10){
			Genero genero = new Genero(cursor.getString("genero"), cursor.getInteger("reproducciones"));
			cursor.iterator().next();
			listaGeneros.add(genero);
			System.out.println(genero.toString());
			i++;
		}
		System.out.println("Acabo de listar géneros");
		return listaGeneros;
	}
	
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
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion ORDER BY ID DESC";
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
	
	
	public ArrayList<Cancion> findMasReproducciones () throws CancionException{
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion ORDER BY Reproducciones DESC";
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
	
	public ArrayList<Integer> findIds () throws CancionException{
		String sql = "SELECT ID FROM Cancion";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<Integer> listaId = new ArrayList<>();
		int i = 1; //Max 10 canciones
		while (cursor.iterator().hasNext() && i < 10){
			listaId.add(cursor.getInteger("id"));
			cursor.iterator().next();
			i++;
		}
		return listaId;
	}
	
	public int buscarCancion(String nombre) {
		String sql = "SELECT * FROM Cancion WHERE nombre = '"+nombre+"'";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		return cursor.getInteger("ID");
	}
}
