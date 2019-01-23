function getData() {
    fetch('http://localhost:8080/api/games', {

        })
        .then(response => response.json())
        .then(response => {
            let slvGames = response.games
            console.log(slvGames)
            main(slvGames)

        })

        .catch(err => console.log(err));
}

function getLeaderData() {
    fetch('http://localhost:8080/api/leaderboard', {

        })
        .then(response => response.json())
        .then(response => {
            let leaders = response
              main2(leaders)
        })

        .catch(err => console.log(err));
}

getLeaderData()
getData()
login()
//logout()

function main (slvGames){
makeList(slvGames)


}

function main2 (leaders){

createLBoard(leaders)

}

function createLBoard(leaders){

var lTable = document.getElementById("leader-table")
    leaders.forEach(player => {
     var  count = {};
     player.scores.forEach(function(i) { count[i] = (count[i]||0) + 1;});
       let total = player.scores.reduce((a, b) => a + b, 0)
        let row = document.createElement("tr")
        let won = count[1]
        let lost = count[0]
        let tied = count[0.5]
        row.insertCell().innerHTML = player.player_name
        row.insertCell().innerHTML = total
        if(won == undefined || lost == undefined || tied == undefined){
            won = " ---"
            lost = " ---"
            tied = " ---"
              row.insertCell().innerHTML = won
              row.insertCell().innerHTML = lost
              row.insertCell().innerHTML = tied
        } else {
             row.insertCell().innerHTML = won
             row.insertCell().innerHTML = lost
             row.insertCell().innerHTML = tied
        }
    lTable.appendChild(row)
    })

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
                myLi.setAttribute("class", "list-group-item")
                list.appendChild(myLi);
        })
}

function login() {
                let email = document.getElementById("exampleInputEmail1").value.toLowerCase();
                let password = document.getElementById("exampleInputPassword1").value;
                console.log(email)
                console.log(password)

//               $.post("/api/login", {
//                       userName: email,
//                        password: password
//                  })
//                   .then(response => {
//                         console.log("logged in"),
//                        console.log(JSON.stringify(response)),
//                            location.reload();
//                    })
//                    .catch(error => console.error('Error:', error))
            }

 function logout() {
//                $.post("/api/logout").done(function () {
//                    console.log("logged out");
//                });
//                location.reload();
            }
