makeGrid()
//makeSGrid()

  var urlParams = new URLSearchParams(window.location.search);
  var myParam = urlParams.get('gp');
  getData()
  let turn;

 function getData() {
 var url1 = 'http://localhost:8080/api/game_view/' + myParam ;
        var urls = [url1, "http://localhost:8080/api/leaderboard"]
        let responseArray = urls.map((url) => {
          let request = new Request(url, {
          });
          return fetch(request).then(response => response.json());
        });
        Promise.all(responseArray).then(allResults => {
          console.log(allResults[0])
            let slvGames  = allResults[0]
                main (slvGames)
        })
      }

function main(slvGames){
   turn = getTurn(slvGames)
   makeSGrid(slvGames)
   addPlayerInfo(slvGames)
   placeShips(slvGames)
   placeSalvoes(slvGames)
   placeSHits(slvGames)
   myOpponentHits(slvGames)
   getTurn(slvGames)
//   checkSalvo()

}



function makeGrid(){
var tblBody = document.getElementById("grid-tBody")
var tbl = document.getElementById("grid-table");
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
              td.setAttribute("id",letArr[i] + numArr[j])
              td.setAttribute("ondrop", "drop(event)")
              td.setAttribute("ondragover", "allowDrop(event)")
              row.appendChild(td)
        }

        tblBody.appendChild(row);

    }
    tbl.appendChild(tblBody);
}
function getTurn(slvGames){
let n;
    slvGames.salvoes.forEach(el => {
        if(el.gp_id == myParam){
            el.gp_salvoes.length
                n = el.gp_salvoes.length + 1

        }
    })
    return n
}
function makeSGrid(slvGames){
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
               td.setAttribute("onClick", "getSalvo(this.id)")

            row.appendChild(td)
        }
        tblBody.appendChild(row);
    }
    tbl.appendChild(tblBody);
}

