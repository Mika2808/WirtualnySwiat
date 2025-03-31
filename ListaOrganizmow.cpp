#include "ListaOrganizmow.h"
#include "Swiat.h"
//ZWIERZÊTA
#include "Antylopa.h"
#include "Czlowiek.h"
#include "Lis.h"
#include "Owca.h"
#include "Zolw.h"
#include "Wilk.h"


//ROŒLINY
#include "BarszczSosnowskiego.h"
#include "Guarana.h"
#include "Mlecz.h"
#include "Trawa.h"
#include "WilczeJagody.h"


ListaOrganizmow::ListaOrganizmow() :head(nullptr), tail(nullptr) {}

void ListaOrganizmow::insert(Organizm *organizm) {
	if (head == nullptr) {
		head = organizm;
		tail = organizm;
		this->podsumowanie = "";
		SetIloscOrganizmow(0);
	}
	else {
		Organizm* tmp = head;
		int inicjatywa_organizmu = organizm->GetInicjatywa();
		if (inicjatywa_organizmu == 0) {
			tail->next = organizm;
			organizm->prev = tail;
			tail = organizm;
		}
		else {
			while (tmp != nullptr) {
				if (inicjatywa_organizmu > tmp->GetInicjatywa()) {
					if (tmp == head) {
						tmp->prev = organizm;
						organizm->next = tmp;
						head = organizm;
						break;
					}
					else {
						organizm->next = tmp;
						organizm->prev = tmp->prev;
						tmp->prev->next = organizm;
						tmp->prev = organizm;
						break;
					}
				}
				else if (tmp == tail) {
					if (tmp->GetInicjatywa() < inicjatywa_organizmu) {
						organizm->next = tail;
						organizm->prev = tail->prev;
						tail->prev->next = organizm;
						tail->prev = organizm;
						break;
					}
					else {
						tail->next = organizm;
						organizm->prev = tail;
						tail = organizm;
						break;
					}
				}
				tmp = tmp->next;
			}
		}
	}
	this->ilosc_organizmow++;
}

