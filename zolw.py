from Stale import Stale
from animal import Animal
import random

class Turtle(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(2)
        self.SetInitiative(1)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "TURTLE"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#000000')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Turtle(reference, x, y))   

    def Action(self):
        number = random.randint(0, 3)
        tmp_cords = self.GetCords()
        move = False

        if number == 0:
            while not move:
                number = random.randint(0, 3)
                # UP
                if number == 0:
                    if tmp_cords.y > 1:
                        self.SetCords(tmp_cords.x, tmp_cords.y - 1)
                        self.SetLastMove(Stale.UP)
                        move = True
                # DOWN
                elif number == 1:
                    if tmp_cords.y < self.world.height:
                        self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                        self.SetLastMove(Stale.DOWN)
                        move = True
                # RIGHT
                elif number == 2:
                    if tmp_cords.x < self.world.length:
                        self.SetCords(tmp_cords.x + 1, tmp_cords.y)
                        self.SetLastMove(Stale.RIGHT)
                        move = True
                # LEFT
                elif number == 3:
                    if tmp_cords.x > 1:
                        self.SetCords(tmp_cords.x - 1, tmp_cords.y)
                        self.SetLastMove(Stale.LEFT)
                        move = True
                else:
                    self.SetLastMove(0)
                    move = True

    def Collision(self, attacked):
        # TURTLE ATTACK TURTLE
        if self.getName() == attacked.getName():
            self.UndoMove()
            child = attacked.EmptyPlace()

            if child != -1:
                tmp_cords = attacked.GetCords()

                if child == Stale.LEFT:
                    attacked.New(attacked.getName(), self.world, tmp_cords.x - 1, tmp_cords.y)
                elif child == Stale.RIGHT:
                    attacked.New(attacked.getName(), self.world, tmp_cords.x + 1, tmp_cords.y)
                elif child == Stale.UP:
                    attacked.New(attacked.getName(), self.world, tmp_cords.x, tmp_cords.y - 1)
                elif child == Stale.DOWN:
                    attacked.New(attacked.getName(), self.world, tmp_cords.x, tmp_cords.y + 1)

            attacked.SetMove(True)
        # ATTACKED IS A PLANT
        elif attacked.GetInitiative() == 0:
            attacked.Collision(self)
        else:
            # TURTLE IS ATTACKED
            if self.GetMove():
                if attacked.GetStrength() < 5:
                    attacked.UndoMove()
                else:
                    self.world.GetOrganisms().remove(self, attacked)
                    # !!!NOTIFICATIONS WHEN ANIMAL IS DELETED
            # TURTLE IS ATTACKING
            else:
                if self.GetStrength() >= attacked.GetStrength():
                    self.world.GetOrganisms().remove(attacked, self)
                    # !!!NOTIFICATIONS WHEN ANIMAL IS DELETED
                elif self.GetStrength() < attacked.GetStrength():
                    self.world.GetOrganisms().remove(self, attacked)
                    # !!!NOTIFICATIONS WHEN ANIMAL IS DELETED
