package io.github.alekso.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class Downloader {
 private static Long downloadedSize;
private static long fileLength;
public static boolean checkIfUpdated() {
	return false;
	 
 }
static void setfiles() {
	if(Login.rdbtnNewRadioButton.isSelected() && MainC.basedir1 != null && !MainC.basedir1.isEmpty()) {
		MainC.DownloadFile = new File(MainC.basedir1+File.separator+"BigBuckBunny_720p30.mp4");
		MainC.DownloadSubs = new File(MainC.basedir1+File.separator+"SampleSubtitles.srt");
		MainC.tempFile = new File(MainC.basedir1+File.separator+"temp");
	}else if(Login.rdbtnNewRadioButton_1.isSelected() && MainC.basedir2 != null && !MainC.basedir2.isEmpty()) {
		MainC.DownloadFile = new File(MainC.basedir2+File.separator+"BigBuckBunny_720p30.mp4");
		MainC.DownloadSubs = new File(MainC.basedir2+File.separator+"SampleSubtitles.srt");
	    MainC.tempFile = new File(MainC.basedir2+File.separator+"temp");
	}
}
static boolean sendChangeRequest() {
	String uri = MainC.conf.get("syncserver")+"/sync.php";
	String compiletime = "";
	try {
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("user-agent", "java: "+System.getProperty("java.version"));
		
		StringBuilder result = new StringBuilder();
		BufferedReader rd = null;
		if(conn.getResponseCode() != 200) {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}else {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		if(conn.getResponseCode() == 200) {
			JSON js= MainC.gson.fromJson(result.toString(), JSON.class);
			if(js != null && js.getMp4files().size() >= js.getSel()) {
			compiletime = compiletime+"Selected movie index: "+js.getSel()+"\n";
			compiletime = compiletime+"Currently connected users: "+js.getUserCount()+"\n";
			
			String file = js.getMp4files().get(js.getSel());
			if(file != null && !file.isEmpty()) {
				String signature = js.getMp4signatures().get(js.getSel());
				if(signature != null && !signature.isEmpty()) {
					compiletime = compiletime+"Server: "+file+" "+signature+"\n";
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(Files.readAllBytes(MainC.DownloadFile.toPath()));
					String dig = getMD5Checksum(md.digest());
					
					
					
					if(!dig.equals(signature)) {
						compiletime = compiletime+"Want to dl "+file+" because mismatched  "+dig+"\n";
						Authed.txtpnCurrentlySynchronising.setText(compiletime);
						try {
						download(file,false);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}else {
						compiletime = compiletime+"Is up to date on movie\n";
					}
					if(!js.getSrt().isEmpty()){
						Iterator<String> itr = js.getSrt().iterator();
						while(itr.hasNext()) {
							String next = itr.next();
							if(file.contains(".")) 
								file = file.substring(0, file.lastIndexOf('.'));
							if(next.contains(".")) 
								next = next.substring(0, next.lastIndexOf('.'));
							if(next.equals(file)) {
								//matching subs found
								int index = js.getSrt().indexOf(next+".srt");
								compiletime = compiletime+"Server: "+next+".srt "+js.getSrtsignatures().get(index)+"\n";
								md = MessageDigest.getInstance("MD5");
								md.update(Files.readAllBytes(MainC.DownloadSubs.toPath()));
								
								dig = getMD5Checksum(md.digest());
								String subsig = js.getSrtsignatures().get(index);
								if(subsig != null && !dig.equals(subsig)) {
									compiletime = compiletime+"Want to dl "+file+".srt because mismatched "+dig+"\n";
									Authed.txtpnCurrentlySynchronising.setText(compiletime);
									try {
										download(file,true);
										}catch(Exception e) {
											e.printStackTrace();
										}
								}else {
									compiletime = compiletime+"Is up to date on subtitle\n";
								}
							}
						}
					}
					compiletime = compiletime+"DONE!\n";
					Authed.txtpnCurrentlySynchronising.setText(compiletime);
				}
			}
			
			}else {
				System.out.println("index out of bounds");
			}
			return true;
		}else {
			return false;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;
	
}

public static String getMD5Checksum(byte[] b) {
    String result = "";

    for (int i=0; i < b.length; i++) {
        result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
    }
    return result;
}

private static void download(String filename,boolean subs) throws Exception {
	
	//
	URL url = new URL(MainC.conf.get("syncserver")+"/"+filename);
	//System.out.println(url.toString());
	HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

	connection.setRequestMethod("GET");
	connection.setRequestProperty("user-agent", "java: "+System.getProperty("java.version"));

	if (MainC.tempFile.exists()&& !MainC.bypass)
	{
		connection.setAllowUserInteraction(true);
		connection.setRequestProperty("Range", "bytes=" + MainC.tempFile.length() + "-");
	}

	connection.setConnectTimeout(14000);
	connection.setReadTimeout(20000);
	connection.connect();

	if (connection.getResponseCode() / 100 != 2) {
		System.out.println(connection.getResponseCode()+"");
		throw new Exception("Invalid response code!");
	}else
	{
		String connectionField = connection.getHeaderField("content-range");

		if (connectionField != null)
		{
			String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
			downloadedSize = Long.valueOf(connectionRanges[0]);
		}else {
			downloadedSize = 0L;
		}

		if(connectionField == null) {
			MainC.tempFile.delete();
			MainC.tempFile.createNewFile();
		}
		fileLength = connection.getContentLength() + downloadedSize;
		Authed.lblFilesize.setText(""+fileLength);
		BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
		RandomAccessFile output = new RandomAccessFile(MainC.tempFile, "rw");
		output.seek(downloadedSize);

		byte data[] = new byte[1024];
		int count = 0;
		int __progress = 0;

		while ((count = input.read(data, 0, 1024)) != -1 
				&& __progress != 100) 
		{
			downloadedSize += count;
			Authed.lblNewLabel_1.setText(downloadedSize.toString());
			output.write(data, 0, count);
			__progress = (int) ((downloadedSize * 100) / fileLength);
			Authed.DownloadProgBAR.setValue(__progress);
			
		}

		output.close();
		input.close();
		copyFileUsingIO(MainC.tempFile,subs?MainC.DownloadSubs:MainC.DownloadFile);
		MainC.tempFile.delete();
		Authed.DownloadProgBAR.setValue(0);
		Authed.lblFilesize.setText("filesize");
		Authed.lblNewLabel_1.setText("currentsize");
	}
 }

private static void copyFileUsingIO(File sourceFile, File destinationFile) throws IOException {
	InputStream inputStreamData = null;
	OutputStream outputStreamData = null;

	try {
		inputStreamData = new BufferedInputStream(new FileInputStream(sourceFile));
		outputStreamData = new BufferedOutputStream(new FileOutputStream(destinationFile));
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStreamData.read(buffer)) > 0) {
			outputStreamData.write(buffer, 0, length);
		}
			
	} finally {
		inputStreamData.close();
		outputStreamData.close();
	}
}
}
