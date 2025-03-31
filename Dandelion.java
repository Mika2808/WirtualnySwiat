import javax.swing.*;

public class Dandelion extends Plant{

    Dandelion(World world1, int x, int y) {
        SetStrength(0);
        SetInitiative(0);
        SetCords(x, y);
        SetMove(true);
        world = world1;
    }
    @Override
    public String getName() {
        return "DANDELION";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/dandelion.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Action() {
        for (int i = 0; i < 3; i++) {
            //EMPTY PLACE
            int child = EmptyPLace();
            Polozenie tmp_cords = GetCords();
            if (child != -1) {

                if (child == Stale.LEFT) {
                        New(getName(), world, tmp_cords.x - 1, tmp_cords.y);
                }
                else if (child == Stale.RIGHT) {
                    New(getName(), world, tmp_cords.x + 1, tmp_cords.y);
                }
                else if (child == Stale.UP) {
                    New(getName(), world, tmp_cords.x, tmp_cords.y - 1);
                }
                else if (child == Stale.DOWN) {
                    New(getName(), world, tmp_cords.x, tmp_cords.y + 1);
                }
            }
        }
    }
}
