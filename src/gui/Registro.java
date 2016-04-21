package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import bd.ConexionBD;
import bd.JDBCTemplate;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class Registro extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public Registro() {
		
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
		txtNombreArtistico.setText("Nombre artistico: ");
		txtNombreArtistico.setOpaque(false);
		panel.add(txtNombreArtistico);
		
		JTextPane txtNombreUsuario = new JTextPane();
		txtNombreUsuario.setForeground(Color.BLACK);
		txtNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtNombreUsuario.setBounds(19, 19, 107, 20);
		txtNombreUsuario.setText("Nombre usuario: ");
		txtNombreUsuario.setOpaque(false);
		panel.add(txtNombreUsuario);
		
		JTextPane txtCorreo = new JTextPane();
		txtCorreo.setForeground(Color.BLACK);
		txtCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCorreo.setBounds(19, 81, 107, 20);
		txtCorreo.setEditable(false);
		txtCorreo.setText("Correo: ");
		txtCorreo.setOpaque(false);
		panel.add(txtCorreo);
		
		JTextPane txtcontrasegna = new JTextPane();
		txtcontrasegna.setForeground(Color.BLACK);
		txtcontrasegna.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtcontrasegna.setBounds(19, 112, 107, 20);
		txtcontrasegna.setEditable(false);
		txtcontrasegna.setText("Password:");
		txtcontrasegna.setOpaque(false);
		panel.add(txtcontrasegna);
		
		textField = new JTextField();
		textField.setBounds(129, 19, 188, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(129, 50, 188, 20);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(129, 81, 188, 20);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(129, 112, 188, 20);
		panel.add(textField_3);
		
		JButton btnTerminar = new JButton("Registrarse");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBCTemplate p = ConexionBD.conectar();
				p.executeSentence("INSERT INTO Usuario (NombreUsuario,NombrePublico,Correo,Contrasena,Telefono) VALUES "
						+ "(?,?,?,?,?)",textField.getText(),textField_1.getText(),textField_2.getText(),
						textField_3.getText(),textField_4.getText());
				ConexionBD.desconectar(p);
			}
		});
		btnTerminar.setBounds(195, 175, 122, 23);
		panel.add(btnTerminar);
		
		textField_4 = new JTextField();
		textField_4.setBounds(129, 143, 188, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JTextPane txtpnTelefono = new JTextPane();
		txtpnTelefono.setForeground(Color.BLACK);
		txtpnTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnTelefono.setEditable(false);
		txtpnTelefono.setText("Telefono:");
		txtpnTelefono.setOpaque(false);
		txtpnTelefono.setBounds(19, 143, 107, 20);
		panel.add(txtpnTelefono);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
	}
}
