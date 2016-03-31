package repositorio;

import bd.ConexionBD;
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
	
}
