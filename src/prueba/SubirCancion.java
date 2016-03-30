package prueba;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class SubirCancion extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public SubirCancion() {
		
		setTitle("Spotifree");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 199);
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
		txtNombreArtistico.setBounds(19, 49, 203, 20);
		txtNombreArtistico.setEditable(false);
		txtNombreArtistico.setText("Genero:");
		txtNombreArtistico.setOpaque(false);
		panel.add(txtNombreArtistico);
		
		JTextPane txtNombreUsuario = new JTextPane();
		txtNombreUsuario.setForeground(Color.BLACK);
		txtNombreUsuario.setEditable(false);
		txtNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtNombreUsuario.setBounds(19, 19, 203, 20);
		txtNombreUsuario.setText("Titulo:");
		txtNombreUsuario.setOpaque(false);
		panel.add(txtNombreUsuario);
		
		JTextPane txtCorreo = new JTextPane();
		txtCorreo.setForeground(Color.BLACK);
		txtCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCorreo.setBounds(19, 81, 203, 20);
		txtCorreo.setEditable(false);
		txtCorreo.setText("Archivo:");
		txtCorreo.setOpaque(false);
		panel.add(txtCorreo);
		
		textField = new JTextField();
		textField.setBounds(77, 19, 454, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(77, 49, 454, 20);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(77, 81, 332, 20);
		panel.add(textField_2);
		
		JButton btnTerminar = new JButton("Subir");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTerminar.setBounds(356, 122, 122, 23);
		panel.add(btnTerminar);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(409, 78, 122, 23);
		panel.add(btnSeleccionar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(141, 122, 122, 23);
		panel.add(btnCancelar);
		
		
		
	}
}
