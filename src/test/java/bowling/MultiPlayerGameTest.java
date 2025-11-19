package bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class MultiPlayerGameTest {
@Test
    void testScenarioComplet() {
        IPartieMultiJoueurs partie = new PartieMultiJoueurs();
        String[] players = { "Pierre", "Paul" };

        // --- Démarrage ---
        assertEquals(
                "Prochain tir : joueur Pierre, tour n° 1, boule n° 1",
                partie.demarreNouvellePartie(players)
        );

        // Pierre lance 5
        assertEquals(
                "Prochain tir : joueur Pierre, tour n° 1, boule n° 2",
                partie.enregistreLancer(5)
        );

        // Pierre lance 3 → fin du tour
        assertEquals(
                "Prochain tir : joueur Paul, tour n° 1, boule n° 1",
                partie.enregistreLancer(3)
        );

        // Paul lance 10 → STRIKE → fin immédiate du tour
        assertEquals(
                "Prochain tir : joueur Pierre, tour n° 2, boule n° 1",
                partie.enregistreLancer(10)
        );

        // Pierre lance 7
        assertEquals(
                "Prochain tir : joueur Pierre, tour n° 2, boule n° 2",
                partie.enregistreLancer(7)
        );

        // Pierre lance 3 → spare → fin du tour
        assertEquals(
                "Prochain tir : joueur Paul, tour n° 2, boule n° 1",
                partie.enregistreLancer(3)
        );

        // --- Score Pierre ---
        // Pierre : (5 + 3) + (7 + 3) = 8 + 10 = 18
        assertEquals(18, partie.scorePour("Pierre"));

        // --- Score Paul ---
        // Paul : STRIKE = 10
        assertEquals(10, partie.scorePour("Paul"));

        // --- Joueur inconnu ---
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            partie.scorePour("Jacques");
        });
        assertEquals("Joueur inconnu", e.getMessage());
    }
}
