package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.CancionException;
import modelo.ListaReproduccion;
import modelo.Usuario;
import repositorio.ListasRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

@SuppressWarnings("serial")
public class CrearLista extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private ListasRepositorio listasRepositorio = new ListasRepositorio(Inicio.p);
	Usuario u;
	JTable table;
	
	public CrearLista(final Usuario u, final JTable table) {
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
		txtNombreArtistico.setBounds(12, 19, 107, 20);
		txtNombreArtistico.setEditable(false);
		txtNombreArtistico.setText("Título: ");
		txtNombreArtistico.setOpaque(false);
		panel.add(txtNombreArtistico);
		
		textField = new JTextField();
		textField.setBounds(129, 19, 188, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnTerminar = new JButton("Crear Lista");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaReproduccion lr = new ListaReproduccion(0, u.getCorreo(), 0, textField.getText());
				listasRepositorio.crearLista(lr);
				actualizarListas();
			}
		});
		btnTerminar.setBounds(195, 175, 122, 23);
		panel.add(btnTerminar);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
	

	private void actualizarListas() {
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
			this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}catch(CancionException e){
			System.out.println("Error al cargar listas de reproducción");
		}
	}
}
