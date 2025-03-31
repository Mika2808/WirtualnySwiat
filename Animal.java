import java.util.Random;

public class Animal extends Organism{
    @Override
    public void Action(){
        Polozenie tmp_cords = GetCords();
        boolean move = false;

        while (!move) {
            Random random = new Random();
            int number = random.nextInt(4);

            //UP
            if (number == 0) {
                if (tmp_cords.y > 1) {
                    SetCords(tmp_cords.x, tmp_cords.y - 1);
                    SetLastMove(Stale.UP);
                    move = true;
                }
                else continue;
            }
            //DOWN
            else if (number == 1) {
                if (tmp_cords.y < world.GetHeight()) {
                    SetCords(tmp_cords.x, tmp_cords.y + 1);
                    SetLastMove(Stale.DOWN);
                    move = true;
                }
			    else continue;
            }
            //RIGHT
            else if (number == 2) {
                if (tmp_cords.x < world.GetWidth()) {
                    SetCords(tmp_cords.x + 1,tmp_cords.y);
                    SetLastMove(Stale.RIGHT);
                    move = true;
                }
			    else continue;
            }
            //LEFT
            else {
                if (tmp_cords.x > 1) {
                    SetCords(tmp_cords.x - 1, tmp_cords.y);
                    SetLastMove(Stale.LEFT);
                    move = true;
                }
                else continue;
            }
        }
    }
    @Override
    public  void Collision(Organism attacked){

        //SAME ANIMAL
        if (getName().equals(attacked.getName())) {
            UndoMove();
            int child = attacked.EmptyPLace();
            if (child != -1) {
                Polozenie tmp_cords = attacked.GetCords();

                if (child == Stale.LEFT) {
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x - 1, tmp_cords.y);
                }
                else if (child == Stale.RIGHT) {
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x + 1, tmp_cords.y);
                }
                else if (child == Stale.UP) {
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x, tmp_cords.y - 1);
                }
                else if (child == Stale.DOWN) {
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x, tmp_cords.y + 1);
                }
            }
            attacked.SetMove(true);
        }
        //IF ANIMAL IS ANTELOPE OR TURTLE
	    else if ((attacked.getName().equals("ANTELOPE")) || (attacked.getName().equals("TURTLE")
                || (attacked.getName().equals("HUMAN")))){
            attacked.Collision(this);
        }
        else {
            //IF ATTACKED ORGANISM IS NOT A PLANT
            if (attacked.GetInitiative()!=0) {
                if (GetStrength() > attacked.GetStrength()) {
                    world.GetOrganisms().remove(attacked,this);

                }
			    else if (GetStrength() < attacked.GetStrength()) {
                    world.GetOrganisms().remove(this,attacked);

                }
                else{
                    world.GetOrganisms().remove(attacked,this);
                }
            }
            //IF ATTACKED ORGANISM IS A PLANT
            else {
                attacked.Collision(this);
                }
        }

    }
    @Override
    public  void Drawing(){}
    @Override
    public String getName(){
        return "";
    }
}
