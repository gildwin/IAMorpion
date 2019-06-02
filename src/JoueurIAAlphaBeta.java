import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JoueurIAAlphaBeta extends JoueurIA{

    public JoueurIAAlphaBeta(String name){
        super(name);
    }

    public Symbole vicEncorePossibleLigne(Plateau plateau, int x){
        Symbole sym = Symbole.VIDE;
        int CptSymbConsec = 0;
        for(int k = 0; k<plateau.getTaille(); k++) {
            Symbole curSym = plateau.getCase(x,k);
            if(curSym == sym || curSym == Symbole.VIDE){
                CptSymbConsec++;
                if(CptSymbConsec == 4){
                    return curSym;
                }
            }else {
                CptSymbConsec = 0;
                sym = curSym;
            }
        }
        return Symbole.VIDE;
    }

    public Symbole vicEncorePossibleColonne(Plateau plateau, int y){
        Symbole sym = Symbole.VIDE;
        int CptSymbConsec = 0;
        for(int k = 0; k<plateau.getTaille(); k++) {
            Symbole curSym = plateau.getCase(k,y);
            if(curSym == sym || curSym == Symbole.VIDE){
                CptSymbConsec++;
                if(CptSymbConsec == 4){
                    return curSym;
                }
            }else {
                CptSymbConsec = 0;
                sym = curSym;
            }
        }
        return Symbole.VIDE;
    }

    /*public Symbole vicEncorePossibleDiag(Plateau plateau) {
        Symbole sym = Symbole.VIDE;
        int CptSymbConsec = 0;
        //Diag HGBD
        for (int k = 0; k < plateau.getTaille(); k++) {

        }
        return Symbole.VIDE;
    }*/

    /*public float h(Etat etat){
        Plateau plateau = etat.getPlateau();
        //Si quelqu'un gagne, 100 ou -100
        if (etat.situationCourante() instanceof Victoire) {
            if(this == ((Victoire) etat.situationCourante()).getVainqueur()){
                return 100;
            }
            else{
                return -100;
            }
        }
        //Sinon, il faut évaluer la situation
        else{
            //Human
            int nX = 0;
            //AI
            int nO = 0;
            for(int k = 0; k<plateau.getTaille(); k++){
                Symbole symL = vicEncorePossibleLigne(plateau, k);
                Symbole symC = vicEncorePossibleColonne(plateau, k);
                if(symL == Symbole.X){
                    nX++;
                }else if(symL == Symbole.O){
                    nO++;
                }
                if(symC == Symbole.X){
                    nX++;
                }else if(symC == Symbole.O){
                    nO++;
                }
            }
            return 100*(nO-2*nX)/(nO+2*nX+1);
        }
    }*/

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

    public int h(Etat etat){
        //System.out.println("Appel");
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
                //System.out.println("Nouvelle ligne");
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
            //System.out.println("Nouvelle colonne");
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
                System.out.println("Nouvelle diagonale montante");
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
                    System.out.println("Nouvelle diagonale montante");
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

                System.out.println("Nouvelle diagonale descendante");
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
                    System.out.println("Nouvelle diagonale descendante");
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
