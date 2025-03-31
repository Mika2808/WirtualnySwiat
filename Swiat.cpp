#include<stdio.h>
#include<malloc.h>
#include<string.h>
#include<windows.h>
#include<fstream>

#include "Swiat.h"
#include"conio2.h"

//STRZA£KI DO PORUSZANIA SIÊ 
#define UP 0x48                 
#define DOWN 0x50
#define LEFT 0x4b
#define RIGHT 0x4d


#define PLANSZA 43
#define ENTER 13

Swiat::Swiat() {
	SetUmiejetnosc(false);
	gotoxy(1, 1);
	cputs("Podaj wysokosc planszy: ");
	char znak;
	char wymiary [3];
	int index = 0;
	while ((znak = (char)getch()) !=ENTER)
	{
		if (isdigit(znak)) {
			putch(znak);
			wymiary[index] = znak;
			index++;
		}
	}

	this->SetWysokosc(atoi(wymiary));

	for (int i = 0; i < 3; i++) {
		wymiary[i] = NULL;
	}
	gotoxy(1, 2);
	cputs("Podaj szerokosc planszy: ");
	znak = NULL;
	index = 0;
	while ((znak = (char)getch()) != ENTER)
	{
		if (isdigit(znak)) {
			putch(znak);
			wymiary[index] = znak;
			index++;
		}
	}

	this->SetSzerokosc(atoi(wymiary));
	clrscr();

	legenda();
	this->list.Start(this);
	this->swiat = true;

	rysujSwiat();
}

Swiat::~Swiat() {}

void Swiat::rysujSwiat() {
	legenda();
	int boardx = 1, boardy = 1;
	textcolor(WHITE);
	gotoxy(boardx, boardy);
	
	//RYSOWANIE PLANSZY 
	for (int i = 0; i < this->wysokosc; i++){
		for (int j = 0; j < this->szerokosc; j++){
				putch(PLANSZA);
		}
		gotoxy(boardx, boardy + i + 1);
	}
	
	//RYSOWANIE ZWIERZ¥T
	list.Plansza();
	
}

void Swiat::wykonajTure(int znak) {
	//WYKONANIE RUCHU
	if (SetNextMove(znak)) {
		list.Ruch();
		this->SetRundy(this->rundy + 1);
	}	
}

void Swiat::legenda() {
	
	//LEGENDA WYPISUJ¥CA ZWIERZÊTA I ICH KOLORY
	gotoxy(this->szerokosc + 10, 1);
	textcolor(WHITE); cputs("ANIMALS:");

	gotoxy(this->szerokosc + 10, 2);
	putch(ANIMAL_SIGN); cputs("-HUMAN");

	gotoxy(this->szerokosc + 10, 3);
	textcolor(BLUE); putch(ANIMAL_SIGN); cputs("-WOLF");

	gotoxy(this->szerokosc + 10, 4);
	textcolor(GREEN); putch(ANIMAL_SIGN); cputs("-SHEEP");

	gotoxy(this->szerokosc + 10, 5);
	textcolor(CYAN); putch(ANIMAL_SIGN); cputs("-FOX");

	gotoxy(this->szerokosc + 10, 6);
	textcolor(RED); putch(ANIMAL_SIGN); cputs("-TURTLE");

	gotoxy(this->szerokosc + 10, 7);
	textcolor(MAGENTA); putch(ANIMAL_SIGN); cputs("-ANTELOPE");

	//LEGENDA WYPISUJ¥CA ROŒLINY I ICH KOLORY

	gotoxy(this->szerokosc + 10, 9);
	textcolor(WHITE); cputs("PLANTS:");

	gotoxy(this->szerokosc + 10, 10);
	textcolor(BROWN); putch(ANIMAL_SIGN); cputs("-GRASS");

	gotoxy(this->szerokosc + 10, 11);
	textcolor(YELLOW); putch(ANIMAL_SIGN); cputs("-DANDELION");

	gotoxy(this->szerokosc + 10, 12);
	textcolor(DARKGRAY); putch(ANIMAL_SIGN); cputs("-GUARANA");

	gotoxy(this->szerokosc + 10, 13);
	textcolor(LIGHTBLUE); putch(ANIMAL_SIGN); cputs("-WOLF BERRIES");

	gotoxy(this->szerokosc + 10, 14);
	textcolor(LIGHTGREEN); putch(ANIMAL_SIGN); cputs("-BARSZCZ SOSNOWSKIEGO");

	//PRZYCISKI
	textcolor(WHITE);

	gotoxy(this->szerokosc + 10, 16);
	cputs("q-EXIT");

	gotoxy(this->szerokosc + 10, 17);
	cputs("a-ABILITY");

	gotoxy(this->szerokosc + 10, 18);
	cputs("s-SAVE");

	gotoxy(this->szerokosc + 10, 19);
	cputs("l-LOAD");

}

