package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import bd.ConexionBD;
import bd.JDBCTemplate;
import modelo.Cancion;
import modelo.CancionException;
import repositorio.CancionRepositorio;

public class Logueado  extends JFrame {
	
	private JPanel contentPane;
	private static JFrame frame;
	private JTextField textBusqueda;
	private JTable table;
	
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


	public Logueado() {
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
		
		//Campo de texto busqueda
		textBusqueda = new JTextField();
		textBusqueda.setBounds(20, 15, 411, 20);
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
		btnBuscar.setBounds(435, 14, 103, 23);
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
		String[] colName = { "Nombre", "Artista", "Reproducciones", "Genero", "ID"};
		contactTableModel.setColumnIdentifiers(colName);
		table.setModel(contactTableModel);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(173, 255, 47), 2, true));
		table.setBackground(new Color(255, 255, 255));
		table.setBounds(21, 75, 517, 177);
		
//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//	        public void valueChanged(ListSelectionEvent event) {
//	            // do some actions here, for example
//	            // print first column value from selected row
//	            System.out.println(table.getValueAt(table.getSelectedRow(), 4).toString());
//	        }
//	    });
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	int id = Integer.parseInt((String) table.getValueAt(row, 4));
		        	System.out.println(id);
		        	Reproductor.init(id);
		        	cancionRepositorio.updateReproducciones(id);
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

}
