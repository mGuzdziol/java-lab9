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

    var x = Math.floor(Math.random() * 4);
    var y = Math.floor(Math.random() * 4);

    while(miniMaxBoard[x][y]==="X" || miniMaxBoard[x][y]==="O"){
        x = Math.floor(Math.random() * 4);
        y = Math.floor(Math.random() * 4);
    }

    return x +" "+ y;
}