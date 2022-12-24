/**
 * 
 */
package fr.r34.metagg.manager;

import fr.r34.metagg.MimeTypeOD;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
	public ArrayList<File> directoryContent(File folder, ArrayList<File> odtInFolder) {
		try {
			for(File element : folder.listFiles()) {
				if(element.isDirectory())
					directoryContent(element, odtInFolder);
				else {
					String mimetype = Files.probeContentType(element.toPath());
					for (MimeTypeOD m : MimeTypeOD.values()){
						if (m.getMimetype().equals(mimetype)) {
							odtInFolder.add(element);
						}
					}
				}
			}
			return odtInFolder;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<File> listODTFiles(File dir) {
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".odt");
			}
		});

		for (File file : files) {
			if (file.isDirectory()) {
				listODTFiles(file);
			} else {
				System.out.println(file.getAbsolutePath());
			}
		}
		return new ArrayList<>(Arrays.asList(files));
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
	public ArrayList<File> odtInDirectory(File folder) throws IOException {
		ArrayList<File> odtInFolder = new ArrayList<>();
		for (File element : folder.listFiles()) {
			String mimetype = Files.probeContentType(element.toPath());
			for (MimeTypeOD m : MimeTypeOD.values()){
				if(m.getMimetype().equals(mimetype)){
					odtInFolder.add(element);

				}
			}
		}
		return odtInFolder;
	}
}
