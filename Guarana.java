import javax.swing.*;

public class Guarana extends Plant{

    Guarana(World world1, int x, int y) {
        SetStrength(0);
        SetInitiative(0);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {
        return "GUARANA";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/guarana.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Collision(Organism attacked) {
        attacked.SetStrength(attacked.GetStrength() + 3);
        world.GetOrganisms().remove(this,attacked);
        //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
    }
}
