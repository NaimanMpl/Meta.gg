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
	 * Gestionnaire de répertoire permettant de repérer et accéder aux différents dossiers, sous dossiers et fichiers
	 * @version 0.0.2
	 * @author Andrea PL, Naïman Mpl
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

	/**
	 * Méthode permettant de lister tous les fichiers
	 * de type ODT présent dans un dossier.
	 * La méthode parcourt tous les éléments du dossier
	 * passé en paramètre et vérifie si le mime type de
	 * l'élément traité correspond à celui des fichiers ODT.
	 * Si c'est le cas on l'ajoute à la liste.
	 *
	 * @param folder	Dossier dont on veut récupérer la liste de ses fichiers ODT.
	 * @return	La liste des fichiers ODT appartenant au dossier en paramètre.
	 */
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
