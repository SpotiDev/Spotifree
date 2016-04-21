	package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Reproductor extends JFrame {
	
	private JPanel contentPane;
	
	private CancionRepositorio cancionRepositorio = new CancionRepositorio();
	
	private PausablePlayer player = null;
	
	JFileChooser chooser;
	
	//private boolean status = false;
	
	public static void init(final int id) {

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
	
	public Reproductor(final int id) throws CancionException {
		
		final Cancion c = cancionRepositorio.seleccionarCancion(id);
		
		setTitle("Reproductor - Spotifree");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 372);
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
		
		JButton btnPlay = new JButton(); //play
		
		Image img2 = null;
		Image img2Pulsar = null;
		try {
			img2 = ImageIO.read(new File("lib/img/play.png"));
			img2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			img2Pulsar = ImageIO.read(new File("lib/img/play_2.png"));
			img2Pulsar = img2Pulsar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		btnPlay.setIcon(new ImageIcon(img2));
		btnPlay.setRolloverIcon(new ImageIcon(img2Pulsar));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (player == null) {
						Cancion c = cancionRepositorio.seleccionarCancion(id);
			            player = new PausablePlayer(c.getArchivo());
					}
					if (player.isNotStarted() || player.isPaused() || player.isFinished()) {
						player.play();
					}
				} catch (JavaLayerException | CancionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPlay.setBounds(151, 135, 55, 55);
		panel.add(btnPlay);
		
		JButton buttonStop = new JButton(); //stop
		Image img = null;
		try {
			img = ImageIO.read(new File("lib/img/stop.png"));
			img = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Image imgPulsar = null;
		try {
			imgPulsar = ImageIO.read(new File("lib/img/stop_2.png"));
			imgPulsar = imgPulsar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		buttonStop.setIcon(new ImageIcon(img));
		buttonStop.setRolloverIcon(new ImageIcon(imgPulsar));
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (player.isPlaying()) {
					player.stop();
					player = null;
				}
			}
		});
		buttonStop.setBounds(234, 135, 55, 55);
		panel.add(buttonStop);
		
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
		button_1.setBounds(109, 241, 277, 39);
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
		
		//File Chooser
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
	    fc.setDialogTitle("Selecciona Destino");
	    fc.setApproveButtonText("Descargar");
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false); //Desactivamos opcion all files
		
		JButton btnDescargarCancion = new JButton("Descargar Cancion");
		btnDescargarCancion.setBackground(Color.GREEN);
		btnDescargarCancion.setBounds(164, 287, 162, 47);
		panel.add(btnDescargarCancion);
		
		JButton buttonAtras = new JButton();
		Image imgAtras = null;
		try {
			imgAtras = ImageIO.read(new File("lib/img/backward.png"));
			imgAtras = imgAtras.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Image imgAtrasPulsar = null;
		try {
			imgAtrasPulsar = ImageIO.read(new File("lib/img/backward_2.png"));
			imgAtrasPulsar = imgAtrasPulsar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		buttonAtras.setIcon(new ImageIcon(imgAtras));
		buttonAtras.setRolloverIcon(new ImageIcon(imgAtrasPulsar));
		buttonAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: 
			}
		});
		buttonAtras.setBounds(72, 135, 55, 55);
		panel.add(buttonAtras);
		
		JButton buttonPause = new JButton();
		Image imgPause = null;
		try {
			imgPause = ImageIO.read(new File("lib/img/pause.png"));
			imgPause = imgPause.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Image imgPausePulsar = null;
		try {
			imgPausePulsar = ImageIO.read(new File("lib/img/pause_2.png"));
			imgPausePulsar = imgPausePulsar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		buttonPause.setIcon(new ImageIcon(imgPause));
		buttonPause.setRolloverIcon(new ImageIcon(imgPausePulsar));
		buttonPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		});
		buttonPause.setBounds(314, 135, 55, 55);
		panel.add(buttonPause);
		
		JButton buttonForward = new JButton();
		Image imgForward = null;
		try {
			imgForward = ImageIO.read(new File("lib/img/forward.png"));
			imgForward = imgForward.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Image imgForwardPulsar = null;
		try {
			imgForwardPulsar = ImageIO.read(new File("lib/img/forward_2.png"));
			imgForwardPulsar = imgForwardPulsar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		buttonForward.setIcon(new ImageIcon(imgForward));
		buttonForward.setRolloverIcon(new ImageIcon(imgForwardPulsar));
		buttonForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		buttonForward.setBounds(400, 135, 55, 55);
		panel.add(buttonForward);
		
		btnDescargarCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(Reproductor.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
				      System.out.println("getCurrentDirectory(): " +  fc.getCurrentDirectory());
				      System.out.println("getSelectedFile() : " +  fc.getSelectedFile());
				      try {
						copyFiles("download.mp3",fc.getSelectedFile().getPath()+"/" + c.getArtista() + " - " + c.getNombre()+".mp3");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        } else {
		        	System.out.println("No Selection ");		        
		        	}
			}
		});
		
		//Para no cerrar todos los frames
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	private void copyFiles(String path1, String path2) throws IOException{
		File source = new File(path1);
		File dest = new File(path2);
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
