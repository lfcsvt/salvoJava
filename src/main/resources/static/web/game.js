makeGrid()
makeSGrid()
getData()

 function getData() {
 var urlParams = new URLSearchParams(window.location.search);
 var myParam = urlParams.get('gp');
 var url1 = 'http://localhost:8080/api/game_view/' + myParam ;
        var urls = [url1, "http://localhost:8080/api/leaderboard"]
        let responseArray = urls.map((url) => {
          let request = new Request(url, {
          });
          return fetch(request).then(response => response.json());
        });

        Promise.all(responseArray).then(allResults => {
          console.log(allResults)
          let slvGames  = allResults[0]
          let leaderboard  = allResults[1]
                          main (slvGames, leaderboard)
        })
      }
function main(slvGames, leaderboard){
   addPlayerInfo(slvGames)
   placeShips(slvGames)
   placeSalvoes(slvGames)
   placeSHits(slvGames)
   myOpponentHits(slvGames)
   createLBoard(leaderboard)
//   enemyShips(slvGames)

}

function makeGrid(){
var tblBody = document.getElementById("grid-tBody")
var tbl = document.getElementById("grid-table");
var letArr = ["A", "B" ,"C" ,"D" ,"E","F", "G", "H", "I", "J" ]
var numArr = [1, 2 ,3 ,4 , 5, 6, 7, 8, 9, 10 ]
// create the row for out table
var row = document.createElement("tr");
    tblBody.innerHTML = ""
    for (var i = 0; i < letArr.length; i++) {
//        row.setAttribute("id",letArr[i])
        var letter = letArr[i]
        var blank = " "
        var row = document.createElement("tr");
        var td = document.createElement("td");

        row.insertCell().innerHTML = letter

        for(var j = 0 ; j< numArr.length; j++){
              var td = document.createElement("td");
              td.setAttribute("id",letArr[i] + numArr[j])
            row.appendChild(td)
        }

        tblBody.appendChild(row);

    }
    tbl.appendChild(tblBody);
}

function makeSGrid(){
var tblBody = document.getElementById("salvo-tBody")
var tbl = document.getElementById("salvo-table");
var letArr = ["A", "B" ,"C" ,"D" ,"E","F", "G", "H", "I", "J" ]
var numArr = [1, 2 ,3 ,4 , 5, 6, 7, 8, 9, 10 ]
var row = document.createElement("tr");
    tblBody.innerHTML = ""
    for (var i = 0; i < letArr.length; i++) {
        var letter = letArr[i]
        var blank = " "
        var row = document.createElement("tr");
        var td = document.createElement("td");
        row.insertCell().innerHTML = letter
        for(var j = 0 ; j< numArr.length; j++){
              var td = document.createElement("td");
              td.setAttribute("id","S" + letArr[i] + numArr[j])
            row.appendChild(td)
        }
        tblBody.appendChild(row);
    }
    tbl.appendChild(tblBody);
}

function addPlayerInfo(slvGames){
    var myPlayer_id = slvGames.gPlayer_id
    var infoPlace = document.getElementById("game-info")
    var myPlayer_user= ""
    var myOpponent= ""
    var arr = slvGames.gamePlayer
    var user = document.createElement("h3");
        arr.forEach(elem => {
            if(elem.player.id == myPlayer_id){
            myPlayer_user = elem.player.user
            user = document.createElement("h3");
            var t = document.createTextNode("User: " + myPlayer_user + " X ")
            var x = document.createTextNode("Opponent: " + myOpponent)
            user.appendChild(t);
            user.appendChild(x);
            infoPlace.appendChild(user);
            }   else if(elem.player.id !== myPlayer_id){
                              myOpponent = elem.player.user

                              }
        })
}

function placeShips(slvGames){
   var arr = slvGames.ships
   arr.forEach(locate => {
        locate.ships_locations.forEach(place => {
            var shipSeg = document.getElementById(place)
                if(shipSeg.id == place){
                    shipSeg.setAttribute("style", "background-color: blue;");
                }
        })
   })

}

