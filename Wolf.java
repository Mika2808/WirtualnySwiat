import javax.swing.*;

public class Wolf extends Animal{

    Wolf(World world1, int x, int y) {
        SetStrength(4);
        SetInitiative(4);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {
        return "WOLF";
    }

    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/wolf.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
}
