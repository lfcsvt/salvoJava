function makeGrid(){
    var gridPlace = document.getElementById("grid-place")
    var lat = 11
    for (var i = 0; i < lat; i++ ){
    var gridRow = document.createElement("div")
    for( var j = 0; j < lat; j++){
    var gridCol = document.createElement("div")
    var number = document.createElement("p")
        var y = document.createTextNode("")
     number.appendChild(y)
     gridCol.setAttribute("class","grid-col");
     gridCol.setAttribute("id", [i +1] + [j]);
     gridCol.appendChild(number)
     gridRow.appendChild(gridCol)
    }

       gridRow.setAttribute("class", "grid-row");
       gridPlace.appendChild(gridRow)

    }

}

makeGrid()