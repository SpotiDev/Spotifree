package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import modelo.Cancion;
import repositorio.CancionRepositorio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.EventQueue;

public class SubirCancion extends JFrame{
	
	private JPanel contentPane;
	private JTextField textTitulo;
	private JTextField textGenero;
	private JTextField textArchivo;
	
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// select Look and Feel
		            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		            SubirCancion frame = new SubirCancion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
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
		
		textTitulo = new JTextField();
		textTitulo.setBounds(77, 19, 454, 20);
		panel.add(textTitulo);
		textTitulo.setColumns(10);
		
		textGenero = new JTextField();
		textGenero.setColumns(10);
		textGenero.setBounds(77, 49, 454, 20);
		panel.add(textGenero);
		
		textArchivo = new JTextField();
		textArchivo.setColumns(10);
		textArchivo.setBounds(77, 81, 332, 20);
		panel.add(textArchivo);
		
		JButton btnTerminar = new JButton("Subir");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File("data/blob.mp3");
					FileInputStream fileInput = new FileInputStream(file);
					AudioFile audioFile = AudioFileIO.read(file);
					int duration = audioFile.getAudioHeader().getTrackLength();
					System.out.println(duration);
					Cancion cancion = new Cancion(1, textTitulo.getText(), "artista",
							textGenero.getText(), 0 , duration, fileInput);
					cancionRepositorio.subirCancion(cancion);
				} catch (FileNotFoundException e1) {
				} catch (CannotReadException e1) {
				} catch (TagException e1) {
				} catch (ReadOnlyFileException e1) {
				} catch (InvalidAudioFrameException e1) {
				} catch (IOException e1) {
				}
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
