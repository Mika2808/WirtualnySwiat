#include "Wilk.h"
Wilk::Wilk(){}

Wilk::Wilk(Swiat* ref, int x, int y) {
	this->SetSila(9);
	this->SetInicjatywa(5);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

void Wilk::Rysowanie(){
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y);
	textcolor(BLUE); 
	putch(ANIMAL_SIGN);
}

std::string Wilk::getName() {
	return "WOLF";
}
Wilk::~Wilk() {}
