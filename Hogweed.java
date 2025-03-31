import javax.swing.*;

public class Hogweed extends Plant{
    Hogweed(World world1, int x, int y){
        SetStrength(4);
        SetInitiative(0);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public void Action(){
        Polozenie organism_cords = GetCords();
        Organism tmp_organism = world.GetOrganisms().GetHead();
        while(tmp_organism!=null){
            //CHECKING IF ORGANISM IS ANIMAL
            if(tmp_organism.GetInitiative()!=0) {
                Polozenie tmp_cords = tmp_organism.GetCords();
                //ORGANISM FROM TOP
                if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y - 1)) {
                    if(!tmp_organism.getName().equals("CYBER-SHEEP")) {
                        world.GetOrganisms().remove(tmp_organism, this);
                    }
                }
                //ORGANISM FROM DOWN
                else if ((tmp_cords.x == organism_cords.x) && (tmp_cords.y == organism_cords.y + 1)) {
                    if(!tmp_organism.getName().equals("CYBER-SHEEP")) {
                        world.GetOrganisms().remove(tmp_organism, this);
                    }
                }
                //ORGANISM FROM LEFT
                else if ((tmp_cords.x == organism_cords.x - 1) && (tmp_cords.y == organism_cords.y)) {
                    if(!tmp_organism.getName().equals("CYBER-SHEEP")) {
                        world.GetOrganisms().remove(tmp_organism, this);
                    }
                }
                //ORGANISM FROM RIGHT
                else if ((tmp_cords.x == organism_cords.x + 1) && (tmp_cords.y == organism_cords.y)) {
                    if(!tmp_organism.getName().equals("CYBER-SHEEP")) {
                        world.GetOrganisms().remove(tmp_organism, this);
                    }
                }
            }
            tmp_organism=tmp_organism.next;
        }
        //EMPTY PLACE FOR BREADING
        int place = EmptyPLace();

        //IF THERE IS ANY EMPTY PLACE
        if(place != -1){
            if(place==Stale.LEFT){
                New(getName(), world, organism_cords.x - 1, organism_cords.y);
            }
            else if(place==Stale.RIGHT){
                New(getName(), world, organism_cords.x + 1, organism_cords.y);
            }
            else if(place==Stale.UP){
                New(getName(), world, organism_cords.x, organism_cords.y - 1);
            }
            else if(place==Stale.DOWN){
                New(getName(), world, organism_cords.x, organism_cords.y + 1);
            }
        }
    }
    @Override
    public String getName() {
        return "HOGWEED";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/posion.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Collision(Organism attacked){
        world.GetOrganisms().remove(this,attacked);
        if(!attacked.getName().equals("CYBER-SHEEP")){
            world.GetOrganisms().remove(attacked,this);
        }
    }
}
