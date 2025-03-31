import javax.swing.*;
import java.util.Random;

public class Antelope extends Animal {
    Antelope(World world1, int x, int y){
        SetStrength(4);
        SetInitiative(4);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName(){
       return "ANTELOPE";
    }
    @Override
    public void Action() {
        Polozenie tmp_cords = GetCords();
        boolean move = false;

        while (!move) {
            Random random = new Random();
            int number = random.nextInt(4);

            //UP
            if (number == 0) {
                if (tmp_cords.y > 2) {
                    SetCords(tmp_cords.x, tmp_cords.y - 2);
                    SetLastMove(Stale.UP);
                    move = true;
                }
            }
            //DOWN
            else if (number == 1) {
                if (tmp_cords.y < world.GetHeight() - 1) {
                    SetCords(tmp_cords.x, tmp_cords.y + 2);
                    SetLastMove(Stale.DOWN);
                    move = true;
                }
            }
            //RIGHT
            else if (number == 2) {
                if (tmp_cords.x < world.GetWidth() - 1) {
                    SetCords(tmp_cords.x + 2, tmp_cords.y);
                    SetLastMove(Stale.RIGHT);
                    move = true;
                }
            }
            //LEFT
            else {
                if (tmp_cords.x > 2) {
                    SetCords(tmp_cords.x - 2, tmp_cords.y);
                    SetLastMove(Stale.LEFT);
                    move = true;
                }
            }
        }
    }
    @Override
    public void Collision(Organism attacked) {

        //ANTELOPE ATTACK ANTELOPE
        if (getName().equals(attacked.getName())) {
            UndoMove();
            int child = attacked.EmptyPLace();
            if (child != -1) {
                Polozenie tmp_cords = attacked.GetCords();

                if (child == Stale.LEFT) {
                    attacked.New(getName(), world, tmp_cords.x - 1, tmp_cords.y);
                }
                else if (child == Stale.RIGHT) {
                    attacked.New(getName(), world, tmp_cords.x + 1, tmp_cords.y);
                }
                else if (child == Stale.UP) {
                    attacked.New(getName(), world, tmp_cords.x, tmp_cords.y - 1);
                }
                else if (child == Stale.DOWN) {
                    attacked.New(getName(), world, tmp_cords.x, tmp_cords.y + 1);
                }
            }
            attacked.SetMove(true);
        }
        else {
            //LOOKING FOR EMPTY SPACE
            int place = EmptyPLace();
            Random random = new Random();

            int number = random.nextInt(2);

            //ANTELOPE DOESN'T RUN AWAY OR THERE IS NO EMPTY PLACE
            if ((number==0) || (place == -1)) {
                //ANTELOPE IS STRONGER
                if (attacked.GetStrength() < GetStrength()) {
                    world.GetOrganisms().remove(attacked,this);
                }
                //ATTACKED ORGANISM IS STRONGER
			    else if (attacked.GetStrength() > GetStrength()) {
                    world.GetOrganisms().remove(this,attacked);
                }
                //STRENGTH IS EQUAL
			    else {
                    //ANTELOPE IS ATTACKED
                    if (GetMove()) {
                        world.GetOrganisms().remove(this,attacked);
                    }
                    //ORGANISM IS ATTACKED BY ANTELOPE
				    else {
                        world.GetOrganisms().remove(attacked,this);
                    }
                }
            }
            else{
                if (place == Stale.LEFT) {
                    SetCords(GetCords().x - 2,GetCords().y);
                }
                else if (place == Stale.RIGHT) {
                    SetCords(GetCords().x + 2,GetCords().y);
                }
                else if (place == Stale.UP) {
                    SetCords(GetCords().x,GetCords().y - 2);
                }
                else if (place == Stale.DOWN) {
                    SetCords(GetCords().x,GetCords().y + 2);
                }
            }
        }
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/antelope.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public int EmptyPLace() {

        //AROUND ORGANISMS
        Organism up_organism = null;
        Organism down_organism = null;
        Organism left_organism = null;
        Organism right_organism = null;
        Polozenie organism_cords = GetCords();

        Organism tmp_organism = world.GetOrganisms().GetHead();
        while(tmp_organism!=null){
            Polozenie tmp_cords = tmp_organism.GetCords();

            //ORGANISM FROM TOP
            if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y - 2)) {
                up_organism = tmp_organism;
            }
            //ORGANISM FROM DOWN
            else if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y + 2)) {
                down_organism = tmp_organism;
            }
            //ORGANISM FROM LEFT
            else if ((tmp_cords.x == organism_cords.x - 2) && (tmp_cords.y == organism_cords.y)) {
                left_organism = tmp_organism;
            }
            //ORGANISM FROM right
            else if ((tmp_cords.x == organism_cords.x + 2) && (tmp_cords.y == organism_cords.y)) {
                right_organism = tmp_organism;
            }

            tmp_organism=tmp_organism.next;
        }

        //DRAWING RANDOM PLACE FROM EMPTY ONES
        //l-LEFT r-RIGHT u-UP d-DOWN n-NOPE
        char[] randomise = {'n', 'n', 'n', 'n'};

        int index = 0;

        //FROM LEFT SIDE
        if ((left_organism == null) && (organism_cords.x > 2)) {
            randomise[index] = 'l';
            index++;
        }

        //FROM RIGHT SIDE
        if ((right_organism == null) && (organism_cords.x < world.GetWidth() - 1)) {
            randomise[index] = 'r';
            index++;
        }

        //FROM UP
        if ((up_organism == null) && (organism_cords.y > 2)) {
            randomise[index] = 'u';
            index++;
        }

        //FROM DOWN
        if ((down_organism == null) && (organism_cords.y < world.GetHeight() - 1)) {
            randomise[index] = 'd';
            index++;
        }

        if (index != 0) {
            Random random = new Random();
            int number = random.nextInt(index);

            if (randomise[number] == 'l') {
                return Stale.LEFT;
            } else if (randomise[number] == 'r') {
                return Stale.RIGHT;
            } else if (randomise[number] == 'u') {
                return Stale.UP;
            } else if (randomise[number] == 'd') {
                return Stale.DOWN;
            }
        }

        return -1;

    }


}
