package com.example.mynotes;

public class Note {
    private String nom;
    private String description;
    private String date;
    private String priorite; // "Basse", "Moyenne", "Haute"
    private String photoPath; // Nouveau : chemin de la photo associée

    public Note(String nom, String description, String date, String priorite, String photoPath) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.priorite = priorite;
        this.photoPath = photoPath;
    }

    // Getters
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getPriorite() { return priorite; }
    public String getPhotoPath() { return photoPath; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setPriorite(String priorite) { this.priorite = priorite; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}