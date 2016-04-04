package repositorio;

import java.util.ArrayList;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Cancion;

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
	
	public Cancion seleccionarCancion(int id) {
		String sql = "SELECT * FROM Cancion WHERE id=" + id + ")";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		Cancion cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
				cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
				cursor.getFileInputStream("archivo"));
		return cancion;
	}
	
	public ArrayList<Cancion> findCanciones (String busqueda){
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion WHERE Cancion.Nombre = '"+busqueda+"'" + "OR Cancion.Artista = '"+busqueda+"'" + "OR Cancion.Genero = '"+busqueda+"'";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<Cancion> listaCanciones = new ArrayList<>();
		int i = 1; //Max 10 canciones
		while (cursor.iterator().hasNext() && i < 10){
			Cancion cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
					cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
					null);
			listaCanciones.add(cancion);
			System.out.println(cancion.getId()+cancion.getNombre());
			i++;
		}
		System.out.println("Acabo de listar");
		return listaCanciones;
	}
	
	public ArrayList<Cancion> findLastest (){
		String sql = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		ArrayList<Cancion> listaCanciones = new ArrayList<>();
		int i = 1; //Max 10 canciones
		while (cursor.iterator().hasNext() && i < 10){
			Cancion cancion = new Cancion(cursor.getInteger("id"), cursor.getString("nombre"), cursor.getString("artista"),
					cursor.getString("genero"), cursor.getInteger("reproducciones"), cursor.getInteger("duracion"),
					null);
			listaCanciones.add(cancion);
			System.out.println(cancion.getId()+cancion.getNombre());
			i++;
		}
		System.out.println("Acabo de listar");
		return listaCanciones;
	}
	
}
