package bd;
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
}