package bowling;

import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {
    
    private String[] nomDesJoueurs;
    private Map<String, PartieMonoJoueur> parties;
    private int joueurActuel;
    private boolean partieDemarree = false;

    private boolean partieTerminee() {
        for (PartieMonoJoueur p : parties.values()) {
            if (!p.estTerminee()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
        if(nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
            throw new IllegalArgumentException("Le tableau des joueurs ne peut pas être vide ou null");
        } else{
            this.nomDesJoueurs = nomsDesJoueurs;
            this.parties = new HashMap<>();
            
            for(String nom : nomsDesJoueurs) {
                parties.put(nom, new PartieMonoJoueur());
            }
            this.joueurActuel = 0;
            this.partieDemarree = true;
        }
        
        String joueur = nomDesJoueurs[joueurActuel];
        PartieMonoJoueur partie = parties.get(joueur);

        return "Prochain tir : joueur " + joueur +
         ", tour n° " + (partie.numeroTourCourant()) +
          ", boule n° " + (partie.numeroProchainLancer());
        
    }

    @Override
    public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
        if (!partieDemarree){
            throw new IllegalStateException("La partie n'est pas démarrée.");
        }
        String joueur = nomDesJoueurs[joueurActuel];
        PartieMonoJoueur partie = parties.get(joueur);

        boolean doitRelancer = partie.enregistreLancer(nombreDeQuillesAbattues);

        if (!doitRelancer) {
            // Changement de joueur
            joueurActuel = (joueurActuel + 1) % nomDesJoueurs.length;
        }

        if (partieTerminee())
            return "Partie terminée";

        joueur = nomDesJoueurs[joueurActuel];
        partie = parties.get(joueur);

        return "Prochain tir : joueur " + joueur +
                ", tour n° " + partie.numeroTourCourant() +
                ", boule n° " + partie.numeroProchainLancer();
    }

    @Override
    public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
        PartieMonoJoueur partie = parties.get(nomDuJoueur);
        if (partie == null)
            throw new IllegalArgumentException("Joueur inconnu");

        return partie.score();
    }

    }


