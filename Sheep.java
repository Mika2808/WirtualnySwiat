import javax.swing.*;

public class Sheep extends  Animal{

    Sheep(World world1, int x, int y) {
        SetStrength(4);
        SetInitiative(4);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {
        return "SHEEP";
    }

    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/sheep.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
}
