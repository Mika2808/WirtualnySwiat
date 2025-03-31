import javax.swing.*;
import java.awt.*;

public class Human extends Animal{
    Human(World world1, int x, int y) {
        SetStrength(5);
        SetInitiative(4);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }

    @Override
    public String getName() {
        return "HUMAN";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/human.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Collision(Organism attacked) {
       //CHECKING SUPERPOWER
        if (world.GetSuperpower())
        {
            int space = EmptyPLace();
            //IF THERE IS NO EMPTY PLACE
            if(space == -1) {
                world.GetOrganisms().remove(attacked, this);
            }
            //IF THERE IS AN EMPTY PLACE
		    else {
                Polozenie tmp_cords = GetCords();
                if (space == Stale.UP) {
                    SetCords(tmp_cords.x, tmp_cords.y - 1);
                    SetLastMove(Stale.UP);
                }
                else if (space == Stale.DOWN) {
                    SetCords(tmp_cords.x, tmp_cords.y + 1);
                    SetLastMove(Stale.DOWN);
                }
                else if (space == Stale.LEFT) {
                    SetCords(tmp_cords.x - 1, tmp_cords.y);
                    SetLastMove(Stale.LEFT);
                }
                else if (space == Stale.RIGHT) {
                    SetCords(tmp_cords.x + 1, tmp_cords.y);
                    SetLastMove(Stale.RIGHT);
                }
            }
        }

        //IF SUPERPOWER IS OFF
	    else {
            //ATTACKED IS NOT A PLANT
            if (attacked.GetInitiative() != 0) {
                if (GetStrength() > attacked.GetStrength()) {
                    world.GetOrganisms().remove(attacked, this);
                }
			    else if (GetStrength() < attacked.GetStrength()) {
                    world.GetOrganisms().remove(this, attacked);
                }
			    else {
                    world.GetOrganisms().remove(attacked, this);
                }
            }
            //ATTACKED IS A PLANT
            else {
                attacked.Collision(this);
            }
        }
    }
    @Override
    public void Action(){
        boolean bool_move = false;

        //GETTING DATA
        Polozenie tmp_cords = GetCords();
        int move = world.GetNextMove();

        while (!bool_move) {
            if (move == Stale.UP) {
                SetCords(tmp_cords.x, tmp_cords.y - 1);
                SetLastMove(Stale.UP);
                bool_move = true;
            }
            else if (move == Stale.DOWN) {
                SetCords(tmp_cords.x, tmp_cords.y + 1);
                SetLastMove(Stale.DOWN);
                bool_move = true;
            }
            else if (move == Stale.LEFT) {
                SetCords(tmp_cords.x - 1, tmp_cords.y);
                SetLastMove(Stale.LEFT);
                bool_move = true;
            }
            else if (move == Stale.RIGHT) {
                SetCords(tmp_cords.x + 1, tmp_cords.y);
                SetLastMove(Stale.RIGHT);
                bool_move = true;
            }
            //SUPERPOWER ON
            else if (move == Stale.VEGETABLE) {
                world.SetRounds(0);
                world.SetSuperpower(true);
                bool_move = true;
            }
		    else {
                SetLastMove(0);
                bool_move = true;
            }
        }

        if(world.GetRounds() == 5) {
            world.SetSuperpower(false);
            world.TurnOffSuperpower();
        }
    }
}
