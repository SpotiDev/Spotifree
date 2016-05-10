package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
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
import modelo.ListaReproduccion;
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
public class ListasReproduccion extends JFrame {

	private JPanel contentPane;
	private JTextField textBusqueda;
	private JTable table;
	boolean logueado = false;
	Usuario u;

	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	private ListasRepositorio listasRepositorio = new ListasRepositorio();

	public ListasReproduccion(final Usuario u) {
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

		JButton btnCrearLista = new JButton("Crear Lista");
		btnCrearLista.setBackground(SystemColor.desktop);		
		btnCrearLista.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCrearLista.setBounds(382, 278, 141, 47);
		btnCrearLista.setOpaque(false);
		panel.add(btnCrearLista);
		btnCrearLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearLista crear = new CrearLista(u,table);
				crear.setVisible(true);
			}
		});
		
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
			String[] colName = { "Titulo", "Reproducciones", "ID"};
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
						int id = Integer.parseInt((String) table.getValueAt(row, 2));
						CancionesLista mostrar = new CancionesLista(id,u);
						mostrar.setVisible(true);
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
		try{
			ArrayList<ListaReproduccion> list = listasRepositorio.findMasReproducciones(u);
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
			for (int i = 0; i < list.size(); i++) {
				String[] data = new String[3];
				data[0] = list.get(i).getTitulo();
				data[1] = Integer.toString(list.get(i).getReproducciones());
				data[2] = Integer.toString(list.get(i).getIdLista());
				tableModel.addRow(data);
			}
			table.setModel(tableModel);
			table.repaint();
		}catch(CancionException e){
			System.out.println("Error al cargar listas de reproducción");
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
