var algorithm = function(oneTab) {
    var arrayOfStrings = oneTab.split(" ");

    var miniMaxBoard = new Array(4);

    for (var i = 0; i < miniMaxBoard.length; i++) {
        miniMaxBoard[i] = new Array(4);
    }

    var z=0;
    for (var i = 0; i < 4; i++) {
        for (var j = 0; j < 4; j++) {
            miniMaxBoard[j][i] = arrayOfStrings[z];
            z++;
        }
    }

       for (var i = 0; i < 4; i++) {
           for (var j = 0; j < 4; j++) {
               if(miniMaxBoard[j][i]==="") {
                   miniMaxBoard[j][i] ="O";
                   if (wygrana(miniMaxBoard, "O"))
                       return j +" "+ i;
                   miniMaxBoard[j][i] = "";
               }
           }
   }

   for (var i = 0; i < 4; i++) {
       for (var j = 0; j < 4; j++) {
           if(miniMaxBoard[j][i]==="")
           {
               miniMaxBoard[j][i] ="X";
               if(wygrana(miniMaxBoard,"X"))
                   return j +" "+ i;
               miniMaxBoard[j][i]="";
           }
       }
   }

   var x = Math.floor(Math.random() * 4);
   var y = Math.floor(Math.random() * 4);

   while(miniMaxBoard[x][y]==="X" || miniMaxBoard[x][y]==="O"){
       x = Math.floor(Math.random() * 4);
       y = Math.floor(Math.random() * 4);
   }

    return x +" "+ y;
}

var wygrana = function(t,g) {
    var test;

    test = false;

    for (var y = 0; y < 4; y++) {
    test |= ((t[0][y] === g) && (t[1][y] === g) && (t[2][y] === g));
    test |= ((t[1][y] === g) && (t[2][y] === g) && (t[3][y] === g));
}

    for (var x = 0; x < 4; x++) {
    test |= ((t[x][0] === g) && (t[x][1] === g) && (t[x][2] === g));
    test |= ((t[x][1] === g) && (t[x][2] === g) && (t[x][3] === g));
}

    test |= ((t[0][0] === g) && (t[1][1] === g) && (t[2][2] === g));
    test |= ((t[2][0] === g) && (t[1][1] === g) && (t[0][2] === g));
    test |= ((t[1][0] === g) && (t[2][1] === g) && (t[3][2] === g));
    test |= ((t[3][0] === g) && (t[2][1] === g) && (t[1][2] === g));
    test |= ((t[0][1] === g) && (t[1][2] === g) && (t[2][3] === g));
    test |= ((t[2][1] === g) && (t[1][2] === g) && (t[0][3] === g));
    test |= ((t[1][1] === g) && (t[2][2] === g) && (t[3][3] === g));
    test |= ((t[3][1] === g) && (t[2][2] === g) && (t[1][3] === g));

    if(test)
    {
        return true;
    }
    return false;
}