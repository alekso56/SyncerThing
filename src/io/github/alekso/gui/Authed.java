package io.github.alekso.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Authed extends JPanel {
	

	
	static JLabel lblFilesize;
	static JLabel lblNewLabel_1;
	static JLabel lblDownloadProgress;
	static JLabel SyncTime;
	static JProgressBar SyncTimeBAR;
	static JTextPane txtpnCurrentlySynchronising;
	static  JProgressBar DownloadProgBAR;

	public Authed() {
		setBackground(Color.LIGHT_GRAY);
		
		JLabel SyncTool = new JLabel("Sync Tool");
		SyncTool.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		
		
		DownloadProgBAR = new JProgressBar();
		
		txtpnCurrentlySynchronising = new JTextPane();
		txtpnCurrentlySynchronising.setText("Currently Synchronising:");
		txtpnCurrentlySynchronising.setEditable(false);
		
		SyncTimeBAR = new JProgressBar();
		SyncTimeBAR.setMaximum(60);
		
		JLabel lblNewLabel = new JLabel("Next Sync in:");
		
		SyncTime = new JLabel(".");
		
		lblDownloadProgress = new JLabel("Download progress");
		
		lblFilesize = new JLabel("filesize");
		
		lblNewLabel_1 = new JLabel("currentsize");
		
		JScrollPane scrollPane = new JScrollPane(txtpnCurrentlySynchronising);
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(141)
							.addComponent(SyncTool, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(SyncTimeBAR, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SyncTime))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
								.addComponent(DownloadProgBAR, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDownloadProgress)
							.addPreferredGap(ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1)
							.addGap(18)
							.addComponent(lblFilesize)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(SyncTool, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDownloadProgress)
								.addComponent(lblFilesize)
								.addComponent(lblNewLabel_1)))
						.addGroup(groupLayout.createSequentialGroup()))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(DownloadProgBAR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(SyncTime))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(SyncTimeBAR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709549688658227954L;
}
