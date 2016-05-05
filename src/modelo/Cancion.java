package modelo;

import java.io.FileInputStream;

public class Cancion {

	private int id;
	private String nombre;
	private String artista;
	private String genero;
	private int reproducciones;
	private int duracion;
	private FileInputStream archivo;
	
	public Cancion(int id, String nombre, String artista, String genero, int reproducciones,
			int duracion, FileInputStream archivo) throws CancionException {
		if (id < 0) {
			throw new CancionException();
		}
		else {
			this.id = id;
		}
		if (nombre == null || nombre.equals("")) {
			throw new CancionException();
		}
		else {
			this.nombre = nombre;
		}
		if (artista == null || artista.equals("")) {
			throw new CancionException();
		}
		else {
			this.artista = artista;
		}
		if (genero == null || genero.equals("")) {
			throw new CancionException();
		}
		else {
			this.genero = genero;
		}
		if (reproducciones < 0) {
			throw new CancionException();
		}
		else {
			this.reproducciones = reproducciones;
		}
		if (duracion == 0) {
			throw new CancionException();
		}
		else {
			this.duracion = duracion;
		}
		this.archivo = archivo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getReproducciones() {
		return reproducciones;
	}

	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public FileInputStream getArchivo() {
		return archivo;
	}

	public void setArchivo(FileInputStream archivo) {
		this.archivo = archivo;
	}
	
	public String toString(){
		return this.id +"-"+ this.nombre +"-"+ this.artista +"-"+ this.genero +"-"+ this.reproducciones +"-"+ this.duracion;
	}
}
