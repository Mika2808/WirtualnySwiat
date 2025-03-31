import random
from Stale import Stale
from Polozenie import Polozenie


class Organism:
    def __init__(self):
        self.strength = 0
        self.lastMove = 0
        self.initiative = 0
        self.cords = Polozenie(0,0)
        self.name = ""
        self.move = False
        self.world = None
        self.prev = None
        self.next = None

    # FUNKCJE DO PRZESŁONIĘCIA
    def getName(self):
        pass

    def Action(self):
        pass

    def Collision(self, attacked):
        pass

    def Drawing(self):
        pass

    # SETTERY
    def SetCords(self, x, y):
        self.cords.x = x
        self.cords.y = y

    def SetStrength(self, x):
        self.strength = x

    def SetInitiative(self, x):
        self.initiative = x

    def SetMove(self, x):
        self.move = x

    def SetLastMove(self, x):
        self.lastMove = x

    # GETTERY
    def GetStrength(self):
        return self.strength

    def GetInitiative(self):
        return self.initiative

    def GetCords(self):
        return self.cords

    def GetMove(self):
        return self.move

    def GetLastMove(self):
        return self.lastMove

    def EmptyPlace(self):
        # ORGANIZMY WOKÓŁ
        up_organism = None
        down_organism = None
        left_organism = None
        right_organism = None
        organism_cords = self.GetCords()

        tmp_organism = self.world.GetOrganisms().GetHead()
        while tmp_organism is not None:
            tmp_cords = tmp_organism.GetCords()

            # ORGANIZM Z GÓRY
            if tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y - 1:
                up_organism = tmp_organism
            # ORGANIZM Z DOŁU
            elif tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y + 1:
                down_organism = tmp_organism
            # ORGANIZM Z LEWEJ
            elif tmp_cords.x == organism_cords.x - 1 and tmp_cords.y == organism_cords.y:
                left_organism = tmp_organism
            # ORGANIZM Z PRAWEJ
            elif tmp_cords.x == organism_cords.x + 1 and tmp_cords.y == organism_cords.y:
                right_organism = tmp_organism

            tmp_organism = tmp_organism.next

        # LOSOWE MIEJSCE Z PUSTYCH
        # l-LEFT r-RIGHT u-UP d-DOWN n-NOPE
        randomise = ['n', 'n', 'n', 'n']

        index = 0

        # Z LEWEJ STRONY
        if left_organism is None and organism_cords.x > 1:
            randomise[index] = 'l'
            index += 1

        # Z PRAWEJ STRONY
        if right_organism is None and organism_cords.x < self.world.GetWidth():
            randomise[index] = 'r'
            index += 1

        # Z GÓRY
        if up_organism is None and organism_cords.y > 1:
            randomise[index] = 'u'
            index += 1

        # Z DOŁU
        if down_organism is None and organism_cords.y < self.world.GetHeight():
            randomise[index] = 'd'
            index += 1

        if index != 0:
            number = random.randint(0, index - 1)

            if randomise[number] == 'l':
                return Stale.LEFT
            elif randomise[number] == 'r':
                return Stale.RIGHT
            elif randomise[number] == 'u':
                return Stale.UP
            elif randomise[number] == 'd':
                return Stale.DOWN

        return -1
    
    def UndoMove(self):
        tmp_last_move = self.GetLastMove()
        tmp_cords = self.GetCords()

        if tmp_last_move == Stale.LEFT:
            self.SetCords(tmp_cords.x + 1, tmp_cords.y)
        elif tmp_last_move == Stale.RIGHT:
            self.SetCords(tmp_cords.x - 1, tmp_cords.y)
        elif tmp_last_move == Stale.UP:
            self.SetCords(tmp_cords.x, tmp_cords.y + 1)
        elif tmp_last_move == Stale.DOWN:
            self.SetCords(tmp_cords.x, tmp_cords.y - 1)

        self.SetLastMove(0)
