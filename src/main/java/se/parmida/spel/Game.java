package se.parmida.spel;
import se.parmida.spel.model.Burglar;
import se.parmida.spel.model.Resident;

import java.util.Scanner;

public class Game {
    boolean running = true;
    private boolean fryingPanFound = false;
    Scanner scanner = new Scanner(System.in);

    Resident resident;
    Burglar burglar;

    public Game (boolean running) {this.running = running;}

    public void chooseDirection(){

        resident = new Resident("Resident", 12, 3);
        burglar = new Burglar("Burglar", 12, 4);
        story();
        vardagsrum();
        rooms();

        while (running){
            String userInput = getUserInput();
            running = processInput(userInput);

            if (!running) {
                break;
            }
            System.out.println();
            rooms();
        }
        System.out.println("Spelet är avslutad, tack för att du spelat!");
    }

    private boolean processInput(String input){
        switch (input){
            case "vardagsrum" -> vardagsrum();
            case "kök" -> kok();
            case "sovrum" -> sovrum();
            case "hall" -> hall();
            case "kontor" -> {
                if (!kontor()) {
                    return false;
                }
            }
            case "avsluta" -> {
                return false;
            }
            default -> System.out.println("Fel inmatning");
        }
        return true;
    }

    private static final String VARDAGSRUM = "vardagsrum";
    private static final String KOK = "kök";
    private static final String SOVRUM = "sovrum";
    private static final String HALL = "hall";
    private static final String KONTOR = "kontor";
    private static final String START = "start";
    String currentLocation = START;


    private void vardagsrum() {
        if (!currentLocation.equals(VARDAGSRUM)){
            currentLocation = VARDAGSRUM;
            System.out.println("Du är i vardagsrummet");
        } else {
            System.out.println("Välj ett annat rum");
        }
    }

    private void kok() {
        if (currentLocation.equals(VARDAGSRUM)) {
            currentLocation = KOK;
            System.out.println("Du är i köket");

                if (!fryingPanFound) {
                    System.out.println("Du har hittat en stekpanna. Vill du ta upp stekpannan och använda som försvar? ja/nej");

                    String userInput = getUserInput();
                    if (userInput.equals("ja")){
                        System.out.println("Du har tagit upp stekpannan och din styrka har nu ökat! Nu måste vi hitta tjuven.");
                        fryingPanFound = true;
                        resident.addDamage(3);
                    } else {
                        System.out.println("Du lämnar stekpannan.");
                    }
                } else {
                    System.out.println("Stekpannan är redan använd");
                }
        } else {
            System.out.println("Du måste först gå tillbaka till vardagsrummet");}
    }


    private void sovrum() {
        if (currentLocation.equals(VARDAGSRUM)){
            currentLocation = SOVRUM;
            System.out.println("Du är i sovrummet");
        } else {
            System.out.println("Du måste först gå tillbaka till vardagsrummet");
        }
    }

    private void hall() {
        if (currentLocation.equals(VARDAGSRUM)){
            currentLocation = HALL;
            System.out.println("Du är i hallen och hittar inbrottstjuven");

            if (!fryingPanFound){
                System.out.println("Du är inte tillräckligt stark för att besegra tjuven, gå tillbaka till ett rum för att hitta ett föremål");
            } else {
                System.out.println("Du har stekpannan och kan därför strida mot tjuven");
                initiateFight();
            }
        } else {
            System.out.println("Du måste först gå tillbaka till vardagsrummet");
        }
    }

    private boolean kontor() {
        if (currentLocation.equals(VARDAGSRUM)){
            currentLocation = KONTOR;
            System.out.println("Du är i kontoret och ser en telefon, men behöver först besegra tjuven");


            if (!burglar.isConscious()){
                System.out.println("Vill du ringa polisen? ja/nej");
                String userInput = getUserInput();
                if (userInput.equals("ja"))
                    System.out.println("Du ringer polisen och kan nu andas ut!");
            } return false;
        } else {
            System.out.println("Du måste först gå tillbaka till vardagsrummet");
        }
        return true;
    }

    private void initiateFight(){
        System.out.println();
        while (running && resident.isConscious()) {
            resident.punch(burglar);
            System.out.println("Du slår tjuven, tjuvens hälsa är: " + burglar.getHealth());

            if (!burglar.isConscious()){
                System.out.println("Tjuven har är medvetslös, du måste nu kontakta polisen!");
                break;
            }

            burglar.punch(resident);
            System.out.println("Tjuven slår tillbaka, din hälsa är: " + resident.getHealth());

            if (!resident.isConscious()){
                System.out.println("Tjuven lyckades försvara sig själv och du är medvetslös");
                running = false;
                break;
            }
        }
    }

    private void story(){
        System.out.println("Du sover på soffan i vardagsrummet " +
                "men hör plötsligt ett högt ljud från någonstans i huset. " +
                "Kan det vara en inbrottstjuv?? Låt oss kolla.");
    }

    private void rooms(){
        System.out.println("Välj ett annat rum:  Vardagsrum | Kök | Sovrum | Hall | Kontor | Avsluta ");
    }

    private String getUserInput(){
        return scanner.nextLine();
    }
}