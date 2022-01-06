package tech.getarrays.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String posteAvant;
    private String domaineRecherche;
    private String statut;
    private int statutnumber;
    private String numTelephone;
    private String competences;
    @Column(nullable = false, updatable = false)
    private String codeEmploye;



    public Employee(String nom, String prenom, String adresse, String email, String posteAvant, String domaineRecherche, String statut, int statutnumber, String numTelephone, String competences, String codeEmploye) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.posteAvant = posteAvant;
        this.domaineRecherche = domaineRecherche;
        this.statut = statut;
        this.statutnumber = statutnumber;
        this.numTelephone = numTelephone;
        this.competences = competences;
        this.codeEmploye = codeEmploye;
    }

    public Employee(long id, String nom, String prenom, String adresse, String email, String posteAvant, String domaineRecherche, String statut, int statutnumber, String numTelephone, String competences, String codeEmploye) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.posteAvant = posteAvant;
        this.domaineRecherche = domaineRecherche;
        this.statut = statut;
        this.statutnumber = statutnumber;
        this.numTelephone = numTelephone;
        this.competences = competences;
        this.codeEmploye = codeEmploye;
    }

    public Employee() {

    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getPosteAvant() {
        return posteAvant;
    }

    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public String getStatut() {
        return statut;
    }

    public int getStatutnumber() {
        return statutnumber;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public String getCompetences() {
        return competences;
    }

    public String getCodeEmploye() {
        return codeEmploye;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosteAvant(String posteAvant) {
        this.posteAvant = posteAvant;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setStatutnumber(int statutnumber) {
        this.statutnumber = statutnumber;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public void setCodeEmploye(String codeEmploye) {
        this.codeEmploye = codeEmploye;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", posteAvant='" + posteAvant + '\'' +
                ", domaineRecherche='" + domaineRecherche + '\'' +
                ", statut='" + statut + '\'' +
                ", statutnumber=" + statutnumber +
                ", numTelephone='" + numTelephone + '\'' +
                ", competences='" + competences + '\'' +
                ", codeEmploye='" + codeEmploye + '\'' +
                '}';
    }
}
