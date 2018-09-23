package io.github.alekso.gui;

public class UpdaterThread implements Runnable{

	@Override
	public void run() {
		try {
		while(true) {
			for(int i=1; i<60; i++){
				Thread.sleep(1000);
				Authed.SyncTime.setText(60-i+"");
				Authed.SyncTimeBAR.setValue(i);
			}
			Downloader.sendChangeRequest();
		}} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
