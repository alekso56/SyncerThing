package io.github.alekso.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Login extends JPanel {
	

	
	static JRadioButton rdbtnNewRadioButton_1;
	static JRadioButton rdbtnNewRadioButton;
	public Login() {
		setBackground(Color.LIGHT_GRAY);
		
		JLabel lblLogin = new JLabel("Sync Tool");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		JLabel lblPassword = new JLabel("SyncServer:");
		
		JButton btnLogin = new JButton("Check&Start");
		JLabel lblWrongCredentialInfo = new JLabel("Failed validating sync server!");
		lblWrongCredentialInfo.setEnabled(false);
		
		
		
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//start login.
				Downloader.setfiles();
				MainC.Login.setVisible(false);
		    	MainC.authwindow.setVisible(true);

			    if(!Downloader.sendChangeRequest()) {
			    	lblWrongCredentialInfo.setEnabled(true);
			    	MainC.Login.setVisible(true);
			    	MainC.authwindow.setVisible(false);
			    }else {
			    	lblWrongCredentialInfo.setEnabled(false);
			    	Thread t = new Thread(new UpdaterThread());
			    	t.setDaemon(true);
			    	t.start();
			    	//MainC.myframe.pack();
			    	
			    }
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		rdbtnNewRadioButton = new JRadioButton("Option1");
		
		rdbtnNewRadioButton_1 = new JRadioButton("Option2");
		MainC.basedir1 = RegistryInstallFinder.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Steam App 438100", "InstallLocation");
		if(MainC.basedir1 != null) {
			MainC.basedir1 = MainC.basedir1.replace("\r\n", "");
			rdbtnNewRadioButton.setText(MainC.basedir1);
			MainC.basedir1 = MainC.basedir1+File.separator+"VRChat_Data"+File.separator+"StreamingAssets"+File.separator+"AVProVideoSamples";
		}else {
			rdbtnNewRadioButton.setEnabled(false);
		}
		MainC.basedir2 = RegistryInstallFinder.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\VRChat\\shell\\open\\command", "(Default)");
		if(MainC.basedir2 != null) {
		  rdbtnNewRadioButton_1.setText(MainC.basedir2);
		  MainC.basedir2 = MainC.basedir2+File.separator+"VRChat_Data"+File.separator+"StreamingAssets"+File.separator+"AVProVideoSamples";
		}else {
			rdbtnNewRadioButton_1.setEnabled(false);
		}
		
		rdbtnNewRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					rdbtnNewRadioButton_1.setSelected(false);
				}
			}
		});
		
		
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					rdbtnNewRadioButton.setSelected(false);
				}
			}
		});
		
		String syncserver = MainC.conf.get("syncserver");
		textField.setText(syncserver);
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnNewRadioButton_1)
								.addComponent(rdbtnNewRadioButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(68)
							.addComponent(lblPassword)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(28)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblLogin, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(10)
											.addComponent(lblWrongCredentialInfo))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(52)
									.addComponent(btnLogin)))))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWrongCredentialInfo)
					.addGap(18)
					.addComponent(btnLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton_1)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709549688658227954L;
	private JTextField textField;
}