function placeSalvoes(slvGames){
    let turn = document.createElement("p");
    let arr = slvGames.salvoes
    let user_id = slvGames.gPlayer_id
    arr.forEach(elem => {
        if(elem.gp_id == user_id){
            elem.gp_salvoes.forEach(elem2 => {
                elem2.salvoes_locations.forEach(place => {
                    var slvSeg = document.getElementById("S" + place)
                       if(slvSeg.id == "S" + place){
                             let hitDiv = document.createElement("div")
                             hitDiv.setAttribute("class", "hit-div")
                            let x = elem2.turn;
                           let text = document.createElement("p")
                           var t = document.createTextNode(x);
                           text.appendChild(t)
                           hitDiv.appendChild(text)
                           slvSeg.appendChild(hitDiv)
                    }
                })
            })
        }
    })
}


function placeSHits(slvGames){
    let turn = document.createElement("p");
    let arr = slvGames.salvoes
    let user_id = slvGames.gPlayer_id
    arr.forEach(elem => {
        if(elem.gp_id !== user_id){
            elem.gp_salvoes.forEach(elem2 => {
                elem2.salvoes_locations.forEach(place => {
                    var slvSeg = document.getElementById( place)
                       if(slvSeg.id ==  place){
                            let hitDiv = document.createElement("div")
                            hitDiv.setAttribute("class", "hit-div")
                           let x = elem2.turn;
                           let text = document.createElement("p")
                           var t = document.createTextNode(x);
                           text.appendChild(t)
                           hitDiv.appendChild(text)
                           slvSeg.appendChild(hitDiv)
                    }
                })
            })
        }
    })
}

function myOpponentHits(slvGames){
    let arr = slvGames.salvoes
    let arr2 = slvGames.ships
    let shipLocArr = []
    let slvLocArr = []
    let user_id = slvGames.gPlayer_id
    arr2.forEach(elem => {
        elem.ships_locations.forEach(elem2 => {
            shipLocArr.push(elem2)
        })
    })
    arr.forEach(elem =>{
        if(elem.gp_id !== user_id){
            elem.gp_salvoes.forEach(elem2 =>{
            elem2.salvoes_locations.forEach(elem3 => {
                 let a = shipLocArr.filter(loc => loc == elem3)
                 if(a.length > 0){
                   a.forEach(hit => {
                        let div_id = hit
                        let divHit = document.getElementById(hit)
                        divHit.firstChild.setAttribute("style", "background-color: red")
                   })

                 }
            })

            })
        }
    })
}

function createLBoard(leaderboard){
var lTable = document.getElementById("leader-table")
    leaderboard.forEach(player => {
    console.log(player);
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

//function enemyShips(slvGames){
//    let arr = slvGames.salvoes
//    let shipLocArr = []
//    let slvLocArr = []
//    let user_id = slvGames.gPlayer_id
//
//        slvGames.gamePlayer.forEach(elem => {
//
//            if(elem.gp_id !== user_id){
//                fetch( 'http://localhost:8080/api/game_view/' + elem.gp_id)
//                 .then(response => response.json())
//                 .then(response => {
//                 var enemyShips = response.ships
//                 enemyShips.forEach(elem => {
//                 elem.ships_locations.forEach(loc => {
//                    shipLocArr.push(loc)
//                 } )
//
//                 })
//
//                 slvGames.salvoes.forEach(slv => {
//                      if(slv.gp_id == user_id){
//                        slv.gp_salvoes.forEach(place => {
//                             place.salvoes_locations.forEach(slvPlace => {
//                                let a = shipLocArr.filter(element => element == slvPlace)
//                                if(a.length > 0){
//                                    a.forEach(hit => {
//                                    let div_id = "S" + hit
//                                    let divHit = document.getElementById("S" + hit)
//                                    divHit.firstChild.setAttribute("style", "background-color: red")
//                                    })
//
//                                }
//                             })
//                        })
//
//                      }
//               })
//
//            })
//        }
//    })
//
//}




