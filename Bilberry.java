import javax.swing.*;

public class Bilberry extends Plant{
    Bilberry(World world1, int x, int y) {
        SetStrength(0);
        SetInitiative(0);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {
        return "BILBERRY";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/bilberry.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Collision(Organism attacked) {
        world.GetOrganisms().remove(attacked,this);
        world.GetOrganisms().remove(this,attacked);
        //!!!NOTIFICATIONS WHEN ANIMAL IS DELETED
    }
}
