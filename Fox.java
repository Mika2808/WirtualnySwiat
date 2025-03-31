import javax.swing.*;
import java.util.Random;

public class Fox extends Animal{

    Fox(World world1, int x, int y) {
        SetStrength(3);
        SetInitiative(7);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName(){
        return "FOX";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/fox.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Action() {

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
            if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y - 1)) {
                up_organism = tmp_organism;
            }
            //ORGANISM FROM DOWN
            else if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y + 1)) {
                down_organism = tmp_organism;
            }
            //ORGANISM FROM LEFT
            else if ((tmp_cords.x == organism_cords.x - 1) && (tmp_cords.y == organism_cords.y)) {
                left_organism = tmp_organism;
            }
            //ORGANISM FROM right
            else if ((tmp_cords.x == organism_cords.x + 1) && (tmp_cords.y == organism_cords.y)) {
                right_organism = tmp_organism;
            }
            tmp_organism=tmp_organism.next;
        }


        //EMPTY PLACE OR WEAKER ANIMAL
        //l-LEFT r-RIGHT u-UP d-DOWN n-NAH
        char[] randomise = {'n', 'n', 'n', 'n'};
        int index = 0;
        int tmp_sila = GetStrength();

        //FROM LEFT
        if (((left_organism == null) || (left_organism.GetStrength() <= tmp_sila))
                && (organism_cords.x > 1)) {
            randomise[index] = 'l';
            index++;
        }

        //FROM RIGHT
        if (((right_organism == null) || (right_organism.GetStrength() <= tmp_sila))
                && (organism_cords.x < world.GetWidth())) {
            randomise[index] = 'r';
            index++;
        }

        //UP
        if (((up_organism == null) || (up_organism.GetStrength() <= tmp_sila))
                && (organism_cords.y > 1)) {
            randomise[index] = 'u';
            index++;
        }

        //DOWN
        if (((down_organism == null) || (down_organism.GetStrength() <= tmp_sila))
                && organism_cords.y < world.GetHeight()) {
            randomise[index] = 'd';
            index++;
        }


        if (index != 0) {
            Random random = new Random();
            int number = random.nextInt(index);

            if (randomise[number] == 'l') {
                SetCords(organism_cords.x - 1, organism_cords.y);
                SetLastMove(Stale.LEFT);
            }
            else if (randomise[number] == 'r') {
                SetCords(organism_cords.x + 1, organism_cords.y);
                SetLastMove(Stale.RIGHT);
            }
            else if (randomise[number] == 'u') {
                SetCords(organism_cords.x, organism_cords.y - 1);
                SetLastMove(Stale.UP);
            }
            else if(randomise[number] == 'd'){
                SetCords(organism_cords.x, organism_cords.y + 1);
                SetLastMove(Stale.DOWN);
            }
            else {
                SetLastMove(0);
            }
        }
    }
}
