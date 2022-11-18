package fr.r34.metagg;

public enum Messages {

    FILE_NOT_FOUND("Le fichier n'existe pas !");

    private String msg;
    Messages(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

    public void error() {
        System.err.println(msg);
    }
}
