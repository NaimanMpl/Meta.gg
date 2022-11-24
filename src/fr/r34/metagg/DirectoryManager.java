/**
 * 
 */
package fr.r34.metagg;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * @author andre
 *
 */
public class DirectoryManager {

	/**
	 * Gestionnaire de répertoire permettant de reprérer et accéder aux différents dossier, sous dossiers et fichiers
	 * @version 0.0.2
	 * @author Andrea PL, Naïman Mpl
	 * @return odtInFolder La liste des fichiers .odt contu dans le dossier passé en paramètre et ses sous dossiers
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public ArrayList<File> directoryContent(File folder, ArrayList<File> odtInFolder){
		try {
			for(File element : folder.listFiles()) {
				if(element.isDirectory())
					directoryContent(element, odtInFolder);
				else {
					String mimetype = element.toURL().openConnection().getContentType();
					if (Objects.equals(mimetype, "application/vnd.oasis.opendocument.text")) {
						String fileName = element.getName();
						odtInFolder.add(element);
				
					}
				}
			}
			return odtInFolder;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Affiche les informations princiaples d'un fichier odt avec son dossier parent
	 * @param file Fichier dont on veut afficher les informations principales
	 * 
	 */
	public void directoryODTInfo(File file){
		MetaFile metaFile = new MetaFile(file);
		String name = file.getName();
		String title = metaFile.getTitle();
		Date creattionDate = metaFile.getCreationDate();
		Float size = metaFile.getSize();
		System.out.println(file.getParent());
		System.out.println("◼"+ name + "\t" + title + " " + creattionDate + " " + size + " Ko");
	}

}
