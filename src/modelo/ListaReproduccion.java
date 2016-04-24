package modelo;

public class ListaReproduccion {
	
	private int idLista;
	private String usuario;
	private int reproducciones;
	private String titulo;
	
	public ListaReproduccion (int idLista, String usuario, int reproducciones, String titulo){
		this.idLista = idLista;
		this.usuario = usuario;
		this.reproducciones = reproducciones;
		this.titulo = titulo;
	}

	public int getIdLista() {
		return idLista;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getReproducciones() {
		return reproducciones;
	}

	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