//function checkSalvo(){
//
//    let hitDiv = document.querySelectorAll('.hit-div')
//        console.log(hitDiv)
//        console.log(Array.from(hitDiv).length)
//        hitDiv.forEach(e => {
//        let a = e.offsetParent
//        return a.removeAttribute = "onclick"
//        })
//
//
//}


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
            }else if(elem.player.id !== myPlayer_id){
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
 var urlParams = new URLSearchParams(window.location.search);
 var myParam = urlParams.get('gp');
    let turn = document.createElement("p");
    let arr = slvGames.salvoes
    let user_id = slvGames.gPlayer_id
    arr.forEach(elem => {
        if(elem.gp_id == myParam){
            elem.gp_salvoes.forEach(elem2 => {
                elem2.salvoes_locations.forEach(place => {
                    var slvSeg = document.getElementById(place)
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


function placeSHits(slvGames){
 var urlParams = new URLSearchParams(window.location.search);
 var myParam = urlParams.get('gp');
    let turn = document.createElement("p");
    let arr = slvGames.salvoes
    let user_id = slvGames.player_id
    arr.forEach(elem => {
    slvGames.gamePlayer.forEach(playa =>{
//        console.log(playa.player.id)
    })
        if(elem.gp_id != myParam){
            elem.gp_salvoes.forEach(elem2 => {
                elem2.salvoes_locations.forEach(place => {
                    let newId = place.split('').slice(1, 4).join("")
                    var slvSeg = document.getElementById( newId)
                       if(slvSeg.id ==  newId){
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
            if(elem.gp_id != myParam){
                elem.gp_salvoes.forEach(elem2 =>{
                elem2.salvoes_locations.forEach(elem3 => {
                    let elem4 = elem3.split('').slice(1, 4).join("")
                    let a = shipLocArr.filter(loc => loc == elem4)
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

function placeShip(){
    var urlParams = new URLSearchParams(window.location.search);
    var myParam = urlParams.get('gp');

    let data = [
              { "type": "carrier", "locations": ["A10", "B10", "C10","D10","E10"] },
              { "type": "battleship", "locations": ["D2", "D3", "D4", "D5"] },
              { "type": "destroyer", "locations" : ["A1", "B1", "C1"] },
              { "type": "submarine", "locations": ["F2", "F3", "F4"] },
              { "type": "patrol boat", "locations": ["H5", "H6"] }
               ]

    let  url = '/api/games/players/'+ myParam + '/ships'
    console.log(url);

    fetch(url, {
        method: "POST",
        credentials: "include",
        headers:{
            'Accept': "application/json",
            'Content-Type': "application/json"
        },

         body : JSON.stringify(data)

         })
         .then(function(response){
            return response.json();
         })
         .then(function(json){
            console.log(data)
            location.reload();
         });
}

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

function addShips(){
    var  data = [ ]
    var carrierArr = []
    var patrolBoatArr = []
    var destroyerArr = []
    var submarineArr = []
    var battleshipArr = []
    var urlParams = new URLSearchParams(window.location.search);
    var myParam = urlParams.get('gp');
    var arr = Array.from(Array(10).keys())
    var arr2 = Array.from(Array(10), (e, i) => String.fromCharCode(i + 65));
            arr.forEach(el => {
                    arr2.forEach(elem =>{
                        data.push(elem + (el + 1))
                                        })
                              })
    for(var i = 0; i < data.length; i++){
        while(carrierArr.length < 5 ){
            carrierArr = data.slice(0, 5).concat(carrierArr);
            data = data.slice(5, -1)
            console.log(carrierArr)
                for (var j = 0; j < data.length; j++){
                    while(battleshipArr.length < 4){
                        battleshipArr = data.slice(89, -1).concat(battleshipArr);
                            data = data.slice(0, 89);
                            console.log(battleshipArr)
                for (var x = 0; x < data.length; x++){
                    while(submarineArr.length < 3){
                         submarineArr = data.slice(7, 10).concat(submarineArr);
                             data = data.filter( item => !submarineArr.includes(item))
                             console.log(submarineArr)
                for (var y = 0; y < data.length; y++ ){
                     while(destroyerArr.length < 3){
                         destroyerArr = data.slice(17,20).concat(destroyerArr);
                            data = data.filter( item => !destroyerArr.includes(item))
                            console.log(destroyerArr)
                for (var z = 0; z < data.length; z++){
                     while(patrolBoatArr.length < 2){
                          patrolBoatArr = data.slice(77, 78).concat(patrolBoatArr);
                             data = data.filter( item => !patrolBoatArr.includes(item))
                                console.log(patrolBoatArr)
                                    }

                                }
                            }
                        }

                    }
                 }

               }
            }

        }

    }
    for(var c = 0; c < 1; c++){
    let n = getRandomInt(25) + 1
    const every_nth = (data, nth) => data.filter((e, i) => i % nth === nth - 1);
    let a = data.slice(0, n)
     data = data.filter( item => !a.includes(item))

    let c = every_nth(data, 10)
    console.log(c);
    while(c.length > 5){
        c.shift()
        c.pop()
        console.log(c)
    }
    }


var urlParams = new URLSearchParams(window.location.search);
    var myParam = urlParams.get('gp');

//    let ships = [
//              { type: "carrier", locations: carrierArr},
//              { type: "battleship",locations: battleshipArr},
//              { type: "destroyer", locations: submarineArr },
//              { type: "submarine", locations: destroyerArr },
//              { type: "patrol boat", locations: patrolBoatArr }
//              ]
//                let  url = '/api/games/players/'+ myParam + '/ships'
//                  console.log(url);
//
//                  fetch(url, {
//                      method: "POST",
//                      credentials: "include",
//                      headers:{
//                          'Accept': "application/json",
//                          'Content-Type': "application/json"
//                      },
//
//                       body : JSON.stringify(ships)
//
//                       })
//                       .then(function(response){
//                          return response.json();
//                       })
//                       .then(function(json){
//
//                          location.reload();
//                       });

}

//function allowDrop(ev) {
//  ev.preventDefault();
//}
//
//function drag(ev) {
//
//  ev.dataTransfer.setData("text", ev.target.id);
//}
//
//function drop(ev) {
//  ev.preventDefault();
//  var data = ev.dataTransfer.getData("text");
//  ev.target.appendChild(document.getElementById(data));
//  console.log(ev.target)
//}
function getSalvo(id){
    let isClicked = false
    let allSalvoes = []
    let td = document.getElementById(id)
        allSalvoes.push(id)
//    let newId = id.split('').slice(1, 4).join("")
            if (allSalvoes.includes(id)){
                 isClicked = true
                 if(isClicked == true){
                    let salvos = {turn: turn, locations:[id]}
                        console.log(salvos)
                        var urlParams = new URLSearchParams(window.location.search);
                        var myParam = urlParams.get('gp');

                            let  url = '/api/games/players/'+ myParam + '/salvos'
                              fetch(url, {
                                  method: "POST",
                                  credentials: "include",
                                  headers:{
                                      'Accept': "application/json",
                                      'Content-Type': "application/json"
                                  },

                                   body : JSON.stringify(salvos)

                                   })
                                   .then(function(response){

                                      return response.json();
                                   })
                                   .then(function(json){
                                       console.log(json)

                                       location.reload();

                                   })
                                    .catch(err => console.log(err));
        }
    }

}

let hitSink = {
    
}
