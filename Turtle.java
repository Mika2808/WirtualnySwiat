import javax.swing.*;
import java.util.Random;

public class Turtle extends Animal{

    Turtle(World world1, int x, int y) {
        SetStrength(2);
        SetInitiative(1);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName(){

        return "TURTLE";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/turtle.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Action() {
        Random random = new Random();
        int number = random.nextInt(4);

        Polozenie tmp_cords = GetCords();
        boolean move = false;

        if (number == 0) {
            while (!move) {
                number = random.nextInt(4);
                //UP
                if (number == 0) {
                    if (tmp_cords.y > 1) {
                        SetCords(tmp_cords.x, tmp_cords.y - 1);
                        SetLastMove(Stale.UP);
                        move = true;
                    }
                }
                //DOWN
                else if (number == 1) {
                    if (tmp_cords.y < world.GetHeight()) {
                        SetCords(tmp_cords.x, tmp_cords.y + 1);
                        SetLastMove(Stale.DOWN);
                        move = true;
                    }
                }
                //RIGHT
                else if (number == 2) {//PRAWO
                    if (tmp_cords.x < world.GetWidth()) {
                        SetCords(tmp_cords.x + 1, tmp_cords.y);
                        SetLastMove(Stale.RIGHT);
                        move = true;
                    }
                }
                //LEFT
                else if (number == 3) { //LEWO
                    if (tmp_cords.x > 1) {
                        SetCords(tmp_cords.x - 1, tmp_cords.y);
                        SetLastMove(Stale.LEFT);
                        move = true;
                    }
                }
                else {
                    SetLastMove(0);
                    move = true;
                }
            }
        }
    }
    @Override
    public  void Collision(Organism attacked) {

        //TURTLE ATTACK TURTLE
        if (getName().equals(attacked.getName())) {
            UndoMove();
            int child = attacked.EmptyPLace();

            if (child != -1) {
                Polozenie tmp_cords = attacked.GetCords();

                if (child == Stale.LEFT) {
                    attacked.New(attacked.getName(), world, tmp_cords.x - 1, tmp_cords.y);
                }
                else if (child == Stale.RIGHT) {
                    attacked.New(attacked.getName(), world, tmp_cords.x + 1, tmp_cords.y);
                }
                else if (child == Stale.UP) {
                    attacked.New(attacked.getName(), world, tmp_cords.x, tmp_cords.y - 1);
                }
                else if (child == Stale.DOWN) {
                    attacked.New(attacked.getName(), world, tmp_cords.x, tmp_cords.y + 1);
                }
            }
            attacked.SetMove(true);
        }
        //ATTACKED IS A PLANT
	    else if (attacked.GetInitiative() == 0) {
            attacked.Collision(this);
        }
        else {
            //TURTLE IS ATTACKED
            if (GetMove()) {
                if (attacked.GetStrength() < 5) {
                    attacked.UndoMove();
                }
                else {
                    world.GetOrganisms().remove(this,attacked);
                    //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
                }
            }
            //TURTLE IS ATTACKING
		    else {
                if (GetStrength() >= attacked.GetStrength()) {
                    world.GetOrganisms().remove(attacked,this);
                    //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
                }
			    else if (GetStrength() < attacked.GetStrength()) {
                    world.GetOrganisms().remove(this,attacked);
                    //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
                }
            }
        }
    }
}