void Swiat::Zapisz() {
	FILE* plik = nullptr;
	//OWTWIERANIE PLIKU
	if (fopen_s(&plik, "WirtualnySwiat.txt", "w") == 0) {
		
		fprintf(plik, "%d\n", this->wysokosc);
		fprintf(plik, "%d\n", this->szerokosc);
		fprintf(plik, "%d\n", this->superumiejetnosc);
		fprintf(plik, "%d\n", this->rundy);
		this->list.Zapisz(plik);
		fclose(plik);
	}
	//NIE UDA£O SIÊ OTWORZYÆ PLIKU
}

void Swiat::Wczytaj() {
	FILE* plik = nullptr;
	
	//OWTWIERANIE PLIKU
	if (fopen_s(&plik, "WirtualnySwiat.txt", "r") == 0) {
		
		//WCZYTYWANIE DANYCH
		fscanf_s(plik, "%d\n", &this->wysokosc);
		fscanf_s(plik, "%d\n", &this->szerokosc);
		fscanf_s(plik, "%d\n", &this->superumiejetnosc);
		fscanf_s(plik, "%d\n", &this->rundy);
		
		this->list.Wczytaj(plik);
		
		fclose(plik);
	}
	//NIE UDA£O SIÊ OTWORZYÆ PLIKU
}

void Swiat::SetKoniecSwiata()
{
	this->swiat = false;
}

void Swiat::SetWysokosc(int x) {
	this->wysokosc = x;
}

void Swiat::SetSzerokosc(int x) {
	this->szerokosc = x;
}

bool Swiat::SetNextMove(int znak) {
	Polozenie cords = this->list.Human();
	
	//SPRAWDZANIE CZY CZ£OWIEK ISTNIEJE
	if (cords.x != 0) {
		//USTAWIANIE RUCHU CZ£OWIEKA
		if ((znak == UP) && (cords.y > 1)) {
			this->NextMove = GORA;
			return true;
		}
		else if ((znak == DOWN) && (cords.y < this->GetWysokosc())) {
			this->NextMove = DOL;
			return true;
		}
		else if ((znak == RIGHT) && (cords.x < this->GetSzerokosc())) {
			this->NextMove = PRAWO;
			return true;
		}
		else if ((znak == LEFT) && (cords.x > 1)) {
			this->NextMove = LEWO;
			return true;
		}
		else if (znak == 'a') {
			this->NextMove = WARZYWA;
			return true;
		}
		else return false;
	}
	else return true;
	
}

bool Swiat::GetUmiejetnosc() {
	return this->superumiejetnosc;
}

int Swiat::GetRundy() {
	return this->rundy;
}

void Swiat::SetUmiejetnosc(bool x) {
	this->superumiejetnosc = x;
}

void Swiat::SetRundy(int x) {
	this->rundy = x;
}

int Swiat::GetNextMove() {
	return this->NextMove;
}

int Swiat::GetSzerokosc() {
	return this->szerokosc;
}

int Swiat::GetWysokosc() {
	return this->wysokosc;
}

int Swiat::KoniecSwiata() {
	if (this->swiat == true) return 1;
	else return 0;
}