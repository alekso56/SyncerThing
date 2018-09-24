package io.github.alekso.gui;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class MainC {
	static JFrame myframe;
	static Login Login;
	static Authed authwindow;
	static File config;
	static File DownloadFile;
	static File DownloadSubs;
	static File tempFile;

	static String basedir1;
	static String basedir2;

	static HashMap<String,String> conf;
	public static Gson gson = new GsonBuilder().create();
	public static boolean bypass;


	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		config = new File("."+File.separator+"config.json");
		conf=new HashMap<String, String>();

		String basedir = "";
		basedir = RegistryInstallFinder.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Steam App 438100", "InstallLocation");
		System.out.println(basedir.replace("\r\n", ""));
		basedir = RegistryInstallFinder.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\VRChat\\shell\\open\\command", "(Default)");
		System.out.println(basedir);
		if(MainC.config.exists()) {
			try (Reader reader = new FileReader(MainC.config)) {

				// Convert JSON to Java Object
				Type type = new TypeToken<HashMap<String, String>>(){}.getType();
				MainC.conf = MainC.gson.fromJson(reader, type);



			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				MainC.config.createNewFile();
				MainC.conf.put("syncserver", "https://cax.no/sync");
				String js = "{'syncserver' : 'https://cax.no/sync'}";
				try (PrintWriter out = new PrintWriter(MainC.config)) {
					out.println(js);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		myframe = new JFrame();
		myframe.setSize(new Dimension(467, 367));
		Login = new Login();
		authwindow = new Authed();

		GroupLayout groupLayout = new GroupLayout(myframe.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(authwindow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(Login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(authwindow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		myframe.getContentPane().setLayout(groupLayout);
		authwindow.setVisible(false);
		myframe.setVisible(true);
	}

}
