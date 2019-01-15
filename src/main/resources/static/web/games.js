function getData() {
    fetch('http://localhost:8080/api/games', {

        })
        .then(response => response.json())
        .then(response => {
            var slvGames = response
            console.log(slvGames)
            main(slvGames)

        })

        .catch(err => console.log(err));
}


getData()

function main (slvGames){
makeList(slvGames)

}

function makeList(slvGames){
    var arr = []
    var list = document.getElementById("gameList")
    var myLi = document.createElement("li")
     var user1 = ""
     var user2 = ""
        slvGames.forEach( game =>{
        arr.push(game.gamePlayers)
             user1 = game.gamePlayers[0].player.user
             if(game.gamePlayers[1] == null || game.gamePlayers[1].player.user == undefined){
                user2 = ""
             } else {
                user2 = game.gamePlayers[1].player.user
             }

                myLi = document.createElement("li")
                myLi.innerHTML = ("Game Id  " + game.id + "  game creation  "
                                  + game.created + "  game users  " + user1 + " , " + user2 )
                list.appendChild(myLi);
        })
}

