import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JoueurIAAlphaBeta extends JoueurIA{

    public JoueurIAAlphaBeta(String name){
        super(name);
    }

    public float h(Etat etat){
        float cptCroix = 0;
        float cptRond = 0;
        Plateau p = etat.getPlateau();
        int i;
        for(i=0;i<p.getTaille();i++){
            //Test sur les lignes
            Symbole sym1 = p.NSymbolesConsecutifs(i,0,1,0, 2);
            Symbole sym2 = p.NSymbolesConsecutifs(i,0,1,0, 3);
            //Test sur les colonnes
            Symbole sym3 = p.NSymbolesConsecutifs(0, i,0,1, 2);
            Symbole sym4 = p.NSymbolesConsecutifs(0, i,0,1, 3);
            if(sym1.toString().equals("X")){
//                System.out.println("2 Symboles X alignés");
                cptCroix++;
            }else if (sym1.toString().equals("O")){
//                System.out.println("2 Symboles O alignés");
                cptRond++;
            }

            if(sym2.toString().equals("X")){
//                System.out.println("3 Symboles X alignés");
                cptCroix++;
            }else if (sym2.toString().equals("O")){
//                System.out.println("3 Symboles O alignés");
                cptRond++;
            }

            if(sym3.toString().equals("X")){
//                System.out.println("2 Symboles X colonnés");
                cptCroix++;
            }else if (sym3.toString().equals("O")){
                System.out.println("2 Symboles O colonnés");
                cptRond++;
            }

            if(sym4.toString().equals("X")){
//                System.out.println("3 Symboles X colonnés");
                cptCroix++;
            }else if (sym4.toString().equals("O")){
//                System.out.println("3 Symboles O colonnés");
                cptRond++;
            }
        }
        //Test sur les diagonales
        Symbole diag1 = p.NSymbolesConsecutifs(0,0,1,1,2);
        Symbole diag2 = p.NSymbolesConsecutifs(p.getTaille()-1,0,1,-1,2);
        if(diag1.toString().equals("X")){
//            System.out.println("2 Symboles X diagonalés");
            cptCroix++;
        }else if (diag1.toString().equals("O")){
            cptRond++;
        }

        if(diag2.toString().equals("X")){
            cptCroix++;
        }else if (diag2.toString().equals("O")){
            cptRond++;
        }
//        System.out.println("nombre de croix gagnantes : " + cptCroix + " et nombre de ronds gagnants : " + cptRond);
        return 100*(cptRond-cptCroix)/(cptRond+cptCroix+1);
    }

    public float alphaBeta(Etat n, float alpha, float beta){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        if (n.situationCourante() instanceof Victoire) {
            if(this == ((Victoire) n.situationCourante()).getVainqueur()){
                return 99;
            }
            else{
                return -99;
            }
        }
        if(actionsPossibles.isEmpty()) {
            return 0;
        }

        else{
            //Type Max
            if(this.getID()==joueur){
                int action;
                for(action = 0; action<actionsPossibles.size() && alpha<beta; action++){

                        Etat copieden = n.clone();

                        Action curAction = actionsPossibles.get(action);

                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);

                        alpha = Math.max(alpha, alphaBeta(copieden,alpha,beta));
                }
                return alpha;
            }

            //Type Min
            else{
                int action;
                for(action = 0; action<actionsPossibles.size() && alpha<beta; action++){

                        Etat copieden = n.clone();

                        Action curAction = actionsPossibles.get(action);
                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        beta = Math.min(beta, alphaBeta(copieden,alpha,beta));
                }
                return beta;
            }
        }
    }

    public float ApprofondissementIteratif(Etat n, float alpha, float beta, int curProf, int maxProf){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        //On est arrivé à une feuille de l'arbre
        if (n.situationCourante() instanceof Victoire) {
            if (this == ((Victoire) n.situationCourante()).getVainqueur()) {
                return 99;
            } else {
                return -99;
            }
        }
        if (actionsPossibles.isEmpty()) {
            return 0;
        }

        //On est à une feuille de la longueur courante, mais pas de l'arbre
        if(curProf==maxProf) {
            //System.out.println("Profondeur courante : " + curProf + " pour une profondeur max  de : " + maxProf + " - Feuille terminal");
           return h(n);
        }
        //En cours de calcul
        else {
            //System.out.println("Profondeur courante : " + curProf + " pour une profondeur max  de : " + maxProf + " - Noeud");
            //Type Max
            if (this.getID() == joueur) {
                int action;
                for (action = 0; action < actionsPossibles.size() && alpha < beta; action++) {

                    Etat copieden = n.clone();

                    Action curAction = actionsPossibles.get(action);

                    copieden.jouer(curAction);
                    copieden.setIdJoueurCourant(copieden.getIdJoueurCourant() + 1);

                    alpha = Math.max(alpha, ApprofondissementIteratif(copieden, alpha, beta, curProf + 1, maxProf));
                }
                return alpha;
            }

            //Type Min
            else {
                int action;
                for (action = 0; action < actionsPossibles.size() && alpha < beta; action++) {

                    Etat copieden = n.clone();

                    Action curAction = actionsPossibles.get(action);
                    copieden.jouer(curAction);
                    copieden.setIdJoueurCourant(copieden.getIdJoueurCourant() + 1);
                    beta = Math.min(beta, ApprofondissementIteratif(copieden, alpha, beta, curProf + 1, maxProf));
                }
                return beta;
            }
        }
    }

    @Override
    public Action choisirAction(Etat etat){
        List<Action> actionsPossibles = etat.actionsPossibles();

        Action actionChoisie = actionsPossibles.get(0);
        float meilleurScore = -99;
        int i;

        int largeurPlateau = etat.getPlateau().getTaille();

        int profParcours = etat.getPlateau().getToutesCasesLibres().size();
        int profCourant = 1;

        while(profCourant<profParcours) {
            for (i = 0; i < actionsPossibles.size(); i++) {
                Etat copieEtat = etat.clone();
                Action curEtat = actionsPossibles.get(i);
                copieEtat.jouer(curEtat);
                copieEtat.setIdJoueurCourant(copieEtat.getIdJoueurCourant() + 1);
                System.out.println("Action en cours de traitement : " + actionsPossibles.get(i) + " - Profondeux max : " + profCourant);
                float currentScore = ApprofondissementIteratif(copieEtat, -99, 99, 1, profCourant);
                System.out.println("Score retenu : " + currentScore);
                if (currentScore > meilleurScore) {
                    System.out.println("Action retenue : " + currentScore + "\nAction remplacée : " + meilleurScore);
                    meilleurScore = currentScore;
                    actionChoisie = actionsPossibles.get(i);
                    this.proposerAction(actionChoisie);
                }
            }
            profCourant++;
        }
        return actionChoisie;
    }

}