void ListaOrganizmow::Start(Swiat* swiat) {
	int szerokosc = swiat->GetSzerokosc();
	int wysokosc = swiat->GetWysokosc();
	
	Czlowiek* human = new Czlowiek(swiat, rand()%szerokosc+1, rand() % wysokosc + 1);
	this->insert(human);

	Guarana* trawa = new Guarana(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(trawa);

	Mlecz* mleczyk = new Mlecz(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(mleczyk);

	Lis* lisek = new Lis(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(lisek);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	lisek->New(lisek->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);

	/*Zolw* hfk = new Zolw(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(hfk);
	hfk->New(hfk->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);

	BarszczSosnowskiego* trawka = new BarszczSosnowskiego(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(trawka);

	Antylopa* kffk = new Antylopa(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(kffk);
	kffk->New(kffk->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);

	Wilk* ma = new Wilk(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(ma);
	ma->New(ma->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);

	Owca* pa = new Owca(swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	this->insert(pa);
	pa->New(pa->getName(), swiat, rand() % szerokosc + 1, rand() % wysokosc + 1);
	*/
	swiat->wykonajTure(0);
}

void ListaOrganizmow::usun(Organizm* organizm_usuwany,Organizm*stay) {
	
	if (organizm_usuwany->getName() != "HUMAN" || ((organizm_usuwany->getName() == "HUMAN") && (organizm_usuwany->referencja->GetUmiejetnosc() == false))) {
		Organizm* tmp = head;
		while (tmp != nullptr) {

			if (tmp == organizm_usuwany) {
				if (tmp == tail) {
					tmp->prev->next = nullptr;
					tail = tmp->prev;
					break;
				}
				else if (tmp == head) {
					tmp->next->prev = nullptr;
					head = tmp->next;
					break;
				}
				else {
					tmp->prev->next = tmp->next;
					tmp->next->prev = tmp->prev;
					break;
				}
			}
			tmp = tmp->next;
		}
		if(organizm_usuwany->GetInicjatywa())
		DodajOpis(organizm_usuwany->getName() + " GOT KILLED BY " + stay->getName());
		else DodajOpis(organizm_usuwany->getName() + " GOT EATEN BY " + stay->getName());

		this->ilosc_organizmow--;
	}
}

void ListaOrganizmow::Plansza() {
	Organizm* tmp = head;
	while (tmp != nullptr) {
		tmp->Rysowanie();
		tmp = tmp->next;
	}
}

void ListaOrganizmow::Ruch() {
	this->wysokosc_wyswietlania = 1;
	//KA¯DE ZE ZWIERZ¥T PRZECHODZI PRZEZ AKCJE KOLIZJÊ
	Organizm* tmp = head;

	while (tmp != nullptr) {
		if (tmp->GetRuch() == false) {
			tmp->Akcja();
			
			//TYLKO ZWIÊRZÊTA SIÊ RUSZAJ¥
			if(tmp->GetInicjatywa()!=0) this->Kolizje(tmp);
			tmp->SetRuch(true);
			Opis();
		}
		tmp = tmp->next;
	}
	tmp = head;

	while (tmp != nullptr) {
		tmp->SetRuch(false);
		tmp = tmp->next;
	}
}

void ListaOrganizmow::Kolizje(Organizm* organizm) {
	Organizm* tmp = head;
	Polozenie organizm_cords = organizm->GetCords();
	while (tmp != nullptr) {
		if (tmp == organizm) {
		tmp = tmp->next;
		continue;
		}
		else {
			Polozenie tmp_cords = tmp->GetCords();
			if ((organizm_cords.x == tmp_cords.x) && (organizm_cords.y == tmp_cords.y)) {	
				organizm->Kolizja(tmp);
				break;
			}
		}
		tmp = tmp->next;
	}
}

Polozenie ListaOrganizmow::Human() {
	Organizm* tmp = head;
	Czlowiek* human=nullptr;
	while (tmp != nullptr) {
		if (tmp->getName() == "HUMAN") {
			return tmp->GetCords();
		}
		tmp = tmp->next;
	}
	
	//JE¯ELI CZ£OWIEK NIE ISTNIEJE
	Polozenie a;
	a.x = 0; a.y = 0;

	return a;
}

void ListaOrganizmow::DodajOpis(std::string text) {
	this->podsumowanie += text + ".";
}

void ListaOrganizmow::Opis() {
	Organizm* tmp = head;
	
	int szerokosc=tmp->referencja->GetSzerokosc();
	
	textcolor(WHITE);
	
	gotoxy(szerokosc + 40,this->wysokosc_wyswietlania);
	
	if(this->podsumowanie!="")
	this->wysokosc_wyswietlania += 1;
	
	std::cout << this->podsumowanie;

	this->podsumowanie = "";
}

void ListaOrganizmow::Zapisz(FILE*plik) {
	Organizm* tmp = head;
	fprintf(plik, "%d\n", this->ilosc_organizmow);
	while (tmp != nullptr) {
		
		fprintf(plik, "%s\n", tmp->getName().c_str());
		fprintf(plik, "%d\n", tmp->GetCords().x);
		fprintf(plik, "%d\n", tmp->GetCords().y);		
		tmp = tmp->next;
	}	
}

void ListaOrganizmow::Wczytaj(FILE* plik) {
	Organizm* tmp = head;
	this->head->next = nullptr;
	this->tail->prev = nullptr;
	head = nullptr;
	tail = nullptr;

	int amountofanimals = 0;

	fscanf_s(plik, "%d7\n", &amountofanimals);
	
	for (int i = 0; i < amountofanimals; i++) {
		char name[256];
		int x=0, y=0;

		fscanf_s(plik, "%s\n", name,sizeof(name));
		fscanf_s(plik, "%d\n", &x);
		fscanf_s(plik, "%d\n", &y);
		tmp->New(name, tmp->referencja, x, y);
	}
}

void ListaOrganizmow::SetIloscOrganizmow(int a) {
	this->ilosc_organizmow = a;
}

int ListaOrganizmow::GetIloscOrganizmow() {
	return this->ilosc_organizmow;
}

ListaOrganizmow::~ListaOrganizmow(){}