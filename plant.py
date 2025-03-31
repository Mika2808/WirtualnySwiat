from organism import Organism
from Stale import Stale

class Plant(Organism):
    def Collision(self, attacked):
        self.world.GetOrganisms().remove(self, attacked)
        # !!! NOTIFICATIONS WHEN ANIMAL IS DELETED

    def Action(self):
        # EMPTY PLACE FOR BREEDING
        place = self.EmptyPlace()
        tmp_cords = self.GetCords()

        # IF THERE IS ANY EMPTY PLACE
        if place != -1:
            if place == Stale.LEFT:
                self.New(self.getName(), self.world, tmp_cords.x - 1, tmp_cords.y)
            elif place == Stale.RIGHT:
                self.New(self.getName(), self.world, tmp_cords.x + 1, tmp_cords.y)
            elif place == Stale.UP:
                self.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y - 1)
            elif place == Stale.DOWN:
                self.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y + 1)

    def Drawing(self):
        pass

    def getName(self):
        return ""
