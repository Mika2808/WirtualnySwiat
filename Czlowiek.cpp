#include "Czlowiek.h"
#include "Swiat.h"

Czlowiek::Czlowiek() {};

Czlowiek::Czlowiek(Swiat* ref, int x, int y) {
	this->SetSila(4);
	this->SetInicjatywa(4);
	this->SetCords(x, y);
	this->referencja = ref;
	this->referencja->SetUmiejetnosc(false);
	this->referencja->SetRundy(10);
	this->SetRuch(true);
}

std::string Czlowiek::getName() {
	return "HUMAN";
}

void Czlowiek::Akcja() {
	bool ruch = false;
	
	//POBIERANIE DANYCH 
	Polozenie tmp_cords = this->GetCords();
	int wysokosc = this->referencja->GetWysokosc();
	int szerokosc = this->referencja->GetSzerokosc();
	int move = this->referencja->GetNextMove();

	while (ruch == false) {
		//RUSZANIE CZ£OWIEKA W GÓRÊ
		if (move == GORA) {
			this->SetCords(tmp_cords.x, tmp_cords.y - 1);
			this->SetLastMove(GORA);
			ruch = true;
		}
		//RUSZANIE CZ£OWIEKA W DÓ£
		else if (move == DOL) {
			this->SetCords(tmp_cords.x, tmp_cords.y + 1);
			this->SetLastMove(DOL);
			ruch = true;
		}
		//RUSZANIE CZ£OWIEKA W LEWO
		else if (move == LEWO) {
			this->SetCords(tmp_cords.x - 1, tmp_cords.y);
			this->SetLastMove(LEWO);
			ruch = true;
		}
		//RUSZANIE CZ£OWIEKA W PRAWO
		else if (move == PRAWO) {
			this->SetCords(tmp_cords.x + 1, tmp_cords.y);
			this->SetLastMove(PRAWO);
			ruch = true;
		}
		//AKTYWOWANIE NIEŒMIERTELNOŒCI
		else if ((move == WARZYWA)&&(this->referencja->GetRundy() >= 10)) {
			this->referencja->SetRundy(0);
			this->referencja->SetUmiejetnosc(true);
			ruch = true;
		}
		else {
			this->SetLastMove(0);
			ruch = true;
		}
		
	}
	if(this->referencja->GetRundy() == 5)this->referencja->SetUmiejetnosc(false);
};

void Czlowiek::Kolizja(Organizm* atakowany) {
	
	//SPRAWDZENIE CZY CZ£OWIEK JEST NIEŒMIERTLENY
	if (this->referencja->GetUmiejetnosc() == true)
	{
		int space = this->WolneMiejsca();
		
		//JE¯ELI NIE MA ¯ADNEGO WOLNNEGO MIEJSCA
		if(space == -1)
		this->referencja->list.usun(atakowany, this);
		
		//JE¯ELI JEST WOLNE MIEJSCE
		else {
			Polozenie tmp_cords = this->GetCords();

			if (space == GORA) {
				this->SetCords(tmp_cords.x, tmp_cords.y - 1);
				this->SetLastMove(GORA);
			}	
			else if (space == DOL) {
				this->SetCords(tmp_cords.x, tmp_cords.y + 1);
				this->SetLastMove(DOL);
				
			}
			else if (space == LEWO) {
				this->SetCords(tmp_cords.x - 1, tmp_cords.y);
				this->SetLastMove(LEWO);
				
			}		
			else if (space == PRAWO) {
				this->SetCords(tmp_cords.x + 1, tmp_cords.y);
				this->SetLastMove(PRAWO);
			}
		}
	}

	//JE¯ELI CZ£OWIEK JEST ŒMIERTELNY
	else {
		
		//ATAKOWANY TO NIE JEST ROŒLINA
		if (atakowany->GetInicjatywa()) {
			if (this->GetSila() > atakowany->GetSila()) {
				this->referencja->list.usun(atakowany,this);
			}
			else if (this->GetSila() < atakowany->GetSila()) {
				atakowany->referencja->list.usun(this,atakowany);
			}
			else {
				this->referencja->list.usun(atakowany, this);
			}
		}

		//ATAKOWANA JEST ROŒLINA
		else atakowany->Kolizja(this);
	}
};

void Czlowiek::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y);
	textcolor(WHITE);
	putch(ANIMAL_SIGN);
};

Czlowiek::~Czlowiek() {};
