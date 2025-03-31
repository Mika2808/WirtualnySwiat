#include "Roslina.h"
#include "Swiat.h"

Roslina::Roslina() {};

void Roslina::Akcja() {
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

void Roslina::Kolizja(Organizm* atakowany){
	this->referencja->list.usun(this,atakowany);
};

void Roslina::CofnijRuch() {}
void Roslina::Rysowanie() {};
Roslina::~Roslina() {};