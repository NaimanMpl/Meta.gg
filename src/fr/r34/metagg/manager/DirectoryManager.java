/**
 * 
 */
package fr.r34.metagg.manager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
	public ArrayList<File> odtInDirectory(File folder) {
		ArrayList<File> odtInFolder = new ArrayList<>();
		try {
			for (File element : folder.listFiles()) {
				String mimetype = element.toURL().openConnection().getContentType();
				if (Objects.equals(mimetype, "application/vnd.oasis.opendocument.text")) {
					odtInFolder.add(element);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return odtInFolder;
	}
}
