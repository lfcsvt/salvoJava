let loggedUser = false
let loggedUser_id = ""
let newGames = []
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

function main (slvGames){
    makeList(slvGames)
    getGPId(slvGames)

}
function getGPId(slvGames){
slvGames.forEach(game => {
game.gamePlayers.forEach(gp =>{
//console.log(gp.gp_id)

})
})
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
     var user1_id = ""
     var user2_id = ""

        slvGames.forEach( game =>{

                arr.push(game.gamePlayers)
                     user1 = game.gamePlayers[0].player.name
                     user1_id = game.gamePlayers[0].gp_id

                     if(game.gamePlayers[1] == null || game.gamePlayers[1].player.user == undefined){
                          user2 = ""
                          user2_id= ""
                     } else {
                        user2 = game.gamePlayers[1].player.name
                        user2_id = game.gamePlayers[1].gp_id
                     }
                        myLi = document.createElement("li")
                        myLi.setAttribute("id","li" + game.id)
                        var time = new Date (game.created ).toGMTString()
                        myLi.innerHTML = ("Game #: " + game.id + " || "+"Started:  "
                                          + time + "  || Players:  " + user1 + " - " + user1_id
                                          + " X " + user2  +  " - " + user2_id )
                        myLi.setAttribute("class", "list-group-item")
                        list.appendChild(myLi);
                })

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
                    location.reload();
                    if(data.status == "Success"){
                    loggedUser == true
//                    fetch('http://localhost:8080/api/login', {
//                                        credentials: "include",
//                                        method: "POST",
//                                        headers: {
//                                                  "Content-Type": "application/x-www-form-urlencoded",
//                                                  'Accept': 'application/json',
//                                                  "Access-Control-Allow-Origin" : "*",
//                                                  "Access-Control-Allow-Credentials" : true
//                                                  },
//                                        body: `userName=${info.userName}&userPassword=${info.userPassword}`
//
//                                    })
//                                    .then(function(data){
//                                        console.log(data)
//                        });

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
                    loggedUser = true
                    playerInfo()
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

 function logout() {
                $.post("/api/logout").done(function () {
                    console.log("logged out");
                });
                location.reload();
}


function playerInfo(){
    var list = document.getElementById("gameList")
        if(loggedUser == true){
                  fetch('http://localhost:8080/api/games')
                          .then(response => response.json())
                          .then(response => {
                              const pData = response
                              newGames = pData.games
                              loggedUser_id = pData.loggedIn.id
                              newGames.forEach(game => {
                                game.gamePlayers.forEach(gp =>{
                                    if(gp.player.id != loggedUser_id && game.gamePlayers.length == 1){
                                        let btn = document.createElement("BUTTON")
                                        let myLi = document.getElementById("li" + game.id)
                                        btn.setAttribute("class", "btn-join")
                                        btn.setAttribute("onClick", "joinGame()")
                                        btn.innerHTML = "Join Game"
                                        myLi.appendChild(btn)
                                        list.appendChild(myLi)
                                    } else if(gp.player.id == loggedUser_id && game.gamePlayers.length == 2){
                                    loggedUser_id = gp.player.id
                                            let btn = document.createElement("BUTTON")
                                            let myLi = document.getElementById("li" + game.id)
                                            btn.setAttribute("class", "btn-join")
                                            btn.setAttribute("onClick", "resumeGame()")
                                            btn.innerHTML = "Resume Game"
                                            myLi.appendChild(btn)
                                            list.appendChild(myLi)
                                    }
                                })
                              })

                          })
                          .catch(err => console.log(err));


        }

}

function addNewGame(){
    if(loggedUser == true){
        fetch('http://localhost:8080/api/games', {
        credentials: "include",
        method: "POST",
        headers: {
             "Content-Type": "application/x-www-form-urlencoded",
             'Accept': 'application/json'
                  },
         })
         .then(function(response){
         return response.json();
         })
         .then(function(data){
         console.log(data)
         console.log(data.gPlayer_id)
         window.location =  "game.html?gp=" + data.gPlayer_id
         });
    } else {
        alert("You must be logged in to create a game")
    }

}

function joinGame(){
    let arr = []
    let gameData = ""
   $("li").click(function(){
   id = $(this).attr("id")
     var myvar = id;
     myvar = myvar.split('');
    myvar.forEach(el =>{
        arr.push(el)

    })
    delete arr[0]
    delete arr[1]
    gameData = arr.join('')

    fetch('/api/game/' + gameData +"/players", {
            credentials: "include",
            method: "POST",
            headers: {
                 "Content-Type": "application/x-www-form-urlencoded",
                 'Accept': 'application/json'
                      }
             })
             .then(function(response){
             return response.json();
             })
             .then(function(json){
             console.log(json.message)
             window.location =  "game.html?gp=" + json.message

             })
              .catch(err => console.log(err));
   });


}

 function resumeGame(){

   window.location =  "game.html?gp=" + loggedUser_id

}





