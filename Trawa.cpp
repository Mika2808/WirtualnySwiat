#include "Trawa.h"

Trawa::Trawa() {}

Trawa::Trawa(Swiat* ref, int x, int y) {
	this->SetSila(0);
	this->SetInicjatywa(0);
	this->SetCords(x, y);
	this->referencja = ref;
	this->SetRuch(true);
}

std::string Trawa::getName() {
	return "GRASS";
}

void Trawa::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y); 
	textcolor(BROWN);
	putch(ANIMAL_SIGN);
}
Trawa::~Trawa(){}
