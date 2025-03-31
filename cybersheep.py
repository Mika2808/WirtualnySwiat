import random
from animal import Animal
from Stale import Stale

class CyberSheep(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(11)
        self.SetInitiative(4)
        self.SetCords(x, y)
        self.world = world1

    def getName(self):
        return "CYBER-SHEEP"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#0000FF')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(CyberSheep(reference, x, y))    


    def Action(self):
        # GETTING DATA
        tmp_cords = self.GetCords()
        hogweed_coords = self.world.GetOrganisms().FindHogweed(tmp_cords)

        move = False

        while not move:
            if hogweed_coords.x != 0:
                if hogweed_coords.y < tmp_cords.y:
                    self.SetCords(tmp_cords.x, tmp_cords.y - 1)
                    self.SetLastMove(Stale.UP)
                    move = True
                elif hogweed_coords.y > tmp_cords.y:
                    self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                    self.SetLastMove(Stale.DOWN)
                    move = True
                elif hogweed_coords.x < tmp_cords.x:
                    self.SetCords(tmp_cords.x - 1, tmp_cords.y)
                    self.SetLastMove(Stale.LEFT)
                    move = True
                elif hogweed_coords.x > tmp_cords.x:
                    self.SetCords(tmp_cords.x + 1, tmp_cords.y)
                    self.SetLastMove(Stale.RIGHT)
                    move = True
            else:
                number = random.randint(0, 3)

                # UP
                if number == 0:
                    if tmp_cords.y > 1:
                        self.SetCords(tmp_cords.x, tmp_cords.y - 1)
                        self.SetLastMove(Stale.UP)
                        move = True
                    else:
                        continue
                # DOWN
                elif number == 1:
                    if tmp_cords.y < self.world.GetHeight():
                        self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                        self.SetLastMove(Stale.DOWN)
                        move = True
                    else:
                        continue
                # RIGHT
                elif number == 2:
                    if tmp_cords.x < self.world.GetWidth():
                        self.SetCords(tmp_cords.x + 1, tmp_cords.y)
                        self.SetLastMove(Stale.RIGHT)
                        move = True
                    else:
                        continue
                # LEFT
                else:
                    if tmp_cords.x > 1:
                        self.SetCords(tmp_cords.x - 1, tmp_cords.y)
                        self.SetLastMove(Stale.LEFT)
                        move = True
                    else:
                        continue
