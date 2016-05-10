package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Cancion;
import modelo.CancionException;
import modelo.Usuario;
import repositorio.CancionRepositorio;
import repositorio.ListasRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

@SuppressWarnings("serial")
public class AnadirCancion extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private ListasRepositorio listasRepositorio = new ListasRepositorio(Inicio.p);
	private CancionRepositorio cancionRepositorio = new CancionRepositorio(Inicio.p);
	JTable table;
	int idLista;
	Usuario u;
	
	
	public AnadirCancion(final int idLista, final Usuario u, JTable table) {
		this.idLista = idLista;
		this.u = u;
		this.table = table;
		setTitle("Spotifree");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setLayout(null);
		panel.setLayout(null);
		
		JTextPane txtNombreArtistico = new JTextPane();
		txtNombreArtistico.setForeground(Color.BLACK);
		txtNombreArtistico.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtNombreArtistico.setBounds(10, 19, 107, 48);
		txtNombreArtistico.setEditable(false);
		txtNombreArtistico.setText("Introduce Nombre de la canci\u00F3n: ");
		txtNombreArtistico.setOpaque(false);
		panel.add(txtNombreArtistico);
		
		textField = new JTextField();
		textField.setBounds(129, 47, 188, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnTerminar = new JButton("A\u00F1adir Canci\u00F3n");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idCancion = cancionRepositorio.buscarCancion(textField.getText());
				listasRepositorio.subirCancionLista(idCancion,idLista);
				actualizarCanciones();
			}
		});
		btnTerminar.setBounds(195, 175, 122, 23);
		panel.add(btnTerminar);
		setVisible(false);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
	
	private void actualizarCanciones() {
		try {
			ArrayList<Cancion> list = listasRepositorio.findCanciones(idLista,u);
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
			System.out.println("Error al cargar canciones de la lista");
		}
	}
}
