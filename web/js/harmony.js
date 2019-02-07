$("#transposeUp").click(function(){
    transposeAndSetAllChords(1);
});
$("#transposeDown").click(function(){
    transposeAndSetAllChords(-1);
});

function transposeAndSetAllChords(nbrOfSteps) {
    let key = $("#key").text();
    $(".chord").each(index => {
            let origChord = $(".chord")[index].innerText;
            if (origChord != ".") {
                let transposedChord = transpose(nbrOfSteps, origChord, key);
                $(".chord")[index].innerText = transposedChord;
            }
        }
    );
}
/**
 * Created by Hans-Peter Schmid on 25.12.2018.
 */




function shift(chordArray, scalePos, nbrOfSteps) {
    while((scalePos + nbrOfSteps) < 0){ nbrOfSteps += chordArray.length;}
    let newPos = (scalePos + nbrOfSteps) % chordArray.length;
    return chordArray[newPos];
}

const chordScales = {
     '#' : ['C','C#','D','D#','E','F','F#','G','G#','A','A#','H'],
     'B' : ['C','Db','D','Eb','E','F','Gb','G','Ab','A','B','H']
}

const susOrSharpTransposeMap = { 'C' : { down : '#', up: 'B' },
                                'Db' : { down : '#', up: '#' },
                                'D' :  { down : 'B', up: 'B' },
                                'Eb' : { down : '#', up: '#' },
                                'E' :  { down : 'B', up: 'B' },
                                'F' :  { down : '#', up: 'B' },
                                'Gb' : { down : '#', up: '#' },
                                'G' :  { down : 'B', up: 'B' },
                                'Ab' : { down : '#', up: '#' },
                                'A' :  { down : 'B', up: 'B' },
                                'B' :  { down : '#', up: '#' },
                                'H' :  { down : 'B', up: '#' }};

console.log("Maptest");
console.log(susOrSharpTransposeMap["E"].up);

/**
 * Accepts only chords in the format [CDEFGABHcdefgabhc]*[#b]
 * B is to be delivered as H.
 */
function transposeCoreChord(nbrOfSteps, chord, baseChord){
    let isMinor = firstCharIsLowerCase(chord);

    let foundInSharpScale = chordScales["#"].findIndex(scaleChord => scaleChord == chord.toUpperCase());
    let foundInBScale = chordScales["B"].findIndex(scaleChord => scaleChord == chord.substring(0,1).toUpperCase() + chord.substring(1));

    let foundPos = ( foundInBScale != -1 ? foundInBScale : foundInSharpScale);
    if(foundPos > -1){
        let direction = (nbrOfSteps > 0 ? "up" : "down")
        let transposedChord = shift(chordScales[susOrSharpTransposeMap[baseChord][direction]], foundPos, nbrOfSteps)
        if(isMinor){
            return transposedChord.toLowerCase()
        }
        return transposedChord;

    }else{
        return "UNDEFINED"
    }

    // if(posMajor != -1) return bArray[posMajor];
    // if(posMinor != -1) return sharpArray[posMinor];
}

//test only:
let steps = 1;
let baseChord = 'C';
console.log("-----Core transpose: ----");
console.log('C'  + ' wird zu ' + transposeCoreChord(steps, 'C' , baseChord));
console.log('Db' + ' wird zu ' + transposeCoreChord(steps, 'Db', baseChord));
console.log('F#' + ' wird zu ' + transposeCoreChord(steps, 'F#', baseChord));
console.log('B'  + ' wird zu ' + transposeCoreChord(steps, 'B' , baseChord));
console.log('H'  + ' wird zu ' + transposeCoreChord(steps, 'H' , baseChord));
console.log('a'  + ' wird zu ' + transposeCoreChord(steps, 'a' , baseChord));
console.log('db' + ' wird zu ' + transposeCoreChord(steps, 'db', baseChord));
console.log('f#' + ' wird zu ' + transposeCoreChord(steps, 'f#', baseChord));
console.log('b'  + ' wird zu ' + transposeCoreChord(steps, 'b' , baseChord));
console.log('h'  + ' wird zu ' + transposeCoreChord(steps, 'h' , baseChord));

function transpose(nbrOfSteps, chord, baseChord){
    if(!chord) return "ERR0";
    let mainAndBase = separateChordFromBase(chord);
    if(!mainAndBase.main) return "ERR1";
    let adaptedMainChord = minorsAsWithLowerCaseNotation(mainAndBase.main);
    if(!adaptedMainChord) return "ERR2";
    let coreAndAside = separateChord(adaptedMainChord);
    if(!coreAndAside.core) return "ERR3";
    let transposedCore = transposeCoreChord(nbrOfSteps, coreAndAside.core, baseChord);
    let transposedBase = "";

    let transposedChord = transposedCore + coreAndAside.aside;
    if(mainAndBase.base && mainAndBase.base.length > 0){
        transposedBase = transposeCoreChord(nbrOfSteps, mainAndBase.base, baseChord);
        transposedChord += "/" + transposedBase.substring(0,1).toUpperCase() + transposedBase.substring(1);
    }
    return transposedChord;
}
function firstCharIsLowerCase(string){
    return string.substring(0,1).toUpperCase() != string.substring(0,1);
}
console.log("-----MAIN transpose: ----");
console.log('Am7/G'    + ' wird zu ' + transpose(steps, 'Am7/G'  , baseChord));
console.log('Dbm+/F#'  + ' wird zu ' + transpose(steps, 'Dbm+/F#', baseChord));
console.log('F#m7/9'   + ' wird zu ' + transpose(steps, 'F#m7/9' , baseChord));
console.log('Csus4/7'  + ' wird zu ' + transpose(steps, 'Csus4/7', baseChord));
console.log('Cdim7/A'  + ' wird zu ' + transpose(steps, 'Cdim7/A', baseChord));
console.log('Cmin7/D'  + ' wird zu ' + transpose(steps, 'Cmin7/D', baseChord));

