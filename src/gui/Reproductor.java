package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;
import modelo.Cancion;
import modelo.CancionException;
import modelo.PausablePlayer;
import repositorio.CancionRepositorio;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Reproductor extends JFrame{
	
	private JPanel contentPane;
	
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	
	private PausablePlayer player;
	
	//private boolean status = false;
	
	public static void init(final int id){

		// select Look and Feel
        try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            Reproductor frame = new Reproductor(id);
			frame.setVisible(true);
        } catch (CancionException | ClassNotFoundException
        		| InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Reproductor(int id) throws CancionException {
		
		Cancion c = cancionRepositorio.seleccionarCancion(id);
		
		try {
            player = new PausablePlayer(c.getArchivo());
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
		txtTitulo.setBounds(19, 18, 64, 20);
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
		txtpnArtista.setBounds(19, 58, 64, 20);
		panel.add(txtpnArtista);
		
		JTextPane txtpnGenero = new JTextPane();
		txtpnGenero.setForeground(Color.BLACK);
		txtpnGenero.setText("GENERO");
		txtpnGenero.setOpaque(false);
		txtpnGenero.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnGenero.setEditable(false);
		txtpnGenero.setBounds(19, 103, 64, 20);
		panel.add(txtpnGenero);
		
		JButton button_1 = new JButton("Recomendar a un amigo");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(42, 170, 277, 39);
		panel.add(button_1);
		
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setText(c.getNombre());
		txtpnAsd.setOpaque(false);
		txtpnAsd.setForeground(Color.BLACK);
		txtpnAsd.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnAsd.setEditable(false);
		txtpnAsd.setBounds(97, 18, 64, 20);
		panel.add(txtpnAsd);
		
		JTextPane txtpnAsd_1 = new JTextPane();
		txtpnAsd_1.setText(c.getArtista());
		txtpnAsd_1.setOpaque(false);
		txtpnAsd_1.setForeground(Color.BLACK);
		txtpnAsd_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnAsd_1.setEditable(false);
		txtpnAsd_1.setBounds(97, 58, 64, 20);
		panel.add(txtpnAsd_1);
		
		JTextPane txtpnAsd_2 = new JTextPane();
		txtpnAsd_2.setText(c.getGenero());
		txtpnAsd_2.setOpaque(false);
		txtpnAsd_2.setForeground(Color.BLACK);
		txtpnAsd_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnAsd_2.setEditable(false);
		txtpnAsd_2.setBounds(97, 103, 64, 20);
		panel.add(txtpnAsd_2);
		
		//Para no cerrar todos los frames
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
}
