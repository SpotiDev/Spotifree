package modelo;

public class Genero {

	private String genero;
	private int reproducciones;
	
	public Genero(String genero, int reproducciones) throws GeneroException {
		if (genero == null || genero.equals("")) {
			throw new GeneroException();
		}
		else {
			this.genero = genero;
		}
		if (reproducciones < 0) {
			throw new GeneroException();
		}
		else {
			this.reproducciones = reproducciones;
		}
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

	public String toString(){
		return this.genero +"-"+ this.reproducciones;
	}
}
