#include "Owca.h"

Owca::Owca() {}

Owca::Owca(Swiat* ref, int x, int y) {
	this->SetSila(4);
	this->SetInicjatywa(4);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

std::string Owca::getName() {
	return "SHEEP";
}

void Owca::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y); 
	textcolor(GREEN);
	putch(ANIMAL_SIGN);
}
Owca::~Owca() {}

