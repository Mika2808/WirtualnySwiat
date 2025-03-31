#include "BarszczSosnowskiego.h"
#include "Swiat.h"

BarszczSosnowskiego::BarszczSosnowskiego() {}

BarszczSosnowskiego::BarszczSosnowskiego(Swiat* ref, int x, int y) {
	this->SetSila(10);
	this->SetInicjatywa(0);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}
std::string BarszczSosnowskiego::getName() {
	return "BARSZCZ SOSNOWSKIEGO";
}

void BarszczSosnowskiego::Rysowanie() {
	gotoxy(this->GetCords().x, this->GetCords().y);
	textcolor(LIGHTGREEN);
	putch(ANIMAL_SIGN);
}

void BarszczSosnowskiego::Akcja() {
	//WSKANIKI NA OTACZAJ¥CE ORGANIZMY
	Organizm* up_organizm = nullptr;
	Organizm* down_organizm = nullptr;
	Organizm* left_organizm = nullptr;
	Organizm* right_organizm = nullptr;
	Organizm* tmp_organizm = this->referencja->list.head;

	int wysokosc = this->referencja->GetWysokosc();
	int szerokosc = this->referencja->GetSzerokosc();

	Polozenie this_tmp_cords = this->GetCords();

	while (tmp_organizm != nullptr) {
		Polozenie tmpcords = tmp_organizm->GetCords();
		
		//SPRAWDZENIE CZY ORGANIZM JEST ZWIERZÊCIEM
		if (tmp_organizm->GetInicjatywa() != 0) {
		
			//ZWIERZE Z GÓRY
			if ((tmpcords.x == this_tmp_cords.x) && (tmpcords.y == this_tmp_cords.y - 1)) {
				up_organizm = tmp_organizm;
				this->referencja->list.usun(up_organizm,this);
			}

			//ZWIERZE Z DO£U
			else if (tmpcords.x == this_tmp_cords.x && tmpcords.y == this_tmp_cords.y + 1) {
				down_organizm = tmp_organizm;
				this->referencja->list.usun(down_organizm,this);				
			}

			//ZWIERZE Z PRAWEJ
			else if (tmpcords.x == this_tmp_cords.x + 1 && tmpcords.y == this_tmp_cords.y) {
				right_organizm = tmp_organizm;
				this->referencja->list.usun(right_organizm,this);
			}

			//ZWIERZE Z LEWEJ
			else if (tmpcords.x == this_tmp_cords.x - 1 && tmpcords.y == this_tmp_cords.y) {
				left_organizm = tmp_organizm;
				this->referencja->list.usun(left_organizm, this);
			}
		}
		tmp_organizm = tmp_organizm->next;
	}
}

void BarszczSosnowskiego::Kolizja(Organizm* atakowany) {
	this->referencja->list.usun(atakowany,this);
	atakowany->referencja->list.usun(this,atakowany);
}

BarszczSosnowskiego::~BarszczSosnowskiego() {}

