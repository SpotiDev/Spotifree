package bd;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Emulacion de un Cursor de PL/SQL
 *
 */
public class Cursor implements Iterable<Cursor> {

	final ResultSet rs;
	
	public Cursor(ResultSet rs) {
		this.rs = rs;
	}

	public Iterator<Cursor> iterator() {
		return new Iterator<Cursor>() {

			public boolean hasNext() {
				try {
					if (rs.next())
						return true;
					rs.close();
				} catch (SQLException e) {
					try {
						rs.close();
					} catch (SQLException e2) {
					}
				}
				return false;
			}

			public Cursor next() {
				return new Cursor(rs);
			}

			public void remove() {
			}
			
		};
	}

	public String getString(String name) {
		try {
			return rs.getString(name);
		} catch (SQLException e) {
			return "";
		}
	}

	public int getInteger(String name) {
		try {
			return rs.getInt(name);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public double getDouble(String name){
		try {
			return rs.getDouble(name);
		} catch (SQLException e) {
			return 0.0;
		}
	}

	public Date getDate(String name) {
		try {
			return rs.getDate(name);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public FileInputStream getFileInputStream(String name) {
		FileInputStream fileInputStream = null;
		try {
			rs.next();
			Blob blob = rs.getBlob(name); 
			byte[] bytes = blob.getBytes(1, (int) blob.length());
			FileOutputStream fos = new FileOutputStream("download.mp3");
			fos.write(bytes);
			fos.close();
			return new FileInputStream("download.mp3");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SALTA EXCEPCION EN cursor.getFileInputStream 1");
		} catch (IOException e) {
			System.out.println("SALTA EXCEPCION EN cursor.getFileInputStream 2");
		}
		return fileInputStream;
	}
}
