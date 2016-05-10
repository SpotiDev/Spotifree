package repositorio;

import bd.ConexionBD;
import bd.Cursor;
import bd.JDBCTemplate;
import modelo.Usuario;
import modelo.UsuarioException;

public class UsuarioRepositorio {

	private JDBCTemplate p;

	public UsuarioRepositorio() {
		p = ConexionBD.conectar();
	}

	public UsuarioRepositorio(JDBCTemplate p) {
		this.p=p;
	}

	public Usuario seleccionarUsuario(String correo) throws UsuarioException {
		String sql = "SELECT Correo, NombreUsuario, NombrePublico, Contrasena, Telefono FROM Usuario WHERE Correo = '" + correo + "'";
		Cursor cursor = p.executeQueryAndGetCursor(sql);
		Usuario usuario = null;
		if (cursor.iterator().hasNext()) {
			usuario = new Usuario(cursor.getString("correo"), cursor.getString("nombreUsuario"), cursor.getString("nombrePublico"), 
					cursor.getString("contrasena"), cursor.getInteger("telefono"));
			System.out.println(usuario.toString());
		}
		return usuario;
	}

}
