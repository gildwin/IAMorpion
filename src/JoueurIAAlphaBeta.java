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
        if(actionsPossibles.isEmpty()){
            return utilite(n);
        }
        else{
            //Type Max
            if(this.getID()==joueur){
                int action;
                for(action = 0; action<actionsPossibles.size(); action++){
                    while(alpha<beta){
                        Etat copieden = n.clone();
                        copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                        copieden.actionsPossibles().remove(action);
                        alpha = maxab(alpha, alphaBeta(copieden,alpha,beta));
                    }
                }
                return alpha;
            }
            else{
                if(this.getID()==joueur){
                    int action;
                    for(action = 0; action<actionsPossibles.size(); action++){
                        while(alpha<beta){
                            Etat copieden = n.clone();
                            copieden.setIdJoueurCourant(copieden.getIdJoueurCourant()+1);
                            copieden.actionsPossibles().remove(action);
                            beta = minab(alpha, alphaBeta(copieden,alpha,beta));
                        }
                    }
                    return beta;
                }
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
        return null;
    }

}
