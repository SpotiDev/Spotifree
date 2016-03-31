package bd;

public class prueba {
	public static void main (String[] args) {
		JDBCTemplate p = ConexionBD.conectar();
		p.executeQuery("SELECT * FROM Cancion");
	}
}