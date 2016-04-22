package modelo;

public class Usuario {
	
	private String correo;
	private String nombreUsuario;
	private String nombrePublico;
	private String contrasena;
	private int telefono;
	
	public Usuario(String correo, String nombreUsuario, String nombrePublico,String contrasena, int telefono) throws UsuarioException{
		if(correo == null || correo.equals("")){
			throw new UsuarioException();
		}
		else {
			this.correo = correo;
		}
		if(nombreUsuario == null || nombreUsuario.equals("")){
			throw new UsuarioException();
		}
		else {
			this.nombreUsuario = nombreUsuario;
		}
		if(nombrePublico == null || nombrePublico.equals("")){
			throw new UsuarioException();
		}
		else {
			this.nombrePublico = nombrePublico;
		}
		if(contrasena == null || contrasena.equals("")){
			throw new UsuarioException();
		}
		else {
			this.contrasena = contrasena;
		}
		
		if(telefono >0 ){
			this.telefono = telefono;
		}
		else{
			throw new UsuarioException();
		}	
	}
	
	public String getCorreo(){
		return this.correo;
	}
	public void setCorreo(String c){
		this.correo = c;
	}
	
	public String getNombreUsuario(){
		return this.nombreUsuario;
	}
	public void setNombreUsuario(String nomUser){
		this.nombreUsuario = nomUser;
	}
	
	public String getNombrePublico(){
		return this.nombrePublico;
	}
	
	public void setNombrePublico(String nomPublic){
		this.nombrePublico = nomPublic;
	}
	
	public int getTelefono(){
		return this.telefono;
	}
	public void setTelefono(int tlf){
		this.telefono = tlf;
	}
	
	public String getArtista(){
		return this.nombrePublico;
	}
}
