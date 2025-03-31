import random
from animal import Animal
from Stale import Stale
from Polozenie import Polozenie


class Antelope(Animal):
    def __init__(self, world1, x, y):
        super().__init__()
        self.SetStrength(4)
        self.SetInitiative(4)
        self.SetCords(x, y)
        self.SetMove(True)
        self.world = world1

    def getName(self):
        return "ANTELOPE"

    def Action(self):
        tmp_cords = self.GetCords()
        move = False

        while not move:
            number = random.randint(0, 3)

            # UP
            if number == 0:
                if tmp_cords.y > 2:
                    self.SetCords(tmp_cords.x, tmp_cords.y - 2)
                    self.SetLastMove(Stale.UP)
                    move = True

            # DOWN
            elif number == 1:
                if tmp_cords.y < self.world.height - 1:
                    self.SetCords(tmp_cords.x, tmp_cords.y + 2)
                    self.SetLastMove(Stale.DOWN)
                    move = True

            # RIGHT
            elif number == 2:
                if tmp_cords.x < self.world.length - 1:
                    self.SetCords(tmp_cords.x + 2, tmp_cords.y)
                    self.SetLastMove(Stale.RIGHT)
                    move = True

            # LEFT
            else:
                if tmp_cords.x > 2:
                    self.SetCords(tmp_cords.x - 2, tmp_cords.y)
                    self.SetLastMove(Stale.LEFT)
                    move = True

    def Collision(self, attacked):
        # ANTELOPE ATTACK ANTELOPE
        if self.getName() == attacked.getName():
            self.UndoMove()
            child = attacked.EmptyPlace()
            if child != -1:
                tmp_cords = attacked.GetCords()

                if child == Stale.LEFT:
                    attacked.New(self.getName(), self.world, tmp_cords.x - 1, tmp_cords.y)
                elif child == Stale.RIGHT:
                    attacked.New(self.getName(), self.world, tmp_cords.x + 1, tmp_cords.y)
                elif child == Stale.UP:
                    attacked.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y - 1)
                elif child == Stale.DOWN:
                    attacked.New(self.getName(), self.world, tmp_cords.x, tmp_cords.y + 1)

            attacked.SetMove(True)
        else:
            # LOOKING FOR EMPTY SPACE
            place = self.EmptyPLace()
            number = random.randint(0, 1)

            # ANTELOPE DOESN'T RUN AWAY OR THERE IS NO EMPTY PLACE
            if number == 0 or place == -1:
                # ANTELOPE IS STRONGER
                if attacked.GetStrength() < self.GetStrength():
                    self.world.GetOrganisms().remove(attacked, self)

                # ATTACKED ORGANISM IS STRONGER
                elif attacked.GetStrength() > self.GetStrength():
                    self.world.GetOrganisms().remove(self, attacked)

                # STRENGTH IS EQUAL
                else:
                    # ANTELOPE IS ATTACKED
                    if self.GetMove():
                        self.world.GetOrganisms().remove(self, attacked)
                    # ORGANISM IS ATTACKED BY ANTELOPE
                    else:
                        self.world.GetOrganisms().remove(attacked, self)
            else:
                if place == Stale.LEFT:
                    self.SetCords(self.GetCords().x - 2, self.GetCords().y)
                elif place == Stale.RIGHT:
                    self.SetCords(self.GetCords().x + 2, self.GetCords().y)
                elif place == Stale.UP:
                    self.SetCords(self.GetCords().x, self.GetCords().y - 2)
                elif place == Stale.DOWN:
                    self.SetCords(self.GetCords().x, self.GetCords().y + 2)

    def Drawing(self):
        self.world.buttons[self.GetCords().y -1][self.GetCords().x -1].configure(bg='red')
    
    def New(self, text, reference, x, y):
        self.world.GetOrganisms().add(Antelope(reference, x, y))    

    def EmptyPLace(self):
        # AROUND ORGANISMS
        up_organism = None
        down_organism = None
        left_organism = None
        right_organism = None
        organism_cords = self.GetCords()

        tmp_organism = self.world.GetOrganisms().GetHead()
        while tmp_organism is not None:
            tmp_cords = tmp_organism.GetCords()

            # ORGANISM FROM TOP
            if tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y - 2:
                up_organism = tmp_organism

            # ORGANISM FROM DOWN
            elif tmp_cords.x == organism_cords.x and tmp_cords.y == organism_cords.y + 2:
                down_organism = tmp_organism

            # ORGANISM FROM LEFT
            elif tmp_cords.x == organism_cords.x - 2 and tmp_cords.y == organism_cords.y:
                left_organism = tmp_organism

            # ORGANISM FROM RIGHT
            elif tmp_cords.x == organism_cords.x + 2 and tmp_cords.y == organism_cords.y:
                right_organism = tmp_organism

            tmp_organism = tmp_organism.next

        # DRAWING RANDOM PLACE FROM EMPTY ONES
        # l-LEFT r-RIGHT u-UP d-DOWN n-NOPE
        randomise = ['n', 'n', 'n', 'n']
        index = 0

        # FROM LEFT SIDE
        if left_organism is None and organism_cords.x > 2:
            randomise[index] = 'l'
            index += 1

        # FROM RIGHT SIDE
        if right_organism is None and organism_cords.x < self.world.GetWidth() - 1:
            randomise[index] = 'r'
            index += 1

        # FROM UP
        if up_organism is None and organism_cords.y > 2:
            randomise[index] = 'u'
            index += 1

        # FROM DOWN
        if down_organism is None and organism_cords.y < self.world.GetHeight() - 1:
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
    
