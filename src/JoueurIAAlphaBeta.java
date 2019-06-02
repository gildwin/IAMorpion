import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Cette classe représente l'IA qui implémente l'algorithme Alpha-Beta et l'algorithme d'approfondissement itératif
 */
public class JoueurIAAlphaBeta extends JoueurIA{

    /**
     * Constructeur
     * @param name  Nom choisi pour l'IA
     */
    public JoueurIAAlphaBeta(String name){
        super(name);
    }

    /**
     * Fonction qui vérifie s'il y a au moins un certain nombre de pions d'un joueur sur une ligne/colonne/diagonale du plateau
     * @param nbPions   Nombre de pions minimum sur la ligne/colonne/diagonale
     * @param symbole   Symbole du pion du joueur
     * @param x         abscisse du point de départ sur le plateau
     * @param y         ordonnée du point de départ sur le plateau
     * @param dX        Déplacement pour la prochaine case à vérifier en abscisse
     * @param dY        Déplacement pour la prochaine case à vérifier en ordonnée
     * @param plateau   Plateau sur lequel s'effectue la vérification
     * @return          vrai s'il y a au moins nbPions sur la ligne/colonne/diagonale et false sinon
     */
    public boolean auMoins(int nbPions, Symbole symbole, int x, int y, int dX, int dY, Plateau plateau){
        boolean res = false;
        int curX = x;
        int curY = y;
        int curPions = 0;
        while(curX>=0 && curY>=0 && curX<plateau.getTaille() && curY<plateau.getTaille()){
            System.out.println("case("+curX+","+curY+")");
            if(plateau.getCase(curX, curY) == symbole){
                curPions++;
            }
            curX+=dX;
            curY+=dY;
        }
        if(curPions>=nbPions){
            res = true;
        }
        return res;
    }

