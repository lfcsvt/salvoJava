function testShip(){
// sets all arrays needed to build the random ship selection
   let arr = []
   let arr3 = []
   let arr4 = []
   let arr5 = []
   let arr6 = []
   let carrier = []
   let battleship = []
   let submarine = []
   let destroyer = []
   let pBoat = []
   let arr2 = Array.from(Array(10), (e, i) => String.fromCharCode(i + 65));

// determines if the ship is horizontal or vertical
   let option = Math.floor(Math.random() * 2);
   let option2 = Math.floor(Math.random() * 2);
   let option3 = Math.floor(Math.random() * 2);
   let option4 = Math.floor(Math.random() * 2);
   let option5 = Math.floor(Math.random() * 2);

// set a random number to calculate the difference
   let a = Math.floor(Math.random() * 10) + 1;
   let b = Math.floor(Math.random() * 10) + 1;
   let c = Math.floor(Math.random() * 10) + 1;
   let d = Math.floor(Math.random() * 10) + 1;
   let e = Math.floor(Math.random() * 10) + 1;

// sets the index of the letter array
   let index = Math.floor(Math.random() * 5);
   let index2 = Math.floor(Math.random() * 5);
   let index3 = Math.floor(Math.random() * 5);
   let index4 = Math.floor(Math.random() * 5);
   let index5 = Math.floor(Math.random() * 5);

 // sets the difference, to prevent numbers over or under the allowed
   let dif = 10 - a
   let dif2 = 10 - b
   let dif3 = 10 - c
   let dif4 = 10 - d
   let dif5 = 10 - e

// sets the letter for the vertical select
   let letter = arr2[index]
   let letter2 = arr2[index2]
   let letter3 = arr2[index3]
   let letter4 = arr2[index4]
   let letter5 = arr2[index5]

// selects the Carrier locations  array
    if(option == 0){
        for(var i = 0; i < 5; i++){
            if(dif > 6){
                arr.push(letter + a++)
        } else  {
        arr.push(letter + a--)
        }
    }
   if(arr.includes(letter + 0)){
        arr.pop(letter + 0)
        arr.push(letter + 5)
   }

    carrier = arr.sort(function(a, b){return a-b})
   } else {
        for(var i = 0; i < 5; i++){
            arr.push(arr2[index++] + a)
            carrier = arr.sort(function(a, b){return a-b})
         }
   }

// selects the Battleship locations  array
   if(option2 == 0){
      for(var j = 0; j< 4; j++){
          if(dif2 > 5){
               arr3.push(letter2 + b++)
          } else  {
           arr3.push(letter2 + b--)
          }
      }
      if(arr3.includes(letter2 + 0)){
       arr3.pop(letter2 + 0)
       arr3.push(letter2 + 4)
      }

      battleship = arr3.sort(function(a, b){return a-b})
    } else {
           for(var j = 0; j< 4; j++){
               arr3.push(arr2[index2++] + b)
               battleship = arr3.sort(function(a, b){return a-b})
           }
      }

// selects the Submarine locations  array
      if(option3 == 0){
            for(var x = 0; x < 3; x++){
                if(dif3 > 5){
                     arr4.push(letter3 + c++)
                } else  {
                 arr4.push(letter3 + c--)
                }
            }
            if(arr4.includes(letter3 + 0)){
             arr4.pop(letter3 + 0)
             arr4.push(letter3 + 3)
            }

                submarine = arr4.sort(function(a, b){return a-b})
      } else {
         for(var x = 0; x < 3; x++){
             arr4.push(arr2[index3++] + c)
             submarine = arr4.sort(function(a, b){return a-b})
          }
      }
// selects the Destroyer locations  array
      if(option4 == 0){
                  for(var y = 0;  y < 3; y++){
                      if(dif4 > 5){
                           arr5.push(letter4 + d++)
                      } else  {
                       arr5.push(letter4 + d--)
                      }
                  }
                  if(arr5.includes(letter4 + 0)){
                   arr5.pop(letter4 + 0)
                   arr5.push(letter + 3)
                  }

                      destroyer = arr5.sort(function(a, b){return a-b})
      } else {
           for(var y = 0; y < 3; y++){
               arr5.push(arr2[index4++] + d)
               destroyer = arr5.sort(function(a, b){return a-b})
            }
      }

// selects the Patrol Boat locations  array
      if(option5 == 0){
                        for(var z = 0;  z < 2; z++){
                            if(dif5 > 5){
                                 arr6.push(letter5 + e++)
                            } else  {
                             arr6.push(letter5 + e--)
                            }
                        }
                        if(arr6.includes(letter5 + 0)){
                         arr6.pop(letter5 + 0)
                         arr6.push(letter5 + 2)
                        }

                            pBoat = arr6.sort(function(a, b){return a-b})
            } else {
                 for(var z = 0; z < 2; z++){
                     arr6.push(arr2[index5++] + e)
                     pBoat = arr6.sort(function(a, b){return a-b})
                  }
            }

   console.log(carrier)
   console.log(battleship)
   console.log(submarine)
   console.log(destroyer)
   console.log(pBoat)
   return carrier
}
testShip()