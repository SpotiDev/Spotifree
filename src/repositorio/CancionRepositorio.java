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

	public CancionRepositorio(JDBCTemplate p) {
		this.p=p;
	}

	public void subirCancion(Cancion cancion) {
		if (!cancion.getNombre().equals("")) {
			String sql = "INSERT INTO Cancion (nombre, artista, genero, reproducciones, duracion, archivo)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			p.executeSentence(sql, cancion.getNombre(), cancion.getArtista(), cancion.getGenero(),
					cancion.getReproducciones(), cancion.getDuracion(), cancion.getArchivo());
		}
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

	public void updateRecomendacion (int id, String correo) {
		String sql = "INSERT INTO Recomendacion (ID_Cancion, Correo, Reproducciones) VALUES"
				+ " (" + id + ", '" + correo + "', 1) ON DUPLICATE KEY UPDATE	Reproducciones = Reproducciones + 1";
		p.executeSentence(sql);
	}

	public ArrayList<Cancion> findRecomendaciones (String correo) throws CancionException {
		String sql_1 = "SELECT c.Artista AS Artista FROM Cancion c, Recomendacion r, Usuario u"
				+ " WHERE c.ID = r.ID_Cancion AND r.Correo = '" + correo + "' AND r.Correo = u.Correo"
				+ " AND c.Artista = u.NombrePublico AND u.correo != '" + correo + "' GROUP BY c.Artista ORDER BY r.Reproducciones DESC";
		Cursor cursor_1 = p.executeQueryAndGetCursor(sql_1);
		ArrayList<String> listaArtistas = new ArrayList<>();
		int i = 1;
		while (cursor_1.iterator().hasNext() && i < 10) {
			cursor_1.iterator().next();
			String artista = cursor_1.getString("artista");
			listaArtistas.add(artista);
			cursor_1.iterator().next();
			System.out.println(artista.toString());
			i++;
		}
		String sql_2 = "SELECT c.Genero FROM Cancion c, Recomendacion r, Usuario u"
				+ " WHERE c.ID = r.ID_Cancion AND r.Correo = '" + correo + "' AND r.Correo = u.Correo"
				+ " AND c.Artista = u.NombrePublico AND u.correo != '" + correo + "' GROUP BY c.Genero ORDER BY r.Reproducciones DESC";
		Cursor cursor_2 = p.executeQueryAndGetCursor(sql_2);
		ArrayList<String> listaGeneros = new ArrayList<>();
		int j = 1;
		while (cursor_2.iterator().hasNext() && j < 10) {
			cursor_2.iterator().next();
			String genero = cursor_2.getString("genero");
			listaGeneros.add(genero);
			cursor_2.iterator().next();
			System.out.println(genero.toString());
			j++;
		}
		String query_artistas = "";
		String query_generos = "";
		for (String artista: listaArtistas) {
			query_artistas += "Artista = " + artista + "OR ";
		}
		for (String genero: listaGeneros) {
			query_generos += "Genero = " + genero + "OR ";
		}
		String subquery = "";
		if (query_artistas.equals("") && !query_generos.equals("")) {
			subquery = "WHERE (" + query_generos.substring(0, query_generos.length() - 3) + ")";
		} else if (query_generos.equals("") && !query_artistas.equals("")) {
			subquery = "WHERE (" + query_artistas.substring(0, query_artistas.length() - 3) + ")";
		} else if (!query_artistas.equals("") && !query_generos.equals("")) {
			subquery = "WHERE (" + query_artistas.substring(0, query_artistas.length() - 3)
			+ ") AND (" + query_generos.substring(0, query_generos.length() - 3) + ")";
		}
		String sql_3 = "SELECT ID, Nombre, Artista, Genero, Reproducciones, Duracion FROM Cancion"
				+ subquery + " ORDER BY Reproducciones DESC";
		Cursor cursor_3 = p.executeQueryAndGetCursor(sql_3);
		ArrayList<Cancion> listaCanciones = new ArrayList<>();
		int k = 1; //Max 10 canciones
		while (cursor_3.iterator().hasNext() && k < 10){
			Cancion cancion = new Cancion(cursor_3.getInteger("id"), cursor_3.getString("nombre"), cursor_3.getString("artista"),
					cursor_3.getString("genero"), cursor_3.getInteger("reproducciones"), cursor_3.getInteger("duracion"),
					null);
			cursor_3.iterator().next();
			listaCanciones.add(cancion);
			System.out.println(cancion.toString());
			k++;
		}
		System.out.println("Acabo de listar recomendaciones");
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
		String sql = "SELECT ID FROM Cancion WHERE nombre LIKE '"+nombre+"'";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		if (cursor.iterator().hasNext()) {
			return cursor.getInteger("id");	
		}
		return -1;
	}
}
