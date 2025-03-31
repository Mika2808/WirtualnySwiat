import tkinter as tk
from ListOfOrganism import ListOfOrganism
from Polozenie import Polozenie
import pickle
from Stale import Stale


class World:
    def __init__(self,x,y):
        self.buttons = []
        self.length = x  
        self.height = y  
        self.root = tk.Tk()
        self.rounds = 11
        self.super_power = 0
        self.text_label = tk.Label(self.root, text="")
        self.text = ""
        self.organisms = ListOfOrganism()
        self.create_world()
        self.button_super = tk.Button()
        self.NextMove = Stale.VEGETABLE

        
        
    def button_click(self,row,col):
        print(f"Kliknięto przycisk w rzędzie {row}, kolumnie {col}")
        
    
    def legend_draw(self):

        label = tk.Label(
                self.root,
                text="LEGENDA:",
                fg="black",
                width=20,
                height=2
        )
        
        label.place(x=1290, y=0)

        animals =["Antylopa", "Wilcze jagody", "Cyberowca","Mlecz", "Lis", "Trawa", "Guarana", "Barszcz Sosnowskiego", "Człowiek","Owca","Zolw", "Wilk"]
        colors = ["#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FFA500", "#800080", "#FFC0CB", "#808080", "#A52A2A", "#FFFFFF", "#AAB000", "#40E0D0"]


        for i, element in enumerate(animals):
            
            button = tk.Button(
                self.root,
                width=2,
                height=1,
                bg=colors[i]
            )    
            button.place(x=1275, y=35*i + 40)

            label = tk.Label(
                    self.root,
                    text=element,
                    fg="black",
                    width=16,
                    height=1
            )
            label.place(x=1310, y=35*i + 40)
        
    def create_world(self):        
        
        self.root.title("Mikołaj Kabała 193380")
        screen_width = self.root.winfo_screenwidth()
        screen_height = self.root.winfo_screenheight()
        self.root.geometry(f"{screen_width}x{screen_height}")
        
        # Tworzenie siatki planszy z przyciskami
        for i in range(self.height):
            row = []
            
            for j in range(self.length):
                button = tk.Button(
                self.root,
                width=2,
                height=1,
                bg='white',
                command=lambda row=i, col =j: self.button_click(row,col)
                )
                
                button.grid(row=i, column=j)
                row.append(button)  # Dodawanie przycisku do rzędu
            
            self.buttons.append(row)  # Dodawanie rzędu do listy przycisków        
        
        button = tk.Button(
                self.root,
                width=12,
                height=3,
                text="Wczytaj",
                command =lambda: self.wczytaj()
        )    
        button.place(x = 5, y = 550)
        
        button = tk.Button(
                self.root,
                width=12,
                height=3,
                text="Zapisz",
                command = lambda: self.zapisz()
        )    
        button.place(x = 130, y = 550)
        
        button = tk.Button(
                self.root,
                width=12,
                height=3,
                text="Wykonaj ture",
                command =lambda: self.wykonajTure()
        )    
        button.place(x = 255, y = 550)
        
        self.button_super = tk.Button(
                self.root,
                width=13,
                height=3,
                bg='#EB1404',
                text="Superumiejetnosc",
                command =lambda: self.superpower()
        )    
        self.button_super.place(x = 380, y = 550)
        
        self.organisms.Start(self)
        self.legend_draw()
        def move(event) :
            if event.keysym == "Up":
                self.NextMove = Stale.UP
            elif event.keysym == "Down":
                    self.NextMove = Stale.DOWN
            elif event.keysym == "Left":
                    self.NextMove = Stale.LEFT
            elif event.keysym == "Right":
                    self.NextMove = Stale.RIGHT
        self.root.bind('<Key>',move)
        self.root.mainloop()
    
    def wykonajTure(self):
        self.text=""            
        self.organisms.Move()
        self.clear_board()
        self.rounds += 1
        self.organisms.DrawOrganism()
        self.text_label.config(text=self.text)
        self.text_label.place(x=500, y=0)
    
    def clear_board(self):
        for i in range(self.height):
            for j in range(self.length):
                self.buttons[i][j].configure(bg='white')

        
    def superpower(self):
        a = Polozenie(self.organisms.Human().x,self.organisms.Human().y)
        if a.x != 0:
            if self.rounds > 10 :
                self.super_power = 1
                self.button_super.config(bg='#16EB2E')
                self.NextMove=Stale.VEGETABLE
                self.rounds=0
                self.wykonajTure()
    
    def GetSuperpower(self):
        return self.super_power
    
    def zapisz(self):
        with open('zapis.txt', 'wb') as file:
            pickle.dump(self.height, file)
            pickle.dump(self.length, file)
            if not self.super_power:
                pickle.dump(0, file)
            else: 
                pickle.dump(1, file)
            pickle.dump(self.rounds, file)
            self.organisms.SaveOrganisms(file)

    def wczytaj(self):
        with open('zapis.txt', 'rb') as file:
            self.height = pickle.load(file)
            self.length = pickle.load(file)    
            self.super_power = pickle.load(file)
            self.rounds = pickle.load(file)
            self.organisms.LoadOrganisms(file)
        self.clear_board()
        self.organisms.DrawOrganism()    


    def remove_root(root):
        for widget in root.winfo_children():
            widget.destroy()
        root=tk.Tk()    
    
    def GetWidth(self):
        return self.length
    
    def GetHeight(self):
        return self.height
    
    def GetOrganisms(self):
        return self.organisms
    def GetNextMove(self):
        return self.NextMove
    def SetSuperpower(self,false):
        self.super_power = false
    def TurnOffSuperpower(self):
        self.button_super.config(bg='#EB1404')
    def GetRounds(self):
        return self.rounds
        