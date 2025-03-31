from Stale import Stale
from animal import Animal

class Human(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(5)
        self.SetInitiative(4)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "HUMAN"

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='#A52A2A')

    def Collision(self, attacked):
        # CHECKING SUPERPOWER
        if self.world.GetSuperpower():
            space = self.EmptyPlace()
            # IF THERE IS NO EMPTY PLACE
            if space == -1:
                self.world.GetOrganisms().remove(attacked, self)
            # IF THERE IS AN EMPTY PLACE
            else:
                tmp_cords = self.GetCords()
                if space == Stale.UP:
                    self.SetCords(tmp_cords.x, tmp_cords.y - 1)
                    self.SetLastMove(Stale.UP)
                elif space == Stale.DOWN:
                    self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                    self.SetLastMove(Stale.DOWN)
                elif space == Stale.LEFT:
                    self.SetCords(tmp_cords.x - 1, tmp_cords.y)
                    self.SetLastMove(Stale.LEFT)
                elif space == Stale.RIGHT:
                    self.SetCords(tmp_cords.x + 1, tmp_cords.y)
                    self.SetLastMove(Stale.RIGHT)

        # IF SUPERPOWER IS OFF
        else:
            # ATTACKED IS NOT A PLANT
            if attacked.GetInitiative() != 0:
                if self.GetStrength() > attacked.GetStrength():
                    self.world.GetOrganisms().remove(attacked, self)
                elif self.GetStrength() < attacked.GetStrength():
                    self.world.GetOrganisms().remove(self, attacked)
                else:
                    self.world.GetOrganisms().remove(attacked, self)
            # ATTACKED IS A PLANT
            else:
                attacked.Collision(self)

    def Action(self):
        bool_move = False

        # GETTING DATA
        tmp_cords = self.GetCords()
        move = self.world.GetNextMove()

        while not bool_move:
            if move == Stale.UP:
                if tmp_cords.y > 1:
                    self.SetCords(tmp_cords.x, tmp_cords.y - 1)
                    self.SetLastMove(Stale.UP)
                bool_move = True
            elif move == Stale.DOWN:
                if tmp_cords.y < self.world.height - 1:
                    self.SetCords(tmp_cords.x, tmp_cords.y + 1)
                    self.SetLastMove(Stale.DOWN)
                bool_move = True
            elif move == Stale.LEFT:
                if tmp_cords.x > 1:
                    self.SetCords(tmp_cords.x - 1, tmp_cords.y)
                    self.SetLastMove(Stale.LEFT)
                bool_move = True
            elif move == Stale.RIGHT:
                if tmp_cords.x < self.world.length - 1:
                    self.SetCords(tmp_cords.x + 1, tmp_cords.y)
                    self.SetLastMove(Stale.RIGHT)
                bool_move = True
            # SUPERPOWER ON
            elif move == Stale.VEGETABLE:
                self.world.SetRounds(0)
                self.world.SetSuperpower(True)
                bool_move = True
            else:
                self.SetLastMove(0)
                bool_move = True

        if self.world.GetRounds() == 5:
            self.world.SetSuperpower(False)
            self.world.TurnOffSuperpower()
