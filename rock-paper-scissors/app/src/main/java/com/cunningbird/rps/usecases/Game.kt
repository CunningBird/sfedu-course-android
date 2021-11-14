package com.cunningbird.rps.usecases

class Game(userChoice: Int) {

    var winner: String = "Nobody"

    var computerChoice = (0..2).random()

    init {
        if ((computerChoice == 1 && userChoice == 0) || (computerChoice == 2 && userChoice == 1) || (computerChoice == 0 && userChoice == 2)) {
            winner = "You"
        }
        if ((computerChoice == 0 && userChoice == 1) || (computerChoice == 1 && userChoice == 2) || (computerChoice == 2 && userChoice == 0)) {
            winner = "Computer"
        }
    }
}