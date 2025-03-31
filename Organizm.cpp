#include "Organizm.h"

//ZWIERZÊTA
#include "Lis.h"
#include "Antylopa.h"
#include "Owca.h"
#include "Wilk.h"
#include "Zolw.h"


#include "Swiat.h"

//ROŒLINY
#include "Mlecz.h"
#include "Trawa.h"
#include "Guarana.h"
//#include "Owca.h"
//#include "Wilk.h"
//#include "Zolw.h"

Organizm::Organizm() {
	this->cords.x = 0;
	this->cords.y = 0;
	this->inicjatywa = 0;
	this->lastMove = NULL;
	this->sila = 0;
	this->referencja = nullptr;
	this->next = nullptr;
	this->prev = nullptr;
};

void Organizm::New(std::string txt, Swiat* ref, int x, int y) {
	Organizm* a = nullptr;
	if (txt == "FOX") {		
		a = new Lis(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "ANTELOPE") {
		a = new Antylopa(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "WOLF") {
		a = new Wilk(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "TURTLE") {
		a = new Zolw(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "SHEEP") {
		a = new Owca(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "GRASS") {
		a = new Trawa(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "DANDELION") {
		a = new Mlecz(ref, x, y);
		this->referencja->list.insert(a);
	}
	else if (txt == "GUARANA") {
		a = new Guarana(ref, x, y);
		this->referencja->list.insert(a);
	}
}

int Organizm::WolneMiejsca() {
	
	//WSKANIKI NA OTACZAJ¥CE ORGANIZMY
	Organizm* up_organizm = nullptr;
	Organizm* down_organizm = nullptr;
	Organizm* left_organizm = nullptr;
	Organizm* right_organizm = nullptr;
	Organizm* tmp_organizm = this->referencja->list.head;
	Polozenie this_tmp_cords = this->GetCords();

	while (tmp_organizm != nullptr) {
		Polozenie tmpcords = tmp_organizm->GetCords();
			
			//ORGANIZM Z GÓRY
			if ((tmpcords.x == this_tmp_cords.x) && (tmpcords.y == this_tmp_cords.y - 1)) {
				up_organizm = tmp_organizm;
			}

			//ORGANIZM Z DO£U
			else if (tmpcords.x == this_tmp_cords.x && tmpcords.y == this_tmp_cords.y + 1) {
				down_organizm = tmp_organizm;
			}

			//ORGANIZM Z LEWEJ
			else if (tmpcords.x == this_tmp_cords.x - 1 && tmpcords.y == this_tmp_cords.y) {
				left_organizm = tmp_organizm;
			}

			//ORGANIZM Z PRAWEJ
			else if (tmpcords.x == this_tmp_cords.x + 1 && tmpcords.y == this_tmp_cords.y) {
				right_organizm = tmp_organizm;
			}

		tmp_organizm = tmp_organizm->next;
	}

	//LOSOWANIE MIEJSCA WOLNEGO LUB ZAJÊTEGO PRZEZ S£ABSZY ORGANIM
	//l-LEWO p-PRAWO g-GÓRA d-DÓ£ n-NIE, TO NIE TO  
	char losowanie[4];
	for (int i = 0; i < 4; i++) {
		losowanie[i] = 'n';
	}
	int index = 0;

	//Z LEWEJ STRONY
	if ((left_organizm == nullptr) && (this_tmp_cords.x > 1)) {
		losowanie[index] = 'l';
		index++;
	}

	//Z PRAWEJ STRONY
	if ((right_organizm == nullptr) && (this_tmp_cords.x < this->referencja->GetSzerokosc())) {
		losowanie[index] = 'p';
		index++;
	}

	//Z GÓRY
	if ((up_organizm == nullptr)&& (this_tmp_cords.y > 1)) {
		losowanie[index] = 'g';
		index++;
	}

	//Z DO£U
	if ((down_organizm == nullptr) && this_tmp_cords.y < this->referencja->GetWysokosc()) {
		losowanie[index] = 'd';
		index++;
	}

	if (index != 0) {
		int tmp = rand() % index;
		if (losowanie[tmp] == 'l') {
			return LEWO;
		}
		else if (losowanie[tmp] == 'p') {
			return PRAWO;
		}
		else if (losowanie[tmp] == 'g') {
			return GORA;
		}
		else if (losowanie[tmp] == 'd')
		{
			return DOL;
		}
	}
	else return -1;
}

std::string Organizm::getName() {
	return "";
};

Polozenie Organizm::GetCords() {
	return cords;
}

int Organizm::GetSila() {
	return this->sila;
}

int Organizm::GetInicjatywa() {
	return this->inicjatywa;
}

int Organizm::GetLastMove() {
	return this->lastMove;
}

bool Organizm::GetRuch() {
	return this->ruch;
}

void Organizm::SetCords(int x, int y) {
	this->cords.x = x;
	this->cords.y = y;
}

void Organizm::SetInicjatywa(int x) {
	this->inicjatywa = x;
}

void Organizm::SetRuch(bool x) {
	this->ruch = x;
}

void Organizm::SetSila(int x) {
	this->sila = x;
}

void Organizm::SetLastMove(int x) {
	this->lastMove = x;
}

void Organizm::Akcja() {};
void Organizm::Kolizja(Organizm* atakowany) {};
void Organizm::Rysowanie() {};
void Organizm::CofnijRuch() {};

Organizm::~Organizm() {};