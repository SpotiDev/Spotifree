package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import modelo.Cancion;
import modelo.CancionException;
import modelo.Usuario;
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

	//Archvio para guardar el Fichero seleccionado
	File file;

	private CancionRepositorio cancionRepositorio = new CancionRepositorio();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// select Look and Feel
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					//SubirCancion frame = new SubirCancion();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SubirCancion( final Usuario u) {

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

		//Selector de fichero de mp3
		final JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("MP3 File","mp3");
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);

		JButton btnTerminar = new JButton("Subir");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file != null){
					final JDialog loading = new JDialog();
					JPanel p1 = new JPanel(new BorderLayout());
					p1.add(new JLabel("Subiendo cancion..."), BorderLayout.CENTER);
					loading.setUndecorated(true);
					loading.getContentPane().add(p1);
					loading.pack();
					loading.setLocationRelativeTo(contentPane);
					loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					loading.setModal(true);
					SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
						@Override
						protected String doInBackground() throws InterruptedException {
							// Ejecutamos operacion larga   
							try{
								FileInputStream fileInput = new FileInputStream(file);
								AudioFile audioFile = AudioFileIO.read(file);
								int duration = audioFile.getAudioHeader().getTrackLength();
								//System.out.println(duration);
								Cancion cancion = new Cancion(1, textTitulo.getText(), u.getArtista(),
										textGenero.getText(), 0 , duration, fileInput);
								cancionRepositorio.subirCancion(cancion);
							}
							catch (CancionException e1) {
							} catch (FileNotFoundException e1) {
							} catch (CannotReadException e1) {
							} catch (TagException e1) {
							} catch (ReadOnlyFileException e1) {
							} catch (InvalidAudioFrameException e1) {
							} catch (IOException e1) {
							}
							return  null;
						}
						@Override
						protected void done() {
							//Cuando acaba, quitamos el mensaje
							loading.dispose();
						}
					};
					worker.execute();
					loading.setVisible(true);
					try {
						worker.get();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnTerminar.setBounds(356, 122, 122, 23);
		panel.add(btnTerminar);

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(409, 78, 122, 23);
		panel.add(btnSeleccionar);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(SubirCancion.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					textArchivo.setText(file.getName());
				} else {
					textArchivo.setText("ERROR AL SELECCIONAR EL ARCHIVO");
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(141, 122, 122, 23);
		panel.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false); //you can't see me!
				dispose(); //Destroy the JFrame object
			}
		});
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


	}
}
