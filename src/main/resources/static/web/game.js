makeGrid()
makeSGrid()
getData()

 function getData() {
 var urlParams = new URLSearchParams(window.location.search);
 var myParam = urlParams.get('gp');
 var url1 = 'http://localhost:8080/api/game_view/' + myParam ;
 console.log(myParam)
        var urls = [url1, "http://localhost:8080/api/leaderboard"]
        let responseArray = urls.map((url) => {
          let request = new Request(url, {
          });
          return fetch(request).then(response => response.json());
        });

        Promise.all(responseArray).then(allResults => {
          console.log(allResults[0])
          let slvGames  = allResults[0]
//          let leaderboard  = allResults[1]
                          main (slvGames)
        })
      }
function main(slvGames){
   addPlayerInfo(slvGames)
   placeShips(slvGames)
   placeSalvoes(slvGames)
   placeSHits(slvGames)
   myOpponentHits(slvGames)
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
              td.setAttribute("ondrop", "drop(event)")
              td.setAttribute("ondragover", "allowDrop(event)")
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
//placeShip()
function placeShip(){
    var urlParams = new URLSearchParams(window.location.search);
    var myParam = urlParams.get('gp');

    let data = [
//              { type: "carrier", locations: ["I2", "I3", "I4","I5","I6"] },
//              { type: "battleship",locations: ["D2", "D3", "D4", "D5"] },
//              { type: "destroyer", locations: ["A1", "B1", "C1"] },
//              { type: "submarine", locations: ["F2", "F3", "F4"] },
//              { type: "patrol boat", locations: ["H5", "H6"] }
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

//         body:`type=${data.type}&locations=${data.locations}`

         })
         .then(function(response){
            return response.json();
         })
         .then(function(json){
            console.log(data)
            location.reload();
         });

}

function addShips(){
    var  data = [ ]
    var carrier = []
    var patrolBoat = []
    var destroyer = []
    var submarine = []
    var battleship = []
    var urlParams = new URLSearchParams(window.location.search);
    var myParam = urlParams.get('gp');
    var arr = Array.from(Array(10).keys())
    var arr2 = Array.from(Array(10), (e, i) => String.fromCharCode(i + 65));
            arr.forEach(el => {
                    arr2.forEach(elem =>{
                        data.push(elem + (el + 1))
                                        })
                              })

//    console.log(data)
console.log(carrier.length)

    for(var i = 0; i < data.length; i++){
        while(carrier.length < 5 ){
            carrier = data.slice(0, 5).concat(carrier);
            data = data.slice(5, -1)
            for (var j = 0; j < data.length; j++){
              while(battleship.length < 4){
               battleship = data.slice(89, -1).concat(battleship);
               data = data.slice(0, 89);
                 for (var x = 0; x < data.length; x++){
                      while(submarine.length < 3){
                         submarine = data.slice(7, 10).concat(submarine);
                            data = data.slice(0, 89);
                        }
                 }




               }
            }

//            while(destroyer.length < 3){
//                destroyer.push(data[i ++])
//            }


        }

    }


console.log(carrier)
console.log(patrolBoat)
console.log(submarine)
console.log(destroyer)
console.log(battleship)
}

function allowDrop(ev) {
  ev.preventDefault();
}

function drag(ev) {

  ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
  ev.preventDefault();
  var data = ev.dataTransfer.getData("text");
  ev.target.appendChild(document.getElementById(data));
  console.log(ev.target)
}



