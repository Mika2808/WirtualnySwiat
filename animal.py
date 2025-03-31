import random
from organism import Organism
from Stale import Stale


class Animal(Organism):
    def Action(self):
        tmp_cords = self.GetCords()
        move = False

        while not move:
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
                if tmp_cords.y < self.world.height:
                    self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                    self.SetLastMove(Stale.DOWN)
                    move = True
                else:
                    continue
            # RIGHT
            elif number == 2:
                if tmp_cords.x < self.world.length:
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

    def Collision(self, attacked):
        # SAME ANIMAL
        if self.getName() == attacked.getName():
            self.UndoMove()
            child = attacked.EmptyPLace()
            if child != -1:
                tmp_cords = attacked.GetCords()

                if child == Stale.LEFT:
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x - 1, tmp_cords.y)
                elif child == Stale.RIGHT:
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x + 1, tmp_cords.y)
                elif child == Stale.UP:
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x, tmp_cords.y - 1)
                elif child == Stale.DOWN:
                    attacked.New(attacked.getName(), attacked.world, tmp_cords.x, tmp_cords.y + 1)
            
            attacked.SetMove(True)
        # IF ANIMAL IS ANTELOPE OR TURTLE
        elif attacked.getName() == "ANTELOPE" or attacked.getName() == "TURTLE" or attacked.getName() == "HUMAN":
            attacked.Collision(self)
        else:
            # IF ATTACKED ORGANISM IS NOT A PLANT
            if attacked.GetInitiative() != 0:
                if self.GetStrength() > attacked.GetStrength():
                    self.world.GetOrganisms().remove(attacked, self)
                elif self.GetStrength() < attacked.GetStrength():
                    self.world.GetOrganisms().remove(self, attacked)
                else:
                    self.world.GetOrganisms().remove(attacked, self)
            # IF ATTACKED ORGANISM IS A PLANT
            else:
                attacked.Collision(self)

    def Drawing(self):
        pass

    def getName(self):
        return ""
