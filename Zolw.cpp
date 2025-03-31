#include "Zolw.h"
#include "Swiat.h"

Zolw::Zolw() {};

Zolw::Zolw(Swiat* ref, int x, int y) {
	this->SetSila(2);
	this->SetInicjatywa(1);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

std::string Zolw::getName(){
	return "TURTLE";
}

void Zolw::Akcja() {
	int losowanie75 = rand() % 4;
	Polozenie tmp_cords = this->GetCords();
	bool ruch = false;
	int liczba;
	if (losowanie75 == 0) {
		while (ruch == false) {
			liczba = rand() % 4;
			if (liczba == 0) { //GORA
				if (tmp_cords.y > 1) {
					this->SetCords(tmp_cords.x, tmp_cords.y - 1);
					this->SetLastMove(GORA);
					ruch = true;
				}
				else continue;
			}
			else if (liczba == 1) {//DOL
				if (tmp_cords.y < this->referencja->GetWysokosc()) {
					this->SetCords(tmp_cords.x, tmp_cords.y + 1);
					this->SetLastMove(DOL);
					ruch = true;
				}
				else continue;
			}
			else if (liczba == 2) {//PRAWO 
				if (tmp_cords.x < this->referencja->GetSzerokosc()) {
					this->SetCords(tmp_cords.x + 1, tmp_cords.y);
					this->SetLastMove(PRAWO);
					ruch = true;
				}
				else continue;
			}
			else if (liczba == 3) { //LEWO 
				if (tmp_cords.x > 1) {
					this->SetCords(tmp_cords.x - 1, tmp_cords.y);
					this->SetLastMove(LEWO);
					ruch = true;
				}
				else continue;
			}
			else {
				this->SetLastMove(0);
				ruch = true;
			}
		}
	}	
};

void Zolw::Kolizja(Organizm* atakowany) {

	//¯Ó£W TRAFIA NA ¯Ó£WIA
	if (this->getName() == atakowany->getName()) {
		this->CofnijRuch();
		int nowy = atakowany->WolneMiejsca();
		if (nowy != -1) {

			Polozenie tmp_cords = atakowany->GetCords();

			if (nowy == LEWO) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x - 1, tmp_cords.y);
			}
			else if (nowy == PRAWO) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x + 1, tmp_cords.y);
			}
			else if (nowy == GORA) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x, tmp_cords.y - 1);
			}
			else if (nowy == GORA) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x, tmp_cords.y + 1);
			}
		}
		atakowany->SetRuch(true);
	}
	
	//¯Ó£W TRAFIA NA ROŒLINÊ
	else if (atakowany->GetInicjatywa() == 0) {
		atakowany->Kolizja(this);
	}
	else {

		//¯Ó£W JEST ATAKOWANY
		if (this->GetRuch()) {
			if (atakowany->GetSila() < 5) {
				atakowany->CofnijRuch();
			}
			else {
				atakowany->referencja->list.usun(this,atakowany);
			}
		}

		//¯Ó£W ATAKUJE
		else {
			if (this->GetSila() > atakowany->GetSila()) {
				this->referencja->list.usun(atakowany,this);
			}
			else if (this->GetSila() < atakowany->GetSila()) {
				atakowany->referencja->list.usun(this,atakowany);
			}
			else {
				this->referencja->list.usun(atakowany,this);
			}
		}
	}
};

void Zolw::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y);
	textcolor(RED);
	putch(ANIMAL_SIGN);
};

Zolw::~Zolw() {};
