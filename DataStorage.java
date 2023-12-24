package com.internship;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DataStorage {
    public static String saveData(List<PotentialJob> activeData, List<PotentialJob> archive, String fileName) {
        if (activeData.size() == 0 && archive.size() == 0) {
            return "No data to save.";
        }
        fileName = fileName.trim().replace(' ', '-');
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".ser"))) {
            oos.writeObject(activeData);
            oos.writeObject(archive);
            return "Save successful.";
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public static void loadInternshipData(String fileName, List<PotentialJob> activeData, List<PotentialJob> archive) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".ser"))) {
            Object activeObject = ois.readObject();
            Object archiveObject = ois.readObject();

            if (activeObject instanceof List && archiveObject instanceof List) {
                activeData.clear();
                activeData.addAll((List<PotentialJob>) activeObject);

                archive.clear();
                archive.addAll((List<PotentialJob>) archiveObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