function separateChordFromBase(chord){
    let chordSepDelimiterPattern = /(\/)[A-GHa-gh#]+/;
    let chordParts = chord.split(chordSepDelimiterPattern);

    let aside;
    if(chordParts[1] == "/"){
       aside = chord.substring(chordParts[0].length + chordParts[1].length);
    }
    return {main : chordParts[0], base : aside};
}

console.log("-----Separating Chord from Base note ----");
console.log('Am7/G'    + ' wird zu ' + separateChordFromBase('Am7/G'  ).main + " / side: " + separateChordFromBase('Am7/G'  ).base);
console.log('Dbm+/F#'  + ' wird zu ' + separateChordFromBase('Dbm+/F#').main + " / side: " + separateChordFromBase('Dbm+/F#').base);
console.log('F#m7/9'   + ' wird zu ' + separateChordFromBase('F#m7/9' ).main + " / side: " + separateChordFromBase('F#m7/9' ).base);
console.log('Csus4/7'  + ' wird zu ' + separateChordFromBase('Csus4/7').main + " / side: " + separateChordFromBase('Csus4/7').base);
console.log('Cdim7/A'  + ' wird zu ' + separateChordFromBase('Cdim7/A').main + " / side: " + separateChordFromBase('Cdim7/A').base);
console.log('Cmin7/D'  + ' wird zu ' + separateChordFromBase('Cmin7/D').main + " / side: " + separateChordFromBase('Cmin7/D').base);


function separateChord(chord){
    let chordSepDelimiterPattern = /(\d|sus|dim|min|\+)/;
    let chordParts = chord.split(chordSepDelimiterPattern);
    return {core : chordParts[0], aside : chord.substring(chordParts[0].length)};
}
console.log("-----Separating Core chord from additional chord specifics: ----");
console.log('Am7'    + ' wird zu ' + separateChord('Am7'   ).core + " / side: " + separateChord('Am7'   ).aside);
console.log('Dbm+'   + ' wird zu ' + separateChord('Dbm+'  ).core + " / side: " + separateChord('Dbm+'  ).aside);
console.log('F#m7/9' + ' wird zu ' + separateChord('F#m7/9').core + " / side: " + separateChord('F#m7/9').aside);
console.log('Csus4'  + ' wird zu ' + separateChord('Csus4' ).core + " / side: " + separateChord('Csus4' ).aside);
console.log('Cdim7'  + ' wird zu ' + separateChord('Cdim7' ).core + " / side: " + separateChord('Cdim7' ).aside);
console.log('Cmin7'  + ' wird zu ' + separateChord('Cmin7' ).core + " / side: " + separateChord('Cmin7' ).aside);

/**
 * This function translates chords minor notations with capital letter and "m" (minor)
 * into the syntax describing minor by having lower case (main) letter:
 * E.G: F#m7 translated to f#7
 */
function minorsAsWithLowerCaseNotation(chord){
    let posMin = chord.indexOf("min");
    let minorPattern = /([^ac-z])(m)(?!in)/;
    if(minorPattern.test(chord)){
        chord = chord.replace(minorPattern, "$1");
        chord = chord.substring(0,1).toLowerCase() + chord.substring(1);
    }
    return chord;
}

console.log("-----Transposing e.g. F#m7 to f#7: ----");
console.log('Am7'    + ' wird zu ' + minorsAsWithLowerCaseNotation('Am7'   ));
console.log('Dbm+'   + ' wird zu ' + minorsAsWithLowerCaseNotation('Dbm+'  ));
console.log('F#m7/9' + ' wird zu ' + minorsAsWithLowerCaseNotation('F#m7/9'));
console.log('Bm'     + ' wird zu ' + minorsAsWithLowerCaseNotation('Bm'    ));
console.log('Hm'     + ' wird zu ' + minorsAsWithLowerCaseNotation('Hm'    ));
console.log('Csus4'  + ' wird zu ' + minorsAsWithLowerCaseNotation('Csus4' ));
console.log('Cdim7'  + ' wird zu ' + minorsAsWithLowerCaseNotation('Cdim7' ));
console.log('Cmin7'  + ' wird zu ' + minorsAsWithLowerCaseNotation('Cmin7' ));