package Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PersistenceModel {

    private String filePath;

    public PersistenceModel(String filePath) {

		this.filePath = filePath;
    }

    public void saveModel(Model model) {

        XMLEncoder encoder=null;
		try {
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		encoder.writeObject(model);
		encoder.close();
    }

    public Model loadModel() {

		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File dvd.xml not found");
		}

		return (Model)decoder.readObject();
    }
}