package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;
import modelo.Cancion;
import modelo.PausablePlayer;
import repositorio.CancionRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;

public class Reproductor extends JFrame{
	
	private JPanel contentPane;
	
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	
	private PausablePlayer player;
	
	private boolean status = false;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// select Look and Feel
		            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		            Reproductor frame = new Reproductor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Reproductor() {
		
		try {
            File file = new File("data/blob.mp3");
        	FileInputStream fileInput = new FileInputStream(file);
        	Cancion cancion = new Cancion(1, "", "", "", 0, 0, fileInput);
    		//Cancion cancion = cancionRepositorio.seleccionarCancion(1);
            player = new PausablePlayer(cancion.getArchivo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Reproductor - Spotifree");
		
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
		
		JTextPane txtTitulo = new JTextPane();
		txtTitulo.setForeground(Color.BLACK);
		txtTitulo.setEditable(false);
		txtTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTitulo.setBounds(19, 18, 304, 20);
		txtTitulo.setText("TITULO");
		txtTitulo.setOpaque(false);
		panel.add(txtTitulo);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (player.isNotStarted() || player.isPaused()) {
						player.play();
					}
				} catch (JavaLayerException e1) {
				}
			}
		});
		btnPlay.setBounds(201, 135, 122, 23);
		panel.add(btnPlay);
		
		JButton button = new JButton("Stop");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		});
		button.setBounds(39, 135, 122, 23);
		panel.add(button);
		
		JTextPane txtpnArtista = new JTextPane();
		txtpnArtista.setForeground(Color.BLACK);
		txtpnArtista.setText("ARTISTA");
		txtpnArtista.setOpaque(false);
		txtpnArtista.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnArtista.setEditable(false);
		txtpnArtista.setBounds(19, 58, 304, 20);
		panel.add(txtpnArtista);
		
		JTextPane txtpnGenero = new JTextPane();
		txtpnGenero.setForeground(Color.BLACK);
		txtpnGenero.setText("GENERO");
		txtpnGenero.setOpaque(false);
		txtpnGenero.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnGenero.setEditable(false);
		txtpnGenero.setBounds(19, 103, 304, 20);
		panel.add(txtpnGenero);
		
		JButton button_1 = new JButton("Recomendar a un amigo");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(42, 170, 277, 39);
		panel.add(button_1);
		
		
		
	}
}
