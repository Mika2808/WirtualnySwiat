import javax.swing.*;
public class Grass extends Plant{
    Grass(World world1, int x, int y) {
        SetStrength(0);
        SetInitiative(0);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {

        return "GRASS";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/grass.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
}
