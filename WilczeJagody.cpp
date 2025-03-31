#include "WilczeJagody.h"
#include "Swiat.h"

WilczeJagody::WilczeJagody() {}

WilczeJagody::WilczeJagody(Swiat* ref, int x, int y) {
	this->SetSila(0);
	this->SetInicjatywa(0);
	this->SetCords(x, y);
	this->referencja = ref;
}

std::string WilczeJagody::getName() {
	return "WOLF BERRIES";
}

void WilczeJagody::Rysowanie() {
	gotoxy(this->GetCords().x, this->GetCords().y);
	textcolor(LIGHTBLUE);
	putch(ANIMAL_SIGN);
}

void WilczeJagody::Kolizja(Organizm* atakowany) {
	this->referencja->list.usun(atakowany,this);
	atakowany->referencja->list.usun(this,atakowany);
}

WilczeJagody::~WilczeJagody() {}
