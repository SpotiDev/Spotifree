package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.EventQueue;

public class Recomendacion extends JFrame {
	
	private JPanel contentPane;
	private JTextField textField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// select Look and Feel
		            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		            Recomendacion frame = new Recomendacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Recomendacion() {
		
		setTitle("Spotifree");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 127);
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
		
		JTextPane txtNombreUsuario = new JTextPane();
		txtNombreUsuario.setForeground(Color.BLACK);
		txtNombreUsuario.setEditable(false);
		txtNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtNombreUsuario.setBounds(19, 19, 203, 20);
		txtNombreUsuario.setText("Introduce email:");
		txtNombreUsuario.setOpaque(false);
		panel.add(txtNombreUsuario);
		
		textField = new JTextField();
		textField.setBounds(129, 19, 188, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnTerminar = new JButton("Enviar");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTerminar.setBounds(186, 51, 122, 23);
		panel.add(btnTerminar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(29, 51, 122, 23);
		panel.add(btnCancelar);
		
		
		
	}
}
