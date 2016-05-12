package correo;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/*import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;*/
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SwingEmailSender extends JFrame {
	private ConfigUtility configUtil = new ConfigUtility();
	
	/*private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("Propiedades");
	private JMenuItem menuItemSetting = new JMenuItem("Propiedades...");*/
	
	private JLabel labelTo = new JLabel("Destino: ");
	//private JLabel labelPass = new JLabel("Contraseña correo: ");
	private JLabel labelSubject = new JLabel("Asunto: ");
	
	private JTextField fieldTo = new JTextField(30);
	//private JPasswordField fieldPass = new JPasswordField(30);
	private JTextField fieldSubject = new JTextField(30);
	
	private JButton buttonSend = new JButton("ENVIAR");
	
	private JFilePicker filePicker = new JFilePicker("Archivo", "Adjuntar archivo...");
	
	private JTextArea textAreaMessage = new JTextArea(10, 30);
	
	private GridBagConstraints constraints = new GridBagConstraints();
	
	public SwingEmailSender(String mensaje) {
		super("Recomendar Canción");
		try {
			String port = configUtil.loadProperties().getProperty("mail.smtp.port");
			String host = configUtil.loadProperties().getProperty("mail.smtp.host");
			String correo = configUtil.loadProperties().getProperty("mail.user");
			String pass = configUtil.loadProperties().getProperty("mail.password");
			configUtil.saveProperties(host, port, correo, pass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fieldSubject.setText("Recomendación de canción");
		textAreaMessage.setText(mensaje);
		
		// set up layout
		setLayout(new GridBagLayout());
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 5);
	
		//setupMenu();
		setupForm();
		
		pack();
		setLocationRelativeTo(null);	// center on screen
		
	}

	/*private void setupMenu() {
		menuItemSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				SettingsDialog dialog = new SettingsDialog(SwingEmailSender.this, configUtil);
				dialog.setVisible(true);
			}
		});
		
		menuFile.add(menuItemSetting);
		menuBar.add(menuFile);
		setJMenuBar(menuBar);		
	}*/
	
	private void setupForm() {
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(labelTo, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldTo, constraints);
		
		/*constraints.gridx = 0;
		constraints.gridy = 1;
		add(labelPass, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldPass, constraints);*/
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(labelSubject, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldSubject, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		buttonSend.setFont(new Font("Arial", Font.BOLD, 16));
		add(buttonSend, constraints);
		
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				buttonSendActionPerformed(event);
			}
		});
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		filePicker.setMode(JFilePicker.MODE_OPEN);
		add(filePicker, constraints);
		
		constraints.gridy = 5;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		
		add(new JScrollPane(textAreaMessage), constraints);		
	}
	
	private void buttonSendActionPerformed(ActionEvent event) {
		if (!validateFields()) {
			return;
		}
		
		String toAddress = fieldTo.getText();
		String subject = fieldSubject.getText();
		String message = textAreaMessage.getText();
		
		File[] attachFiles = null;
		
		if (!filePicker.getSelectedFilePath().equals("")) {
			File selectedFile = new File(filePicker.getSelectedFilePath());
			attachFiles = new File[] {selectedFile};
		}
		
		try {
			Properties smtpProperties = configUtil.loadProperties();
			EmailUtility.sendEmail(smtpProperties, toAddress, subject, message, attachFiles);
			
			JOptionPane.showMessageDialog(this, 
					"El mensaje ha sido enviado");
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, 
					"Error enviando el mensaje: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean validateFields() {
		if (fieldTo.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Introduzca una dirección de destino!",
					"Error", JOptionPane.ERROR_MESSAGE);
			fieldTo.requestFocus();
			return false;
		}
		
		if (fieldSubject.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Introduzca un asunto!",
					"Error", JOptionPane.ERROR_MESSAGE);
			fieldSubject.requestFocus();
			return false;
		}
		
		if (textAreaMessage.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Introduzca un mensaje!",
					"Error", JOptionPane.ERROR_MESSAGE);
			textAreaMessage.requestFocus();
			return false;
		}
		
		return true;
	}
}