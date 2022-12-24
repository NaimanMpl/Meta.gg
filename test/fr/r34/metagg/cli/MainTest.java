package fr.r34.metagg.cli;

import fr.r34.metagg.MetaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void testModifications() throws IOException {

        File file = new File("/Users/naiman/Desktop/testODT/partiel.odt");
        MetaFile tmpMetaFile = new MetaFile(file);

        assertNotNull(tmpMetaFile);

        tmpMetaFile.setTitle("");
        tmpMetaFile.setSubject("");
        tmpMetaFile.getKeywords().clear();
        tmpMetaFile.save();
        tmpMetaFile.deleteTempFolder();

        assertTrue(tmpMetaFile.getTitle().isEmpty());
        assertTrue(tmpMetaFile.getSubject().isEmpty());
        assertTrue(tmpMetaFile.getKeywords().isEmpty());

        MetaFile metaFile = new MetaFile(file);
        metaFile.setTitle("Hello World !");
        metaFile.setSubject("Un sujet...");
        String[] keywords = {"1", "2", "3"};
        metaFile.getKeywords().add("1");
        metaFile.getKeywords().add("2");
        metaFile.getKeywords().add("3");

        metaFile.save();
        metaFile.deleteTempFolder();

        metaFile = new MetaFile(file);

        assertEquals("Hello World !", metaFile.getTitle());
        assertEquals("Un sujet...", metaFile.getSubject());
        assertArrayEquals(keywords, metaFile.getKeywords().toArray());

        metaFile.deleteTempFolder();
    }

    @Test
    public void testFolderDelete() throws IOException {
        File file = new File("/Users/naiman/Desktop/testODT/partiel.odt");
        MetaFile metaFile = new MetaFile(file);
        metaFile.deleteTempFolder();
        assertFalse(metaFile.getDestDir().exists());
    }

}