    /**
     * Evalue l'état de la partie à un instant donné
     * @param etat  Etat de la partie
     * @return      une évaluation entre -100 et 100 de la partie en cours
     */
    public int h(Etat etat){
        if (etat.situationCourante() instanceof Victoire) {
            if(this == ((Victoire) etat.situationCourante()).getVainqueur()){
                return 100;
            }
            else{
                return -100;
            }
        }
        else {
            int res = 0;
            Plateau plateau = etat.getPlateau();
            int ptaille = plateau.getTaille();
            //Gestion lignes et colonnes
            for (int x = 0; x < plateau.getTaille(); x++) {
                //Gestion lignes
                if (!(auMoins(1, Symbole.O, x, 0, 0, 1, plateau) && auMoins(1, Symbole.X, x, 0, 0, 1, plateau))) {
                    if (auMoins(3, Symbole.X, x, 0, 0, 1, plateau)) {
                        res = res - 20;
                    } else if (auMoins(3, Symbole.O, x, 0, 0, 1, plateau)) {
                        res = res + 20;
                    } else if (auMoins(2, Symbole.X, x, 0, 0, 1, plateau)) {
                        res = res - 5;
                    } else if (auMoins(2, Symbole.O, x, 0, 0, 1, plateau)) {
                        res = res + 5;
                    } else if (auMoins(1, Symbole.X, x, 0, 0, 1, plateau)) {
                        res = res - 1;
                    } else if (auMoins(1, Symbole.O, x, 0, 0, 1, plateau)) {
                        res = res + 1;
                    }
                }
            }

            for (int y = 0; y < plateau.getTaille(); y++) {
            //Gestion colonnes
                if (!(auMoins(1, Symbole.O, 0, y, 1, 0, plateau) && auMoins(1, Symbole.X, 0, y, 1, 0, plateau))) {
                    if (auMoins(3, Symbole.X, 0, y, 1, 0, plateau)) {
                        res = res - 20;
                    } else if (auMoins(3, Symbole.O, 0, y, 1, 0, plateau)) {
                        res = res + 20;
                    } else if (auMoins(2, Symbole.X, 0, y, 1, 0, plateau)) {
                        res = res - 5;
                    } else if (auMoins(2, Symbole.O, 0, y, 1, 0, plateau)) {
                        res = res + 5;
                    } else if (auMoins(1, Symbole.X, 0, y, 1, 0, plateau)) {
                        res = res - 1;
                    } else if (auMoins(1, Symbole.O, 0, y, 1, 0, plateau)) {
                        res = res + 1;
                    }
                }
            }

            int PartPlat3 = plateau.getTaille();
            //Gestion diagonales
            for(int i = 0; (i<plateau.getTaille()) || PartPlat3==3; i++){
                //Diagonales montantes
                if (!(auMoins(1, Symbole.O, ptaille-1, i, -1, 1, plateau) && auMoins(1, Symbole.X, ptaille-1, i, -1, 1, plateau))) {
                    if(auMoins(3, Symbole.O, ptaille-1, i, -1, 1, plateau)){
                        res = res + 20;
                    }
                    else if(auMoins(3, Symbole.X, ptaille-1, i, -1, 1, plateau)){
                        res = res - 20;
                    }
                    else if(auMoins(2, Symbole.O, ptaille-1, i, -1, 1, plateau)){
                        res = res + 5;
                    }
                    else if(auMoins(2, Symbole.X, ptaille-1, i, -1, 1, plateau)){
                        res = res - 5;
                    }
                    else if(auMoins(1, Symbole.O, ptaille-1, i, -1, 1, plateau)){
                        res = res + 1;
                    }
                    else if(auMoins(1, Symbole.X, ptaille-1, i, -1, 1, plateau)){
                        res = res - 1;
                    }
                }
                if(i>0){
                    if (!(auMoins(1, Symbole.O, ptaille-1-i, 0, -1, 1, plateau) && auMoins(1, Symbole.X, ptaille-1-i, 0, -1, 1, plateau))) {
                        if(auMoins(3, Symbole.O, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res + 20;
                        }
                        else if(auMoins(3, Symbole.X, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res - 20;
                        }
                        else if(auMoins(2, Symbole.O, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res + 5;
                        }
                        else if(auMoins(2, Symbole.X, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res - 5;
                        }
                        else if(auMoins(1, Symbole.O, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res + 1;
                        }
                        else if(auMoins(1, Symbole.X, ptaille-1-i, 0, -1, 1, plateau)){
                            res = res - 1;
                        }
                    }
                }

                //Diagonales descendantes
                if (!(auMoins(1, Symbole.O, 0, i, 1, 1, plateau) && auMoins(1, Symbole.X, 0, i, 1, 1, plateau))) {
                    if(auMoins(3, Symbole.O, 0, i, 1, 1, plateau)){
                        res = res + 20;
                    }
                    else if(auMoins(3, Symbole.X, 0, i, 1, 1, plateau)){
                        res = res - 20;
                    }
                    else if(auMoins(2, Symbole.O, 0, i, 1, 1, plateau)){
                        res = res + 5;
                    }
                    else if(auMoins(2, Symbole.X, 0, i, 1, 1, plateau)){
                        res = res - 5;
                    }
                    else if(auMoins(1, Symbole.O, 0, i, 1, 1, plateau)){
                        res = res + 1;
                    }
                    else if(auMoins(1, Symbole.X, 0, i, 1, 1, plateau)){
                        res = res - 1;
                    }
                }
                if(i>0){
                    if (!(auMoins(1, Symbole.O, i, 0, 1, 1, plateau) && auMoins(1, Symbole.X, i,0, 1, 1, plateau))) {
                        if(auMoins(3, Symbole.O, i, 0, 1, 1, plateau)){
                            res = res + 20;
                        }
                        else if(auMoins(3, Symbole.X, i, 0, 1, 1, plateau)){
                            res = res - 20;
                        }
                        else if(auMoins(2, Symbole.O, i, 0, 1, 1, plateau)){
                            res = res + 5;
                        }
                        else if(auMoins(2, Symbole.X, i, 0, 1, 1, plateau)){
                            res = res - 5;
                        }
                        else if(auMoins(1, Symbole.O, i, 0, 1, 1, plateau)){
                            res = res + 1;
                        }
                        else if(auMoins(1, Symbole.X, i, 0, 1, 1, plateau)){
                            res = res - 1;
                        }
                    }
                }
                PartPlat3--;
            }
            return res;
        }
    }

    /**
     * Application de l'algorithme Alpha-Beta vu en cours d'Intelligence Artificielle
     * @param n         Etat de la partie en cours
     * @param alpha     Valeur maximum enregistré lors du parcours de l'arbre
     * @param beta      Valeur minimum enregistré lors du parcours de l'arbre
     * @return          alpha si le noeud courant est un noeud max et beta sinon
     */
    public float alphaBeta(Etat n, float alpha, float beta){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        if (n.situationCourante() instanceof Victoire) {
            if(this == ((Victoire) n.situationCourante()).getVainqueur()){
                return 100;
            }
            else{
                return -100;
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

    /**
     * Adaptation de l'algorithme Alpha-Beta à une profondeur maximum donné
     * @param n         Etat de la partie en cours
     * @param alpha     Valeur maximum enregistré lors du parcours de l'arbre
     * @param beta      Valeur minimum enregistré lors du parcours de l'arbre
     * @param curProf   Profondeur actuelle dans le parcours de l'arbre
     * @param maxProf   Profondeur maximum possiblement atteinte
     * @return          une évaluation de la partie en cours en fonction du noeud concerné (maximum ou minimum)
     */
    public float ApprofondissementIteratif(Etat n, float alpha, float beta, int curProf, int maxProf){
        //On est arrivé à une feuille de l'arbre
        if (n.situationCourante() instanceof Victoire) {
            if (this == ((Victoire) n.situationCourante()).getVainqueur()) {
                return 100;
            } else {
                return -100;
            }
        }

        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        if (actionsPossibles.isEmpty()) {
            return 0;
        }

        //On est à une feuille de la longueur courante, mais pas de l'arbre
        if(curProf==maxProf) {
           return h(n);
        }
        //En cours de calcul
        else {
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

    /**
     *
     * @param etat Etat de la partie
     * @return     Action choisie par l'algorithme Alpha-Beta ou l'algorithme Approfondissement itératif
     */
    @Override
    public Action choisirAction(Etat etat){
        List<Action> actionsPossibles = etat.actionsPossibles();

        Action actionChoisie = actionsPossibles.get(0);
        float meilleurScore = -99;
        int i;

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
//                    this.proposerAction(actionChoisie);
                }
            }
            this.proposerAction(actionChoisie);
            profCourant++;
        }
        return actionChoisie;
    }

}
