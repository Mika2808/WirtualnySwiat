#include "Zwierze.h"
#include "Swiat.h"
#include <cstdlib>

Zwierze::Zwierze() {};

void Zwierze::Akcja(){
	Polozenie tmpcords = this->GetCords();
	bool ruch = false;
	int liczba = 0;

	while (ruch == false) {
		liczba = rand() % 4;

		if (liczba == 0) { //GORA
			if (tmpcords.y > 1) {
				this->SetCords(tmpcords.x, tmpcords.y - 1);
				this->SetLastMove(GORA);
				ruch = true;
			}
			else continue;
		}
		else if (liczba == 1) {//DOL
			if (tmpcords.y < this->referencja->GetWysokosc()) {
				this->SetCords(tmpcords.x, tmpcords.y + 1);
				this->SetLastMove(DOL);
				ruch = true;
			}
			else continue;
		}
		else if (liczba == 2) {//PRAWO 
			if (tmpcords.x < this->referencja->GetSzerokosc()) {
				this->SetCords(tmpcords.x + 1, tmpcords.y);
				this->SetLastMove(PRAWO);
				ruch = true;
			}
			else continue;
		}
		else { //LEWO 
			if (tmpcords.x > 1) {
				this->SetCords(tmpcords.x - 1, tmpcords.y);
				this->SetLastMove(LEWO);
				ruch = true;
			}
			else continue;
		}
	}
}

void Zwierze::Kolizja(Organizm* atakowany){
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
	else if (atakowany->getName() == "ANTELOPE" || atakowany->getName() == "TURTLE"){
		atakowany->Kolizja(this);
	}
	else if (atakowany->GetInicjatywa() == 0) {
		atakowany->Kolizja(this);
	}
	else {
		if (atakowany->GetInicjatywa()) {
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
		else atakowany->Kolizja(this);
		
	}
}

void Zwierze::CofnijRuch() {
	int tmp_last_move = this->GetLastMove();
	Polozenie tmp_cords=this->GetCords();

	if (tmp_last_move == LEWO) {
		this->SetCords(tmp_cords.x + 1, tmp_cords.y);
	}
	else if (tmp_last_move == PRAWO) {
		this->SetCords(tmp_cords.x - 1, tmp_cords.y);
	}
	else if (tmp_last_move == GORA) {
		this->SetCords(tmp_cords.x, tmp_cords.y + 1);
	}
	else if (tmp_last_move == DOL)
	{
		this->SetCords(tmp_cords.x, tmp_cords.y - 1);
	}
	this->SetLastMove(0);
}

Zwierze::~Zwierze() {};