package repositorio;

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
	
}
