# Online Music Sheets
The app makes it possible to generate _html_ music sheets that use maximum of the the space provided by different screen sizes 
so that no (much) scrolling is needed. Input of the html generator are _chopro_ formatted files.
The generation is done by Main.java class. The generated HTML files togehter with the CSS and JS files constitute a Web-Page
that can be uploaded to a web server. 

## Usage
### Generate HTML Songs from Chopro format:
1. Place your _*.chopro_ files to the _resources/chopro_ folder.
2. Run Java main class: _ch.hpdy.Main <path_of_input_files>_ 
3. The generated html files are placed in the _web_ folder.

### Generate Song Sets:
The ch.hpdy.songselector.SongSelector main can be run to generate song sets in html format. 
I run it with a working directory pointing to the root of this project while the song set html file is then stored in web/sets.

## Feature list
1. Generate html files from _chopro_ files
   - Subset of _chopro_ directives is supported only
   - _{s:...}_ directive is not chopro standard
   - _{comment:...}_ directive supported.
2. Chord transposition: The html files offer chord transposing.
 
## Contributing instruction
1. Commit messages [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/)
 
   

