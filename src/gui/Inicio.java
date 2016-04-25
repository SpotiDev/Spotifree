package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Cancion;
import modelo.CancionException;
import modelo.ListaReproduccion;
import modelo.Usuario;
import modelo.UsuarioException;
import repositorio.CancionRepositorio;
import repositorio.UsuarioRepositorio;

import com.jtattoo.plaf.smart.SmartLookAndFeel;

import bd.ConexionBD;
import bd.JDBCTemplate;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JComboBox;

/**
 * Pantalla que gestiona el acceso a la aplicacion de los distintos tipos de
 * usuario que hay en la aplicacion: cliente y administrador
 * @author MusicBox
 * Fecha: Marzo de 2015
 */
@SuppressWarnings("serial")
public class Inicio extends JFrame {

	private JPanel contentPane;
	private static JFrame frame;
	private JTextField textBusqueda;
	private JTextField textField;
	private JPasswordField textField_1;
	private JTable table;
	boolean logueado = false;
	private UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
	Usuario u;
	private ListaReproduccion listaReproduccion;

	//	private String [] columnas ={"titulo","reproducciones","artista","genero"};
	//	private String [][] datos ={{"aaaa", "bbbb"},
	//			{"aaaa", "bbbb"}};

	private CancionRepositorio cancionRepositorio = new CancionRepositorio();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// select Look and Feel
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Inicio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"aqui logo"));
		setTitle("Spotifree");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
		contentPane.add(panel);

		//boton registrarse
		final JButton btnRegistro = new JButton("Registrarse");
		btnRegistro.setBackground(new Color(0, 0, 0));
		btnRegistro.setBounds(420, 278, 103, 47);
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registro reg = new Registro();
				reg.setVisible(true);
			}
		});
		panel.setLayout(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panel, popupMenu);
		panel.add(btnRegistro);

		//Campo de texto busqueda
		textBusqueda = new JTextField();
		textBusqueda.setBounds(20, 15, 202, 20);
		panel.add(textBusqueda);
		textBusqueda.setColumns(10);

		//Boton busqueda
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(SystemColor.desktop);		
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//p.executeQuery("SELECT ID, Nombre, Artista, Genero, Duracion, Reproducciones FROM Cancion WHERE Cancion.Nombre = '"+textBusqueda.getText()+"'" + "OR Cancion.Artista = '"+textBusqueda.getText()+"'" + "OR Cancion.Genero = '"+textBusqueda.getText()+"'");
				try {
					ArrayList<Cancion> list = cancionRepositorio.findCanciones(textBusqueda.getText());
					DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
					tableModel.setRowCount(0);
					for (int i = 0; i < list.size(); i++) {
						String[] data = new String[5];
						data[0] = list.get(i).getNombre();
						data[1] = Integer.toString(list.get(i).getReproducciones());
						data[2] = list.get(i).getArtista();
						data[3] = list.get(i).getGenero();
						data[4] = Integer.toString(list.get(i).getId());
						tableModel.addRow(data);
					}
					table.setModel(tableModel);
					table.repaint();
				} catch (CancionException e) { }
			}
		});
		btnBuscar.setBounds(232, 14, 103, 23);
		panel.add(btnBuscar);

		JTextPane txtpnNombre = new JTextPane();
		txtpnNombre.setForeground(new Color(0, 0, 0));
		txtpnNombre.setText("Titulo");
		txtpnNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnNombre.setBounds(30, 47, 42, 20);
		txtpnNombre.setOpaque(false);
		panel.add(txtpnNombre);

		final JTextPane txtpnCorreo = new JTextPane();
		txtpnCorreo.setText("Correo");
		txtpnCorreo.setOpaque(false);
		txtpnCorreo.setForeground(Color.BLACK);
		txtpnCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnCorreo.setBounds(38, 278, 42, 20);
		panel.add(txtpnCorreo);

		final JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setText("Password");
		txtpnPassword.setOpaque(false);
		txtpnPassword.setForeground(Color.BLACK);
		txtpnPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnPassword.setBounds(20, 302, 60, 20);
		panel.add(txtpnPassword);

		textField = new JTextField();
		textField.setBounds(92, 278, 162, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JPasswordField();
		textField_1.setBounds(92, 302, 162, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		final JButton btnListaReproduccion = new JButton("Listas de reproducci�n");
		btnListaReproduccion.setBackground(Color.BLACK);
		btnListaReproduccion.setBounds(77, 278, 141, 47);
		btnListaReproduccion.setVisible(false);
		panel.add(btnListaReproduccion);
		btnListaReproduccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListasReproduccion listaReproduccion = new ListasReproduccion(u);
				listaReproduccion.setVisible(true);
			}
		});
		
		final JButton btnSubirCancion = new JButton("Subir Cancion");
		btnSubirCancion.setBackground(Color.BLACK);
		btnSubirCancion.setBounds(382, 278, 141, 47);
		btnSubirCancion.setVisible(false);
		panel.add(btnSubirCancion);
		btnSubirCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubirCancion subircancion = new SubirCancion(u);
				subircancion.setVisible(true);
			}
		});

		final JButton btnIniciarSesin = new JButton("Iniciar sesi\u00F3n");
		btnIniciarSesin.setBackground(SystemColor.desktop);
		btnIniciarSesin.setBounds(266, 277, 104, 47);
		panel.add(btnIniciarSesin);
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDBCTemplate p = ConexionBD.conectar();
				//	p.executeQuery("SELECT * FROM Usuario WHERE Usuario.NombreUsuario = '"+textField.getText()+"'"
				//			+ " AND Usuario.Contrasena = '"+textField_1.getText()+"'");
				//	ConexionBD.desconectar(p);
				String pass = new String(textField_1.getPassword());
				String comprobarPW = p.executeQueryBuscar("SELECT Correo FROM Usuario WHERE Usuario.Contrasena = '" + pass + "'");
				if (textField.getText().equals(comprobarPW)) {
					try {
						u = usuarioRepositorio.seleccionarUsuario(comprobarPW);
					} catch (UsuarioException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textField.setVisible(false);
					textField_1.setVisible(false);
					txtpnCorreo.setVisible(false);
					txtpnPassword.setVisible(false);
					btnIniciarSesin.setVisible(false);
					btnRegistro.setVisible(false);
					btnSubirCancion.setVisible(true);
					btnListaReproduccion.setVisible(true);
				} else {
					//Error				
				}
			}
		});


		table = new JTable() {
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}};
			DefaultTableModel contactTableModel = (DefaultTableModel) table
					.getModel();
			String[] colName = { "Nombre", "Artista", "Reproducciones", "Genero", "ID"};
			contactTableModel.setColumnIdentifiers(colName);
			table.setModel(contactTableModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setBorder(new LineBorder(new Color(173, 255, 47), 2, true));
			table.setBackground(new Color(255, 255, 255));
			table.setBounds(21, 75, 517, 177);

			table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					final JTable table =(JTable) me.getSource();
					Point p = me.getPoint();
					final int row = table.rowAtPoint(p);
					if (me.getClickCount() == 2) {
						final JDialog loading = new JDialog(frame);
						JPanel p1 = new JPanel(new BorderLayout());
						p1.add(new JLabel("Cargando cancion..."), BorderLayout.CENTER);
						loading.setUndecorated(true);
						loading.getContentPane().add(p1);
						loading.pack();
						loading.setLocationRelativeTo(frame);
						loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
						loading.setModal(true);
						SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
							@Override
							protected String doInBackground() throws InterruptedException {
								// Ejecutamos operacion larga   
								int id = Integer.parseInt((String) table.getValueAt(row, 4));
								System.out.println(id);
								Reproductor.init(id);
								cancionRepositorio.updateReproducciones(id);
								return null;
							}
							@Override
							protected void done() {
								//Cuando acaba, quitamos el mensaje
								loading.dispose();
							}
						};
						worker.execute();
						loading.setVisible(true);
						try {
							worker.get();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});

			panel.add(table);

			JTextPane txtpnReproducciones = new JTextPane();
			txtpnReproducciones.setText("Reproducciones");
			txtpnReproducciones.setOpaque(false);
			txtpnReproducciones.setForeground(Color.BLACK);
			txtpnReproducciones.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtpnReproducciones.setBounds(115, 47, 103, 20);
			panel.add(txtpnReproducciones);

			JTextPane txtpnArtista = new JTextPane();
			txtpnArtista.setText("Artista");
			txtpnArtista.setOpaque(false);
			txtpnArtista.setForeground(Color.BLACK);
			txtpnArtista.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtpnArtista.setBounds(230, 47, 103, 20);
			panel.add(txtpnArtista);

			JTextPane txtpnGenero = new JTextPane();
			txtpnGenero.setText("Genero");
			txtpnGenero.setOpaque(false);
			txtpnGenero.setForeground(Color.BLACK);
			txtpnGenero.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtpnGenero.setBounds(328, 47, 103, 20);
			panel.add(txtpnGenero);


			JTextPane txtpnId = new JTextPane();
			txtpnId.setText("Id");
			txtpnId.setOpaque(false);
			txtpnId.setForeground(Color.BLACK);
			txtpnId.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtpnId.setBounds(445, 47, 103, 20);
			panel.add(txtpnId);
			
			final JComboBox comboBox = new JComboBox();
			comboBox.addItem("Seleccione un filtro");
			comboBox.addItem("Mas reproducciones");
			comboBox.addItem("Ultimas a�adidas");
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					try{
						filtra(comboBox.getSelectedIndex());
					}catch(Exception e2){}
					
				}
			});
			comboBox.setBounds(382, 15, 141, 20);
			panel.add(comboBox);
			


			cargarUltimasCanciones();
	}

	public void cargarUltimasCanciones() {
		try {
			ArrayList<Cancion> list = cancionRepositorio.findLastest();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
			for (int i = 0; i < list.size(); i++) {
				String[] data = new String[5];
				data[0] = list.get(i).getNombre();
				data[1] = Integer.toString(list.get(i).getReproducciones());
				data[2] = list.get(i).getArtista();
				data[3] = list.get(i).getGenero();
				data[4] = Integer.toString(list.get(i).getId());
				tableModel.addRow(data);
			}
			table.setModel(tableModel);
			table.repaint();
		} catch(CancionException e) { }
	}
	
	public void cargarCancionesMasReproducidas(){
		try{
			ArrayList<Cancion> list = cancionRepositorio.findMasReproducciones();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
			for (int i = 0; i < list.size(); i++) {
				String[] data = new String[5];
				data[0] = list.get(i).getNombre();
				data[1] = Integer.toString(list.get(i).getReproducciones());
				data[2] = list.get(i).getArtista();
				data[3] = list.get(i).getGenero();
				data[4] = Integer.toString(list.get(i).getId());
				tableModel.addRow(data);
			}
			table.setModel(tableModel);
			table.repaint();
		}catch(CancionException e){}
	}
	
	public void cargarListaReproduccion(){
		try{
			ArrayList<Cancion> list = cancionRepositorio.findMasReproducciones();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
			for (int i = 0; i < list.size(); i++) {
				String[] data = new String[5];
				data[0] = list.get(i).getNombre();
				data[1] = Integer.toString(list.get(i).getReproducciones());
				data[2] = list.get(i).getArtista();
				data[3] = list.get(i).getGenero();
				data[4] = Integer.toString(list.get(i).getId());
				tableModel.addRow(data);
			}
			table.setModel(tableModel);
			table.repaint();
		}catch(CancionException e){}
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void filtra(int num){
		if(num == 1){
			cargarCancionesMasReproducidas();
		}
		else if(num == 2){
			cargarUltimasCanciones();
		}
	}
}
