package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import modelo.ListaReproduccion;
import modelo.Usuario;
import repositorio.ListasRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

@SuppressWarnings("serial")
public class CrearLista extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private ListasRepositorio listaRepositorio = new ListasRepositorio();
	
	
	public CrearLista(final Usuario u) {
		
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
		txtNombreArtistico.setText("T�tulo: ");
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
				listaRepositorio.crearLista(lr);
			}
		});
		btnTerminar.setBounds(195, 175, 122, 23);
		panel.add(btnTerminar);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
}
