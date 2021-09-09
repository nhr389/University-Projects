#include <iostream>
#include <string>
#include <vector>

#include "Property.h"

using namespace std;

Property::Property(string name, string color, int cost, int rentBase, int rentOneHouse, int rentTwoHouse,
	int rentThreeHouse, int rentFourHouse, int rentHotel, int mortgage, int houseBuyCost, int hotelBuyCost) :
	Tile(name, "Property"), propColor(color), propOwned(false), housesOnProp(0), 
	mortgageValue(mortgage), houseCost(houseBuyCost), hotelCost(hotelBuyCost), hotelIsOwned(false),
	propCost(cost), ownedByNum(0)
{
	rents.resize(6);
	rents.at(0) = rentBase;
	rents.at(1) = rentOneHouse;
	rents.at(2) = rentTwoHouse;
	rents.at(3) = rentThreeHouse;
	rents.at(4) = rentFourHouse;
	rents.at(5) = rentHotel;
}

bool Property::PropIsOwned() {
	return propOwned;
}

int Property::GetCost() {
	return propCost;
}

void Property::PurchaseProp() {
	propOwned = true;
}

string Property::GetColor() {
	return propColor;
}

int Property::GetOwnedBy() {
	return ownedByNum;
}

void Property::SetOwnedBy(int player) {
	ownedByNum = player;
	propOwned = true;
}

void Property::ReleaseProp() {
	ownedByNum = 0;
	propOwned = false;
}

//FIXME: Should use number of houses to decide how much rent. Use a map the maps <color, housesOwned>
int Property::GetRent() {
	return rents.at(0);
}

// textual based data representation that will be deleted if/when GUI is created
void Property::PrintDescription() {
	cout << "Name: " << this->GetName() << "\t Color:" << propColor << endl;
	cout << "Rent: \t$" << rents.at(0) << endl;
	cout << "With 1 House: \t$" << rents.at(1) << endl;
	cout << "With 2 House: \t$" << rents.at(2) << endl;
	cout << "With 3 House: \t$" << rents.at(3) << endl;
	cout << "With 4 House: \t$" << rents.at(4) << endl;
	cout << "With HOTEL: \t$" << rents.at(5) << endl;
	
	cout << endl << "Mortgage Value $" << mortgageValue << endl;
	cout << endl << "House cost $" << houseCost << "  each" << endl;
	cout << endl << "Hotels, $" << hotelCost << " each plus 4 houses" << endl;
}

