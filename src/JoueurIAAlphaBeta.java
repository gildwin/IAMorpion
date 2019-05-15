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


    public float alphaBeta(Etat n, float alpha, float beta){
        List<Action> actionsPossibles = n.actionsPossibles();
        int joueur = n.getIdJoueurCourant();

        System.out.println(n.getIdJoueurCourant()+" "+this.getID());

        if (n.situationCourante() instanceof Victoire) {
            System.out.println(this.getNom() + " et vainqueur : " + ((Victoire) n.situationCourante()).getVainqueur().getNom());
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
                //System.out.println("Max");
                //System.exit(0);
                int action;
                for(action = 0; action<actionsPossibles.size() && alpha<beta; action++){

                        Etat copieden = n.clone();

                        Action curAction = actionsPossibles.get(action);

                        copieden.jouer(curAction);

                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        /*System.out.println("Type Max");
                        System.out.println("Action choisie : " + curAction);
                        System.out.println("Actions possibles : " + copieden.actionsPossibles());*/
                        alpha = Math.max(alpha, alphaBeta(copieden,alpha,beta));
                        //System.out.println("Valeur de Alpha : " + alpha);
                }
                //System.out.println("Valeur de Alpha" + alpha);
                return alpha;
            }
            //Type Min
            else{
                //System.out.println("Min");
                //System.exit(0);
                int action;
                for(action = 0; action<actionsPossibles.size() && alpha<beta; action++){
                        Etat copieden = n.clone();
                        Action curAction = actionsPossibles.get(action);
                        copieden.jouer(curAction);
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        /*System.out.println("Type Min");
                        System.out.println("Action choisie : " + curAction);
                        System.out.println("Actions possibles : " + copieden.actionsPossibles());*/
                        beta = Math.min(beta, alphaBeta(copieden,alpha,beta));
                        //System.out.println("Valeur de Beta : " + beta);
                }
                //System.out.println("Valeur de Beta" + beta);
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

        for(i = 0; i<actionsPossibles.size(); i++){
            Etat copieEtat = etat.clone();
            Action curEtat = actionsPossibles.get(i);
            copieEtat.jouer(curEtat);
            copieEtat.setIdJoueurCourant(copieEtat.getIdJoueurCourant()+1);

            float currentScore = alphaBeta(copieEtat, -99, 99);
            System.out.println("Score de l'action " + curEtat + " : " + currentScore);

            if(currentScore>meilleurScore){
                meilleurScore = currentScore;
                actionChoisie = actionsPossibles.get(i);
            }
        }
        return actionChoisie;
    }

}
