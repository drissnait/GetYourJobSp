package tech.getarrays.employeemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.RestTemplate;
import tech.getarrays.employeemanager.model.EmployeMail;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.EmployeeList;
import tech.getarrays.employeemanager.service.EmployeeService;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    @Autowired
    private EmailSenderService senderService;

    public Employee employe1;
    public RestTemplate restTemplate  = new RestTemplate();
    //EmployeMail emp=new EmployeMail("driss.naitbelkacem@gmail.com","sujet","objet");
    /*ResponseEntity<Employee[]> response =
            (ResponseEntity<Employee[]>) restTemplate.getForEntity(
                    "http://localhost:8080/all/",
                    Employee[].class);*/
    /*EmployeeList reponse = restTemplate.getForObject(
            "http://localhost:8080/all",
            EmployeeList.class);
    List<Employee> employees = reponse.getEmployees();
    int taille = employees.size();*/
    public static int taille;
    public static int tailleCheck;
    public static Employee employeCourant = new Employee("","","","","","","",0,"","","");
    public static boolean employeExiste=false;


    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
        List<Employee> employees=employeeService.findAllEmployees();
        taille=employees.size();
        tailleCheck=taille;
    }
    public boolean getEmployeExiste(){return employeExiste;}
    public void setEmployeExiste(boolean b){
        employeExiste=b;
    }
    public Employee getEmployeCourant(){
        return employeCourant;
    }
    public void setEmployeCourant(Employee employe){
        employeCourant.setNom(employe.getNom());
        employeCourant.setPrenom(employe.getPrenom());
        employeCourant.setAdresse(employe.getAdresse());
        employeCourant.setEmail(employe.getEmail());
        employeCourant.setPosteAvant(employe.getPosteAvant());
        employeCourant.setDomaineRecherche(employe.getDomaineRecherche());
        employeCourant.setStatut(employe.getStatut());
        employeCourant.setStatutnumber(employe.getStatutnumber());
        employeCourant.setNumTelephone(employe.getNumTelephone());
        employeCourant.setCompetences(employe.getCompetences());
        employeCourant.setCodeEmploye(employe.getCodeEmploye());
    }
    public int getTaille(){
        return taille;
    }
    public void setTaille(int taille){
        this.taille=taille;
    }
    public void printTaille(){
        System.out.println(taille);
    }


    public EmailSenderService emailSenderService=new EmailSenderService();

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees=employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    public static void write(String s) throws IOException {
        try {
            FileWriter fw = new FileWriter("data.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(s);

            pw.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void writeCandidat(String s) throws IOException {
        try {
            FileWriter fw = new FileWriter("dataCandidat.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(s);

            pw.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void writeHistorique(String s) throws IOException {
        try {
            FileWriter fw = new FileWriter("historique.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(s);

            pw.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @PostMapping("/inscription")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws IOException {
        Employee newEmployee=employeeService.addEmployee(employee);
        employe1=employee;
        printTaille();
        int nouvelleTaille=getTaille()+1;
        setTaille(nouvelleTaille);
        System.out.println(employee.getStatutnumber());

        if(employee.getStatutnumber()==1){
            write(employee.getEmail() + "," + employee.getDomaineRecherche() + "\n");
        }else if (employee.getStatutnumber()==2){
            writeCandidat(employee.getEmail() + "," + employee.getDomaineRecherche() + "\n");
        }
        setEmployeCourant(employee);
        sendMail();

        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() throws IOException {
        Employee employe=getEmployeCourant();
        String statutPersonneTrouvee = "";
        int nbPersonne=0;
        boolean personneExiste=false;
        BufferedReader br2=new BufferedReader(new FileReader("historique.txt"));
        String st2;
        while ((st2 = br2.readLine()) != null) {
            String[] strArr2 = st2.split("\\,");
            if(strArr2[2].equals(employe.getEmail())==true){
                personneExiste=true;
                statutPersonneTrouvee=strArr2[4];
                nbPersonne++;
            }
        }

        if(personneExiste==false || (personneExiste==true && statutPersonneTrouvee.equals(employe.getStatut())==false && nbPersonne<=2)) {
            if (tailleCheck < taille && employe.getStatutnumber() == 2) {
                String sujet = "Inscription GetYourJob";
                String mailContenu = "Bonjour,\n\n Vous venez de vous inscrire dans l'application GetYourJob, " +
                        "votre profil est transmis aux recruteurs de votre domaine, vos informations sont les " +
                        "suivantes :\n\n" +
                        "Nom : " + employe.getNom() +
                        "\nPrenom : " + employe.getPrenom() +
                        "\nAdresse : " + employe.getAdresse() +
                        "\nEmail : " + employe.getEmail() +
                        "\nPoste occup?? avant " + employe.getPosteAvant() +
                        "\nDomaine de recherche : " + employe.getDomaineRecherche() +
                        "\nVous ??tes : " + employe.getStatut() +
                        "\nNum??ro de t??l??phone : " + employe.getNumTelephone() +
                        "\nComp??tences : " + employe.getCompetences() +
                        "\n\nVous recevrez des r??ponses de la part des recruteurs si votre profil" +
                        "satisfait leurs besoins, veuillez donc surveiller votre bo??te mail\n\nCordialement,\n\nL'??quipe " +
                        "GetYourJob";

                senderService.sendEmail(employe.getEmail(), sujet, mailContenu);
                BufferedReader br = new BufferedReader(new FileReader("data.txt"));
                String st;
                while ((st = br.readLine()) != null) {
                    String[] strArr = st.split("\\,");
                    if (strArr[1].equals(employe.getDomaineRecherche()) == true) {
                        sujet = "GetYourJob - Candidat dans votre domaine de recherche";
                        mailContenu = "Bonjour,\n\n un candidat est ?? la recherche de travail dans votre" +
                                " domaine de recherche potentiel, ci-dessous ses informations :\n\n" +
                                "Nom : " + employe.getNom() +
                                "\nPrenom : " + employe.getPrenom() +
                                "\nAdresse : " + employe.getAdresse() +
                                "\nEmail : " + employe.getEmail() +
                                "\nPoste occup?? avant " + employe.getPosteAvant() +
                                "\nDomaine de recherche : " + employe.getDomaineRecherche() +
                                "\nVous ??tes : " + employe.getStatut() +
                                "\nNum??ro de t??l??phone : " + employe.getNumTelephone() +
                                "\nComp??tences : " + employe.getCompetences() +
                                "\n\nN'h??sitez pas ?? contacter la personne en question si son profil" +
                                "correspond ?? vos recherches\n\nCordialement,\n\nL'??quipe " +
                                "GetYourJob";
                        senderService.sendEmail(strArr[0], sujet, mailContenu);
                    }
                }
                writeHistorique(employe.getNom() + "," + employe.getPrenom() + "," + employe.getEmail() + "," +
                        employe.getDomaineRecherche() + "," + employe.getStatut() + "\n");
                tailleCheck = taille;
            } else if (tailleCheck < taille && employe.getStatutnumber() == 1) {
                String sujet = "Inscription GetYourJob";
                String mailContenu = "Bonjour,\n\n Vous venez de vous inscrire dans l'application GetYourJob, " +
                        "vous recevrez des mails sur les candidats de votre domaine sur cette adresse mail, vos informations sont les " +
                        "suivantes : \n\n" +
                        "Nom : " + employe.getNom() +
                        "\nPrenom : " + employe.getPrenom() +
                        "\nAdresse : " + employe.getAdresse() +
                        "\nEmail : " + employe.getEmail() +
                        "\nPoste occup?? avant " + employe.getPosteAvant() +
                        "\nDomaine de recherche : " + employe.getDomaineRecherche() +
                        "\nVous ??tes : " + employe.getStatut() +
                        "\nNum??ro de t??l??phone : " + employe.getNumTelephone() +
                        "\nComp??tences : " + employe.getCompetences() +
                        "\n\nVeuillez surveiller votre bo??te mail\n\nCordialement,\n\nL'??quipe " +
                        "GetYourJob";

                senderService.sendEmail(employe.getEmail(), sujet, mailContenu);
                writeHistorique(employe.getNom() + "," + employe.getPrenom() + "," + employe.getEmail() + "," +
                        employe.getDomaineRecherche() + "," + employe.getStatut() + "\n");
            }
        }
        //nom,prenom,email,domaine,statut

    }


    @GetMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee newEmployee=employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
