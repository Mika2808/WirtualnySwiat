#include "Lis.h"
#include "Swiat.h"

std::string Lis::getName(){
	return "FOX";
}

Lis::Lis() {}

Lis::Lis(Swiat* ref, int x, int y) {
	this->SetSila(3);
	this->SetInicjatywa(7);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

void Lis::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y);
	textcolor(CYAN);
	putch(ANIMAL_SIGN);
}

void Lis::Akcja() {
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

		//ORGANIZM Z GÓRY
		if ((tmpcords.x == this_tmp_cords.x) && (tmpcords.y == this_tmp_cords.y - 1)) {
			up_organizm = tmp_organizm;
		}

		//ORGANIZM Z DO£U
		else if (tmpcords.x == this_tmp_cords.x && tmpcords.y == this_tmp_cords.y + 1) {
			down_organizm = tmp_organizm;
		}

		//ORGANIZM Z PRAWEJ
		else if (tmpcords.x == this_tmp_cords.x + 1 && tmpcords.y == this_tmp_cords.y) {
			right_organizm = tmp_organizm;
		}

		//ORGANIZM Z LEWEJ
		else if (tmpcords.x == this_tmp_cords.x - 1 && tmpcords.y == this_tmp_cords.y) {
			left_organizm = tmp_organizm;
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

	int tmp_sila = this->GetSila();

	//Z LEWEJ STRONY
	if (((left_organizm == nullptr) || (left_organizm->GetSila() <= tmp_sila))
		&& (this_tmp_cords.x > 1)) {
		losowanie[index] = 'l';
		index++;
	}

	//Z PRAWEJ STRONY
	if (((right_organizm == nullptr) || (right_organizm->GetSila() <= tmp_sila))
		&& (this_tmp_cords.x < this->referencja->GetSzerokosc())) {
		losowanie[index] = 'p';
		index++;
	}

	//Z GÓRY
	if (((up_organizm == nullptr) || (up_organizm->GetSila() <= tmp_sila))
		&& (this_tmp_cords.y > 1)) {
		losowanie[index] = 'g';
		index++;
	}

	//Z DO£U
	if (((down_organizm == nullptr) || (down_organizm->GetSila() <= tmp_sila))
		&& this_tmp_cords.y < this->referencja->GetWysokosc()) {
		losowanie[index] = 'd';
		index++;
	}


	if (index != 0) {
		int tmp = rand() % index;
		if (losowanie[tmp] == 'l') {
			this->SetCords(this_tmp_cords.x - 1, this_tmp_cords.y);
			this->SetLastMove(LEWO);
		}
		else if (losowanie[tmp] == 'p') {
			this->SetCords(this_tmp_cords.x + 1, this_tmp_cords.y);
			this->SetLastMove(PRAWO);
		}
		else if (losowanie[tmp] == 'g') {
			this->SetCords(this_tmp_cords.x, this_tmp_cords.y - 1);
			this->SetLastMove(GORA);
		}
		else if(losowanie[tmp] == 'd'){
			this->SetCords(this_tmp_cords.x, this_tmp_cords.y + 1);
			this->SetLastMove(DOL);
		}
		else {
			this->SetLastMove(0);
		}
	}
}

Lis::~Lis(){}