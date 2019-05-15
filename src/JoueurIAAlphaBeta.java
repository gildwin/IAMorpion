import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JoueurIAAlphaBeta extends JoueurIA{

    public JoueurIAAlphaBeta(String name){
        super(name);
    }

    public float utilite(Etat n){
        return (float)0.2;
    }

    /*public float max(Etat n, float alpha, float beta){
        List<Action> actionsPossibles = n.actionsPossibles();
        int action;
        for(action = 0; action<actionsPossibles.size(); action++){
            while(alpha<beta){
                alpha =
            }
        }
        return alpha;
    }*/

    public float min(Etat n, float alpha, float beta){
        return beta;
    }

    public float alphaBeta(Etat n, float alpha, float beta){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        if (n.situationCourante() instanceof Victoire) {
            if(this == ((Victoire) n.situationCourante()).getVainqueur()){
                return 1;
            }
            else{
                return -1;
            }
        }
        if(actionsPossibles.isEmpty()) {
            return 0;
        }
        else{
            //Type Max
            if(this.getID()==joueur){
                int action;
                for(action = 0; action<actionsPossibles.size(); action++){
                    if (alpha<beta){
                        Etat copieden = n.clone();
                        Action curAction = copieden.actionsPossibles().get(action);
                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        System.out.println("Type Max");
                        System.out.println("Action choisie : " + curAction);
                        System.out.println("Actions possibles : " + copieden.actionsPossibles());
                        alpha = maxab(alpha, alphaBeta(copieden,alpha,beta));
                        System.out.println("Valeur de Alpha : " + alpha);
                    }
                }
                return alpha;
            }
            //Type Min
            else{
                int action;
                for(action = 0; action<actionsPossibles.size(); action++){
                    if (alpha<beta){
                        Etat copieden = n.clone();
                        Action curAction = copieden.actionsPossibles().get(action);
                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        System.out.println("Type Min");
                        System.out.println("Action choisie : " + curAction);
                        System.out.println("Actions possibles : " + copieden.actionsPossibles());
                        beta = minab(alpha, alphaBeta(copieden,alpha,beta));
                        System.out.println("Valeur de Beta : " + beta);
                    }
                }
                return beta;
            }
        }
    }

    public float maxab(float a, float b){
        float ret = a;
        if (b > a) ret = b;
        return ret;
    }

    public float minab(float a, float b){
        float ret = a;
        if (b < a) ret = b;
        return ret;
    }

    @Override
    public Action choisirAction(Etat etat){
        List<Action> actionsPossibles = etat.actionsPossibles();
        Action actionChoisie = actionsPossibles.get(0);
        float meilleurScore = -1;
        int i;
        for(i = 0; i<actionsPossibles.size(); i++){
            Etat copieEtat = etat.clone();
            copieEtat.setIdJoueurCourant(copieEtat.getIdJoueurCourant()+1);
            copieEtat.jouer(copieEtat.actionsPossibles().get(i));
            float currentScore = alphaBeta(copieEtat, -99, 99);
            if(currentScore>meilleurScore){
                meilleurScore = currentScore;
                actionChoisie = actionsPossibles.get(i);
            }
        }
        return actionChoisie;
    }

}
