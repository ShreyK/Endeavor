package com.khosla.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveAndRead {
	int savedText;
	
	public void Save(int string) {
		try {
			FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(string);
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Read() {
		try {
			FileInputStream saveFile = new FileInputStream("saveFile.sav");
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			savedText = (int) restore.readObject();
			restore.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getRead(){
		return savedText;
	}
}
