#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;

class Player {
public:
	Player();
	Player(string name, int playerNum);
	void PrintPlayerInfo();
	int GetNetWorth();
	string GetName();
	string GetColor();
	void MovePosition(int toMove);
	int GetPosition();
	bool IsInJail();
	void AddToColorMap(string colorToAdd);
	bool CanPurchaseHouse(string colorToPurchase);
	void PayRent(int toPay);
	void CollectRent(int toCollect);
	void PurchaseProperty(int propCost, int position);
	bool IsBankrupt();
	void GoToJail();
	int DoublesRolled();
	void IncrementDoubles();
	bool GetInJail();
	void ResetDoubles();
	void FreeFromJail();
	vector<int> GetVect();
	void AddRailroad();
	int GetNumRailroads();
	void AddUtil();
	int GetNumUtils();
private:
	string playerName;
	string playerColor;
	int numRailRoads;
	int numUtils;
	int playerNum;
	int netWorth;
	int playerPosition;
	int rollsInJail;
	bool inJail;
	int playerDoubles;
	vector<int> propsOwned;
	map<string, int> numOfColor;

};