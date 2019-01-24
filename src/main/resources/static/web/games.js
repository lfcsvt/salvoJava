
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
//login()
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

 function logout() {
                $.post("/api/logout").done(function () {
                    console.log("logged out");
                });
                location.reload();
}



function signIn() {
      let name = document.getElementById("create-user").value.toLowerCase();
      let userName = document.getElementById("create-username").value.toLowerCase();
      let userPassword = document.getElementById("create-password").value;

             let info ={
                  fullName: name,
                  userName: userName,
                  userPassword: userPassword,
                }
                        console.log(info)
                   fetch('http://localhost:8080/api/players', {
                    credentials: "include",
                    method: "POST",
                    headers: {
                                       "Content-Type": "application/x-www-form-urlencoded",
                                       'Accept': 'application/json',
                                     },
                    body: `fullName=${info.fullName}&userName=${info.userName}&userPassword=${info.userPassword}`

                })
                .then(function(response){
                   return response.json();
                })
                .then(function(data){
                    console.log(data)
                    if(data.status == "Success"){
                        var form2  = document.getElementById("signIn")
                        form2.style.display = 'none';
                        var btnJoin  = document.getElementById("btn3")
                        btnJoin.style.visibility = 'visible';
                    }
                });

}

function login() {
      let userName = document.getElementById("username").value.toLowerCase();
      let userPassword = document.getElementById("password").value;

             let info ={
                  userName: userName,
                  userPassword: userPassword,
                }
                   fetch('http://localhost:8080/api/login', {
                    credentials: "include",
                    method: "POST",
                    headers: {
                              "Content-Type": "application/x-www-form-urlencoded",
                              'Accept': 'application/json',
                              "Access-Control-Allow-Origin" : "*",
                              "Access-Control-Allow-Credentials" : true
                              },
                    body: `userName=${info.userName}&userPassword=${info.userPassword}`

                })
                .then(function(data){
                    console.log(data)
                    if(data.status == 200){
                    var form  = document.getElementById("login")
                    var btnJoin  = document.getElementById("btn3")
                     form.style.display = 'none';
                     btnJoin.style.visibility = 'visible';
                    } else{
                    alert("User not registered. Please sign In")
                            var form2  = document.getElementById("signIn")
                            var form  = document.getElementById("login")
                            form.style.display = 'none';
                            form2.style.visibility = 'visible';
                    }
                });

}

