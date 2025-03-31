#include "Antylopa.h"
#include "Swiat.h"

Antylopa::Antylopa() {};

Antylopa::Antylopa(Swiat* ref, int x, int y) {
	this->SetSila(4);
	this->SetInicjatywa(4);
	this->SetCords(x, y);
	this->SetRuch(true);
	this->referencja = ref;
}

std::string Antylopa::getName() {
	return "ANTELOPE";
}

void Antylopa::Akcja() {
	Polozenie tmp_cords = this->GetCords();
	int tmpx = tmp_cords.x;
	int tmpy = tmp_cords.y;
	int liczba = 0;
	bool ruch = false;
	while (ruch == false) {
		liczba = rand() % 4;

		//GÓRA
		if (liczba == 0) { 
			if (tmp_cords.y > 2) {
				this->SetCords(tmp_cords.x, tmp_cords.y - 2);
				this->SetLastMove(GORA);
				ruch = true;
			}
			else continue;
		}
		//DÓL
		else if (liczba == 1) {
			if (tmp_cords.y < this->referencja->GetWysokosc() - 1) {
				this->SetCords(tmp_cords.x, tmp_cords.y + 2);
				this->SetLastMove(DOL);
				ruch = true;
			}
			else continue;
		}
		else if (liczba == 2) {//PRAWO 
			if (tmp_cords.x < this->referencja->GetSzerokosc() - 1) {
				this->SetCords(tmp_cords.x + 2, tmp_cords.y);
				this->SetLastMove(PRAWO);
				ruch = true;
			}
			else continue;
		}
		else { //LEWO 
			if (tmp_cords.x > 2) {
				this->SetCords(tmp_cords.x - 2, tmp_cords.y);
				this->SetLastMove(LEWO);
				ruch = true;
			}
			else continue;
		}
	}
};

void Antylopa::Kolizja(Organizm* atakowany) {


	//ANTYLOPA TRAFIA NA ANTYLOPÊ
	if (this->getName() == atakowany->getName()) {
		this->CofnijRuch();
		int nowy = atakowany->WolneMiejsca();
		if (nowy != -1) {

			Polozenie tmp_cords = atakowany->GetCords();

			if (nowy == LEWO) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x - 1, tmp_cords.y);
			}
			else if (nowy == PRAWO) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x + 1, tmp_cords.y);
			}
			else if (nowy == GORA) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x, tmp_cords.y - 1);
			}
			else if (nowy == GORA) {
				atakowany->New(atakowany->getName(), atakowany->referencja, tmp_cords.x, tmp_cords.y + 1);
			}
		}
		atakowany->SetRuch(true);
	}

	//ANTYLOPA TRAFIA NA ROŒLINÊ
	else if (atakowany->GetInicjatywa() == 0) {
		atakowany->Kolizja(this);
	}
	else {

		//SPRAWDZAMY CZY ANTYLOPA MA MIEJSCE DO UCIECZKI
		int z = this->WolneMiejsca();

		//JE¯ELI ANTYLOPA NIE MA MIEJSCA DO UCIECZKI TO I TAK MUSI WALCZYÆ		

		//NIE UCIEKA LUB NIE MA GDZIE UCIEC
		if ((rand() % 2 == 0) || (z == -1)) {

			//ANTYLOPA JEST SILNIEJSZA
			if (atakowany->GetSila() < this->GetSila()) {
				this->referencja->list.usun(atakowany,this);				
			}

			//ORGANIZM JEST SILNIEJSZY
			else if (atakowany->GetSila() > this->GetSila()) {
				atakowany->referencja->list.usun(this,atakowany);
			}

			//SI£Y SA WYRÓWNANE
			else {

				//ANTYLOPA JEST ATAKOWANY
				if (this->GetRuch()) {
					atakowany->referencja->list.usun(this,atakowany);
						
				}

				//ORGANIZM JEST ATAKOWANY PRZEZ ANTYLOPÊ
				else {
					this->referencja->list.usun(atakowany,this);
				}
			}			
		}
	}
}

int Antylopa::WolneMiejsca() {
	
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
		if ((tmpcords.x == this_tmp_cords.x) && (tmpcords.y == this_tmp_cords.y - 2)) {
			up_organizm = tmp_organizm;
		}

		//ORGANIZM Z DO£U
		else if (tmpcords.x == this_tmp_cords.x && tmpcords.y == this_tmp_cords.y + 2) {
			down_organizm = tmp_organizm;
		}

		//ORGANIZM Z LEWEJ
		else if (tmpcords.x == this_tmp_cords.x - 2 && tmpcords.y == this_tmp_cords.y) {
			left_organizm = tmp_organizm;
		}

		//ORGANIZM Z PRAWEJ
		else if (tmpcords.x == this_tmp_cords.x + 2 && tmpcords.y == this_tmp_cords.y) {
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
	if ((up_organizm == nullptr) && (this_tmp_cords.y > 1)) {
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
void Antylopa::Rysowanie() {
	Polozenie tmp_cords = this->GetCords();
	gotoxy(tmp_cords.x, tmp_cords.y);
	textcolor(MAGENTA);
	putch(ANIMAL_SIGN);
};

Antylopa::~Antylopa() {};