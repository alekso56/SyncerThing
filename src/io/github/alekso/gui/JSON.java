package io.github.alekso.gui;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class JSON implements Serializable{
	private static final long serialVersionUID = 3763336675639039818L;
	@SerializedName("mp4files")
	private ArrayList<String> mp4files = new ArrayList<String>();
	@SerializedName("mp4signatures")
    private ArrayList<String> mp4signatures = new ArrayList<String>();
	@SerializedName("srt")
	private ArrayList<String> srt = new ArrayList<String>();
	@SerializedName("srtsignatures")
	private ArrayList<String> srtsignatures = new ArrayList<String>();
	@SerializedName("sel")
	private int sel = 0;
	@SerializedName("userCount")
	private int userCount = 0;
	
	public ArrayList<String> getMp4files() {
		return mp4files;
	}
	public ArrayList<String> getMp4signatures() {
		return mp4signatures;
	}
	public ArrayList<String> getSrt() {
		return srt;
	}
	public ArrayList<String> getSrtsignatures() {
		return srtsignatures;
	}
	public int getSel() {
		return sel;
	}
	public int getUserCount() {
		return userCount;
	}
}
