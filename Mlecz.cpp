#include "Mlecz.h"

Mlecz::Mlecz() {}

Mlecz::Mlecz(Swiat* ref, int x, int y) {
	this->SetSila(0);
	this->SetInicjatywa(0);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}
std::string Mlecz::getName() {
	return "DANDELION";
}

void Mlecz::Rysowanie() {
	gotoxy(this->GetCords().x, this->GetCords().y);
	textcolor(YELLOW);
	putch(ANIMAL_SIGN);
}

void Mlecz::Akcja() {
	for (int i = 0; i < 3; i++) {
		//WOLNE MIEJSCE GDZIE ZASIEJEMY NOW¥ ROŒLINÊ
		int zasiewanie = this->WolneMiejsca();

		Polozenie tmp_cords = this->GetCords();
		if (this->GetRuch() == false) {
			if (zasiewanie != -1) {
				//ZASIEWANIE Z LEWEJ STRONY
				if (zasiewanie == LEWO) {
					this->New(this->getName(), this->referencja, tmp_cords.x - 1, tmp_cords.y);
				}
				//ZASIEWANIE Z PRAWEJ STRONY
				else if (zasiewanie == PRAWO) {
					this->New(this->getName(), this->referencja, tmp_cords.x + 1, tmp_cords.y);
				}
				//ZASIEWANIE DO GÓRY
				else if (zasiewanie == GORA) {
					this->New(this->getName(), this->referencja, tmp_cords.x, tmp_cords.y - 1);
				}
				else if (zasiewanie == DOL) {
					this->New(this->getName(), this->referencja, tmp_cords.x, tmp_cords.y + 1);
				}
			}
		}
	}
}


Mlecz::~Mlecz() {}
