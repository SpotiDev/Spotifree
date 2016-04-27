package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import modelo.Usuario;
import repositorio.CancionRepositorio;
import repositorio.ListasRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

@SuppressWarnings("serial")
public class AñadirCancion extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private ListasRepositorio listaRepositorio = new ListasRepositorio();
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	
	
	public AñadirCancion(final int idLista, final Usuario u) {
		
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
		txtNombreArtistico.setBounds(19, 50, 107, 20);
		txtNombreArtistico.setEditable(false);
		txtNombreArtistico.setText("Introduce Nombre de la canción: ");
		txtNombreArtistico.setOpaque(false);
		panel.add(txtNombreArtistico);
		
		textField = new JTextField();
		textField.setBounds(129, 19, 188, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnTerminar = new JButton("Añadir Canción");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idCancion = cancionRepositorio.buscarCancion(textField.getText());
				listaRepositorio.subirCancionLista(idCancion,idLista);
			}
		});
		btnTerminar.setBounds(195, 175, 122, 23);
		panel.add(btnTerminar);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
}
