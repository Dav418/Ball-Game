using System;
using System.Collections.Generic;


namespace Ass1Server
{

    class Player{
        private bool ball;
        bool hasBall(){ return ball; }
        void giveBall(){ ball=true; }
        void takeBall(){ ball=false; }

        private int ID;
        int getID() {
            return ID;
        }

    }
    class GameFunctions
    {
        private List<Player> clientList;

        public GameFunctions(){ 
            clientList = new List<Player> ();
        }

        void updateClients(){

        }

        void addToList(Player p){
            clientList.Add(p);
            if((clientList.Count) == 1){

            }
        }

        void removeFromList(){

        }

        private int passBallRandom(){
            return -1;
        }

        void passBall(){

        }

    }
}
