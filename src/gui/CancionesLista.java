package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import modelo.Cancion;
import modelo.CancionException;
import modelo.Usuario;
import repositorio.CancionRepositorio;
import repositorio.ListasRepositorio;

import javax.swing.JPopupMenu;
import java.awt.Component;

/**
 * Pantalla que gestiona el acceso a la aplicacion de los distintos tipos de
 * usuario que hay en la aplicacion: cliente y administrador
 * @author MusicBox
 * Fecha: Marzo de 2015
 */
@SuppressWarnings("serial")
public class CancionesLista extends JFrame {

	private JPanel contentPane;
	private static JFrame frame;
	private JTextField textBusqueda;
	private JTable table;
	boolean logueado = false;
	Usuario u;
	int id;

	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	private ListasRepositorio listasRepositorio = new ListasRepositorio();

	public CancionesLista(final int id, final Usuario u) {
		this.id = id;
		this.u = u;
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

		panel.setLayout(null);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panel, popupMenu);

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
		
		JButton btnAnadirCancion = new JButton("A�adir Cancion");
		btnAnadirCancion.setBackground(SystemColor.desktop);		
		btnAnadirCancion.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnadirCancion.setBounds(382, 278, 141, 47);
		btnAnadirCancion.setOpaque(false);
		panel.add(btnAnadirCancion);
		btnAnadirCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirCancion nueva = new AnadirCancion(id,u);
				nueva.setVisible(true);
			}
		});

		JTextPane txtpnNombre = new JTextPane();
		txtpnNombre.setForeground(new Color(0, 0, 0));
		txtpnNombre.setText("Titulo");
		txtpnNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnNombre.setBounds(30, 47, 42, 20);
		txtpnNombre.setOpaque(false);
		panel.add(txtpnNombre);

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
								Reproductor.init(id,false);
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

			cargarListasMasReproducidas();
			
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void cargarListasMasReproducidas() {
		try {
			ArrayList<Cancion> list = listasRepositorio.findCanciones(id,u);
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
		} catch (CancionException e) {
			
		}
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
}
