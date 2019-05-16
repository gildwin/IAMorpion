import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JoueurIAAlphaBeta extends JoueurIA{

    public JoueurIAAlphaBeta(String name){
        super(name);
    }

    public float h(Etat etat){
        return 1;
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

    public float ApprofondissementIteratif(Etat n, float alpha, float beta, int curProf, int curMax){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        if(curProf==curMax) {
            if (n.situationCourante() instanceof Victoire) {
                if (this == ((Victoire) n.situationCourante()).getVainqueur()) {
                    return 99;
                } else {
                    return -99;
                }
            }
            if (actionsPossibles.isEmpty()) {
                return 0;
            } else {
                //Type Max
                if (this.getID() == joueur) {
                    int action;
                    for (action = 0; action < actionsPossibles.size() && alpha < beta; action++) {

                        Etat copieden = n.clone();

                        Action curAction = actionsPossibles.get(action);

                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant() + 1);

                        alpha = Math.max(alpha, ApprofondissementIteratif(copieden, alpha, beta, curProf + 1, curMax));
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
                        beta = Math.min(beta, ApprofondissementIteratif(copieden, alpha, beta, curProf + 1, curMax));
                    }
                    return beta;
                }
            }
        }else{
            return h(n);
        }
    }

    @Override
    public Action choisirAction(Etat etat){
        List<Action> actionsPossibles = etat.actionsPossibles();

        Action actionChoisie = actionsPossibles.get(0);
        float meilleurScore = -99;
        int i;

        for(i = 0; i<actionsPossibles.size(); i++){
            Etat copieEtat = etat.clone();
            Action curEtat = actionsPossibles.get(i);
            copieEtat.jouer(curEtat);
            copieEtat.setIdJoueurCourant(copieEtat.getIdJoueurCourant()+1);

            float currentScore = alphaBeta(copieEtat, -99, 99);

            if(currentScore>meilleurScore){
                meilleurScore = currentScore;
                actionChoisie = actionsPossibles.get(i);
                this.proposerAction(actionsPossibles.get(i));
            }
        }
        return actionChoisie;
    }

}
