package fr.r34.metagg.cli;

import fr.r34.metagg.MetaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void testModifications() {

        File file = new File("C:\\Users\\Naiman\\Desktop\\BanqueSujetODT\\partiel.odt");
        MetaFile metaFile = new MetaFile(file);

        assertNotNull(metaFile);

        metaFile.setTitle("");
        assertTrue(metaFile.getTitle().isEmpty());

        metaFile.setTitle("Hello World !");
        metaFile.setSubject("Un sujet...");
        metaFile.save();
        metaFile.deleteTempFolder();

        assertEquals("Hello World !", metaFile.getTitle());
        assertEquals("Un sujet...", metaFile.getSubject());

        MetaFile newMetaFile = new MetaFile(file);

        assertEquals("Hello World !", newMetaFile.getTitle());
        assertEquals("Un sujet...", newMetaFile.getSubject());

        newMetaFile.deleteTempFolder();
    }

    @Test
    public void testFolderDelete() {
        File file = new File("C:\\Users\\Naiman\\Desktop\\BanqueSujetODT\\partiel.odt");
        MetaFile metaFile = new MetaFile(file);
        metaFile.deleteTempFolder();
        assertFalse(metaFile.getDestDir().exists());
    }

}