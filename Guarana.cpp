#include "Guarana.h"
#include "Swiat.h"

Guarana::Guarana() {}

Guarana::Guarana(Swiat* ref, int x, int y) {
	this->SetSila(0);
	this->SetInicjatywa(0);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

std::string Guarana::getName() {
	return "GUARANA";
}

void Guarana::Rysowanie() {
	gotoxy(this->GetCords().x, this->GetCords().y);
	textcolor(DARKGRAY);
	putch(ANIMAL_SIGN);
}

void Guarana::Kolizja(Organizm* atakowany) {
	atakowany->SetSila(atakowany->GetSila() + 3);
	this->referencja->list.usun(this,atakowany);
}

Guarana::~Guarana() {}
