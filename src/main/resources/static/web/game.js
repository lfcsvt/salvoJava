
function getPlayerData(){
var urlParams = new URLSearchParams(window.location.search);
var myParam = urlParams.get('gp');
var url = 'http://localhost:8080/api/game_view/' + myParam
    fetch( url, {

            })
            .then(response => response.json())
            .then(response => {
                var slvGames = response
                console.log(slvGames)
                main (slvGames)

            })

            .catch(err => console.log(err));
}

function main(slvGames){
   addPlayerInfo(slvGames)
   placeShips(slvGames)
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
        locate.locations.forEach(place => {
            var shipSeg = document.getElementById(place)
                if(shipSeg.id == place){
                    shipSeg.setAttribute("style", "background-color: blue;");
                }
        })
   })

}

makeGrid()
getPlayerData()