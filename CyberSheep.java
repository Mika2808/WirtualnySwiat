import javax.swing.*;
import java.util.Random;

public class CyberSheep extends Animal{
    CyberSheep(World world1, int x, int y) {
        SetStrength(11);
        SetInitiative(4);
        SetCords(x, y);
        world = world1;
    }

    @Override
    public String getName() {
        return "CYBER-SHEEP";
    }
    @Override
    public void Drawing() {
        ImageIcon icon = new ImageIcon("src/Icons/cyber.png");
        world.GetButton(GetCords()).setIcon(icon);
    }
    @Override
    public void Action(){
        //GETTING DATA
        Polozenie tmp_cords = GetCords();
        Polozenie hogweed_coords= world.GetOrganisms().FindHogweed(tmp_cords);


        boolean move = false;

        while (!move) {
            if(hogweed_coords.x!=0){
                if (hogweed_coords.y < tmp_cords.y) {
                    SetCords(tmp_cords.x, tmp_cords.y - 1);
                    SetLastMove(Stale.UP);
                    move = true;
                }
                else if (hogweed_coords.y > tmp_cords.y) {
                    SetCords(tmp_cords.x, tmp_cords.y + 1);
                    SetLastMove(Stale.DOWN);
                    move = true;
                }
                else if (hogweed_coords.x < tmp_cords.x) {
                    SetCords(tmp_cords.x - 1, tmp_cords.y);
                    SetLastMove(Stale.LEFT);
                    move = true;
                }
                else if (hogweed_coords.x > tmp_cords.x) {
                    SetCords(tmp_cords.x + 1, tmp_cords.y);
                    SetLastMove(Stale.RIGHT);
                    move = true;
                }
            }
            else{
                Random random = new Random();
                int number = random.nextInt(4);

                //UP
                if (number == 0) {
                    if (tmp_cords.y > 1) {
                        SetCords(tmp_cords.x, tmp_cords.y - 1);
                        SetLastMove(Stale.UP);
                        move = true;
                    }
                    else continue;
                }
                //DOWN
                else if (number == 1) {
                    if (tmp_cords.y < world.GetHeight()) {
                        SetCords(tmp_cords.x, tmp_cords.y + 1);
                        SetLastMove(Stale.DOWN);
                        move = true;
                    }
                    else continue;
                }
                //RIGHT
                else if (number == 2) {
                    if (tmp_cords.x < world.GetWidth()) {
                        SetCords(tmp_cords.x + 1,tmp_cords.y);
                        SetLastMove(Stale.RIGHT);
                        move = true;
                    }
                    else continue;
                }
                //LEFT
                else {
                    if (tmp_cords.x > 1) {
                        SetCords(tmp_cords.x - 1, tmp_cords.y);
                        SetLastMove(Stale.LEFT);
                        move = true;
                    }
                    else continue;
                }
            }
        }
    }
}
